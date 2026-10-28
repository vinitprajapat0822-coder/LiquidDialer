package com.vinit.liquiddialer.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinit.liquiddialer.data.repository.ContactRepository
import com.vinit.liquiddialer.domain.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DialerUiState(
    val number: String = "",
    val suggestions: List<Contact> = emptyList(),
    val contactsLoaded: Boolean = false,
    val permissionNeeded: Boolean = false
)

@HiltViewModel
class DialerViewModel @Inject constructor(
    private val contactRepository: ContactRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DialerUiState())
    val state: StateFlow<DialerUiState> = _state.asStateFlow()

    private var allContacts: List<Contact> = emptyList()

    init {
        loadContacts()
    }

    fun loadContacts() {
        viewModelScope.launch {
            val list = contactRepository.loadContacts()
            allContacts = list
            _state.update {
                it.copy(
                    contactsLoaded = true,
                    permissionNeeded = list.isEmpty()
                )
            }
            onNumberChanged(_state.value.number)
        }
    }

    fun onNumberChanged(newNumber: String) {
        val filtered = contactRepository.filterT9(allContacts, newNumber)
        _state.update {
            it.copy(number = newNumber, suggestions = filtered)
        }
    }

    fun appendDigit(digit: String) {
        onNumberChanged(_state.value.number + digit)
    }

    fun deleteLast() {
        val current = _state.value.number
        if (current.isNotEmpty()) {
            onNumberChanged(current.dropLast(1))
        }
    }

    fun clear() {
        onNumberChanged("")
    }

    fun selectSuggestion(contact: Contact) {
        onNumberChanged(contact.number)
    }
}

package com.vinit.liquiddialer.ui.screens

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.CallLog
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallReceived
import androidx.compose.material.icons.filled.CallMade
import androidx.compose.material.icons.filled.CallMissed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vinit.liquiddialer.data.repository.CallLogRepository
import com.vinit.liquiddialer.domain.CallLogEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class RecentsViewModel @Inject constructor(
    private val repo: CallLogRepository
) : ViewModel() {
    private val _entries = MutableStateFlow<List<CallLogEntry>>(emptyList())
    val entries = _entries.asStateFlow()

    fun load() {
        viewModelScope.launch {
            _entries.value = repo.loadRecent()
        }
    }
}

@Composable
fun RecentsScreen(viewModel: RecentsViewModel = hiltViewModel()) {
    val context = LocalContext.current
    val entries by viewModel.entries.collectAsState()

    val permLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { if (it) viewModel.load() }

    LaunchedEffect(Unit) {
        val ok = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CALL_LOG) ==
            PackageManager.PERMISSION_GRANTED
        if (ok) viewModel.load() else permLauncher.launch(Manifest.permission.READ_CALL_LOG)
    }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Recents", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))
        if (entries.isEmpty()) {
            Box(Modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No recent calls", color = MaterialTheme.colorScheme.onSurface.copy(0.5f))
            }
        } else {
            LazyColumn {
                items(entries, key = { it.id }) { entry ->
                    RecentRow(entry) {
                        val uri = Uri.fromParts("tel", entry.number, null)
                        try {
                            context.startActivity(Intent(Intent.ACTION_CALL, uri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
                        } catch (_: SecurityException) {
                            context.startActivity(Intent(Intent.ACTION_DIAL, uri))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun RecentRow(entry: CallLogEntry, onCall: () -> Unit) {
    val icon = when (entry.type) {
        CallLog.Calls.INCOMING_TYPE -> Icons.Default.CallReceived
        CallLog.Calls.OUTGOING_TYPE -> Icons.Default.CallMade
        CallLog.Calls.MISSED_TYPE -> Icons.Default.CallMissed
        else -> Icons.Default.Call
    }
    val time = remember(entry.timestamp) {
        SimpleDateFormat("dd MMM, HH:mm", Locale.getDefault()).format(Date(entry.timestamp))
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onCall)
            .padding(vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = entry.name ?: entry.number,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = "${entry.number} · $time",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(0.6f)
            )
        }
        IconButton(onClick = onCall) {
            Icon(Icons.Default.Call, contentDescription = "Call")
        }
    }
}

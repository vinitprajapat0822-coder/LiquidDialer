package com.vinit.liquiddialer.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.vinit.liquiddialer.data.Prefs

@Composable
fun SettingsScreen() {
    val context = LocalContext.current
    val prefs = remember { Prefs(context) }

    var flash by remember { mutableStateOf(prefs.flashEnabled) }
    var announcer by remember { mutableStateOf(prefs.announcerEnabled) }
    var slide by remember { mutableStateOf(prefs.slideToAnswer) }
    var theme by remember { mutableStateOf(prefs.themeId) }
    var blocked by remember { mutableStateOf(prefs.blockedNumbers().toList()) }
    var newBlock by remember { mutableStateOf("") }

    LazyColumn(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            Text("Settings", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp))
        }
        item {
            Text("Call features", style = MaterialTheme.typography.titleMedium)
            SettingSwitch("Flash on call", flash) { flash = it; prefs.flashEnabled = it }
            SettingSwitch("Caller announcer (TTS)", announcer) { announcer = it; prefs.announcerEnabled = it }
            SettingSwitch("Slide to answer", slide) { slide = it; prefs.slideToAnswer = it }
        }
        item {
            Spacer(Modifier.height(12.dp))
            Text("Theme", style = MaterialTheme.typography.titleMedium)
            listOf(
                "glass_dark" to "Glass Dark",
                "glass_green" to "Emerald Green",
                "glass_neon" to "Neon Green"
            ).forEach { (id, label) ->
                Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                    RadioButton(selected = theme == id, onClick = { theme = id; prefs.themeId = id })
                    Text(label)
                }
            }
        }
        item {
            Spacer(Modifier.height(12.dp))
            Text("Block list", style = MaterialTheme.typography.titleMedium)
            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = newBlock,
                    onValueChange = { newBlock = it },
                    label = { Text("Number") },
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                Spacer(Modifier.width(8.dp))
                Button(onClick = {
                    if (newBlock.isNotBlank()) {
                        prefs.block(newBlock)
                        blocked = prefs.blockedNumbers().toList()
                        newBlock = ""
                    }
                }) { Text("Block") }
            }
        }
        items(blocked) { num ->
            Row(
                Modifier.fillMaxWidth().padding(vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(num)
                TextButton(onClick = {
                    prefs.unblock(num)
                    blocked = prefs.blockedNumbers().toList()
                }) { Text("Unblock") }
            }
        }
        item {
            Spacer(Modifier.height(24.dp))
            Text(
                "LiquidDialer · Free · No ads · No subscription",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
            )
        }
    }
}

@Composable
private fun SettingSwitch(label: String, checked: Boolean, onChange: (Boolean) -> Unit) {
    Row(
        Modifier.fillMaxWidth().padding(vertical = 6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label)
        Switch(checked = checked, onCheckedChange = onChange)
    }
}

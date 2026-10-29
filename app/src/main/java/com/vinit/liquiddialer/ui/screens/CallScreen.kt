package com.vinit.liquiddialer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Alarm
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

@Composable
fun CallScreen(
    callerName: String,
    callerNumber: String,
    isSpam: Boolean = false,
    showSlide: Boolean = true,
    onAnswer: () -> Unit,
    onReject: () -> Unit,
    onMessage: () -> Unit = {},
    onRemind: () -> Unit = {}
) {
    val density = LocalDensity.current
    val maxSlidePx = with(density) { 160.dp.toPx() }
    var dragOffset by remember { mutableStateOf(0f) }

    Box(
        Modifier.fillMaxSize().background(
            Brush.verticalGradient(listOf(Color(0xFF0B1220), Color(0xFF152238), Color(0xFF0D1B2A)))
        )
    ) {
        Box(Modifier.size(280.dp).offset((-40).dp, 80.dp).blur(80.dp).background(Color(0xFF3D5AFE).copy(0.25f), CircleShape))
        Box(Modifier.size(220.dp).align(Alignment.BottomEnd).offset(40.dp, (-60).dp).blur(70.dp).background(Color(0xFF00E5FF).copy(0.18f), CircleShape))

        Column(
            Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                if (isSpam) "Spam / Suspicious" else "Incoming call",
                color = if (isSpam) Color(0xFF39FF88) else Color.White.copy(0.7f),
                fontSize = 14.sp, fontWeight = FontWeight.Medium
            )
            Spacer(Modifier.height(48.dp))
            Box(Modifier.size(120.dp).clip(CircleShape).background(Color.White.copy(0.12f)), contentAlignment = Alignment.Center) {
                Text(callerName.take(1).uppercase().ifEmpty { "#" }, fontSize = 48.sp, fontWeight = FontWeight.Light, color = Color.White)
            }
            Spacer(Modifier.height(28.dp))
            Text(callerName.ifEmpty { "Unknown" }, color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.SemiBold, textAlign = TextAlign.Center)
            Spacer(Modifier.height(6.dp))
            Text(callerNumber, color = Color.White.copy(0.65f), fontSize = 16.sp)
            if (isSpam) {
                Spacer(Modifier.height(12.dp))
                Surface(color = Color(0xFF39FF88).copy(0.2f), shape = RoundedCornerShape(20.dp)) {
                    Text("Possible spam", color = Color(0xFF69F0AE), modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp), fontSize = 13.sp)
                }
            }
            Spacer(Modifier.weight(1f))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                QuickAction(Icons.Default.Alarm, "Remind", onRemind)
                QuickAction(Icons.Default.Message, "Message", onMessage)
            }
            Spacer(Modifier.height(28.dp))

            if (showSlide) {
                Box(Modifier.fillMaxWidth().height(72.dp).clip(RoundedCornerShape(36.dp)).background(Color.White.copy(0.10f)), contentAlignment = Alignment.CenterStart) {
                    Text("Slide to answer", color = Color.White.copy(0.45f), fontSize = 15.sp, modifier = Modifier.align(Alignment.Center))
                    Box(
                        Modifier.offset { IntOffset(dragOffset.roundToInt(), 0) }.size(64.dp).padding(4.dp).clip(CircleShape)
                            .background(Brush.linearGradient(listOf(Color(0xFF00C853), Color(0xFF69F0AE))))
                            .pointerInput(Unit) {
                                detectHorizontalDragGestures(
                                    onDragEnd = { if (dragOffset > maxSlidePx * 0.55f) onAnswer() else dragOffset = 0f },
                                    onHorizontalDrag = { c, a -> c.consume(); dragOffset = (dragOffset + a).coerceIn(0f, maxSlidePx) }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Call, "Answer", tint = Color.White, modifier = Modifier.size(28.dp))
                    }
                }
                Spacer(Modifier.height(20.dp))
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly, verticalAlignment = Alignment.CenterVertically) {
                Box(Modifier.size(68.dp).clip(CircleShape).background(Color(0xFFFF1744)).clickable { onReject() }, contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.CallEnd, "Decline", tint = Color.White, modifier = Modifier.size(30.dp))
                }
                Box(Modifier.size(68.dp).clip(CircleShape).background(Brush.linearGradient(listOf(Color(0xFF00C853), Color(0xFF69F0AE)))).clickable { onAnswer() }, contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.Call, "Answer", tint = Color.White, modifier = Modifier.size(30.dp))
                }
            }
            Spacer(Modifier.height(12.dp))
            Text("Decline          Answer", color = Color.White.copy(0.5f), fontSize = 12.sp)
        }
    }
}

@Composable
private fun QuickAction(icon: ImageVector, label: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.size(52.dp).clip(CircleShape).background(Color.White.copy(0.12f)).clickable(onClick = onClick), contentAlignment = Alignment.Center) {
            Icon(icon, label, tint = Color.White, modifier = Modifier.size(24.dp))
        }
        Spacer(Modifier.height(6.dp))
        Text(label, color = Color.White.copy(0.7f), fontSize = 12.sp)
    }
}

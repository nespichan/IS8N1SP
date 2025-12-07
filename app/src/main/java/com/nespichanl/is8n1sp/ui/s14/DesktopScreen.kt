package com.nespichanl.is8n1sp.ui.s14

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DesktopScreen(
    onListar: () -> Unit,
    onInsertar: () -> Unit,
    onPedidos: () -> Unit,
    onCaja: () -> Unit,
    onCerrarSesion: () -> Unit,
    onSalir: () -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        Text(
            text = "Escritorio",
            style = MaterialTheme.typography.headlineMedium.copy(
                color = Color.Red,
                fontWeight = FontWeight.Bold
            )
        )

        Spacer(Modifier.height(32.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DesktopButton("LISTAR", onListar, Modifier.weight(1f))
                DesktopButton("INSERTAR", onInsertar, Modifier.weight(1f))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DesktopButton("PEDIDOS", onPedidos, Modifier.weight(1f))
                DesktopButton("CAJA", onCaja, Modifier.weight(1f))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                DesktopButton("CERRAR\nSESIÓN", onCerrarSesion, Modifier.weight(1f))
                DesktopButton("SALIR", onSalir, Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun DesktopButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(80.dp)
            .background(
                brush = Brush.verticalGradient(
                    listOf(
                        Color(0xFF6A5AE0),
                        Color(0xFF00E0FF)
                    )
                ),
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}

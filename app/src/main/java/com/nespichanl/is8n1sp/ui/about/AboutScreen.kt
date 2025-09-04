package com.nespichanl.is8n1sp.ui.about

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen(onContinue: () -> Unit) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "Información del curso",
                style = MaterialTheme.typography.titleLarge
            )

            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    AboutRow(titulo = "Carrera", valor = "Ingeniería de Sistemas")
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    AboutRow(titulo = "Curso",   valor = "IS8N1SP-Desarrollo de Aplicaciones Móviles")
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    AboutRow(titulo = "Docente", valor = "Freddy Elias Niño Soto")
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                    AboutRow(titulo = "Grupo",   valor = "N° 2")
                }
            }

            Text(
                "En esta app podrás visualizar y validar todas las actividades del curso.",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(Modifier.height(24.dp))
            Button(onClick = onContinue) {
                Text("Continuar")
            }
        }
}

@Composable
private fun AboutRow(titulo: String, valor: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(titulo, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.width(12.dp))
        Text(valor, style = MaterialTheme.typography.bodyMedium)
    }
}

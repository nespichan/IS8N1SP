package com.nespichanl.is8n1sp.ui.grades

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GradesScreen(onContinue: () -> Unit) {
    var apellido by remember { mutableStateOf("") }
    var curso    by remember { mutableStateOf("") }
    var n1       by remember { mutableStateOf("") }
    var n2       by remember { mutableStateOf("") }
    var n3       by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("") }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Registro de Notas") }) }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = apellido, onValueChange = { apellido = it },
                label = { Text("Apellido") }, singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(
                value = curso, onValueChange = { curso = it },
                label = { Text("Curso") }, singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))

            NumberField(n1, { n1 = it }, "Nota 1 (0–20)")
            Spacer(Modifier.height(8.dp))
            NumberField(n2, { n2 = it }, "Nota 2 (0–20)")
            Spacer(Modifier.height(8.dp))
            NumberField(n3, { n3 = it }, "Nota 3 (0–20)")

            Spacer(Modifier.height(16.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = { onContinue() },
                    modifier = Modifier.weight(1f)
                ) { Text("Finalizar") }

                Button(
                    onClick = {
                        val v1 = n1.toDoubleOrNull()
                        val v2 = n2.toDoubleOrNull()
                        val v3 = n3.toDoubleOrNull()

                        when {
                            apellido.isBlank() || curso.isBlank() || v1 == null || v2 == null || v3 == null ->
                                resultado = "Complete todos los campos con números válidos."
                            listOf(v1, v2, v3).any { it < 0 || it > 20 } ->
                                resultado = "Las notas deben estar entre 0 y 20."
                            else -> {
                                val prom = (((v1 + v2 + v3) / 3.0) * 100).roundToInt() / 100.0
                                val cond = if (prom >= 11.0) "Aprobado" else "Desaprobado"
                                resultado = """
                            Alumno: $apellido
                            Curso: $curso
                            Promedio: $prom
                            Condición: $cond
                        """.trimIndent()
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Calcular") }
            }

            Spacer(Modifier.height(16.dp))
            if (resultado.isNotBlank()) {
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = Color.Red // Fondo rojo para toda la tarjeta
                    )
                ) {
                    Text(
                        text = resultado,
                        style = MaterialTheme.typography.bodyLarge.copy(color = Color.White), // Texto blanco
                        modifier = Modifier.padding(all = 16.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun NumberField(value: String, onChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = { input ->
            // permitir vacío o números con hasta 2 decimales
            if (input.isBlank() || input.matches(Regex("""\d{0,2}(\.\d{0,2})?"""))) {
                onChange(input)
            }
        },
        label = { Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = Modifier.fillMaxWidth()
    )
}

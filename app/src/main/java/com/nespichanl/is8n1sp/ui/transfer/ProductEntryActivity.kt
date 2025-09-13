package com.nespichanl.is8n1sp.ui.transfer

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.unit.dp

class ProductEntryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ProductEntryScreen(
            onSend = { id, nombre, precio ->
                val i = Intent(this, ProductSummaryActivity::class.java).apply {
                    putExtra("EXTRA_ID", id)
                    putExtra("EXTRA_NOMBRE", nombre)
                    putExtra("EXTRA_PRECIO", precio)
                }
                startActivity(i)
            },
            onFinish = { finish() }
        ) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductEntryScreen(
    onSend: (Int, String, Double) -> Unit,
    onFinish: () -> Unit
) {
    var id by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = { TopAppBar(title = { Text("Datos del producto") }) }
    ) { inner ->
        Column(
            Modifier.padding(inner).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = id, onValueChange = { id = it },
                label = { Text("ID (entero)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = nombre, onValueChange = { nombre = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = precio, onValueChange = { precio = it },
                label = { Text("Precio (0.0–9999.99)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            error?.let {
                Text(it, color = MaterialTheme.colorScheme.error)
            }

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(
                    onClick = { onFinish() },
                    modifier = Modifier.weight(1f)
                ) { Text("Finalizar") }

                Button(
                    onClick = {
                        val idInt = id.toIntOrNull()
                        val precioD = precio.toDoubleOrNull()
                        when {
                            idInt == null || idInt < 0              -> error = "ID inválido"
                            nombre.isBlank()                         -> error = "Ingrese el nombre"
                            precioD == null || precioD < 0           -> error = "Precio inválido"
                            else -> {
                                error = null
                                onSend(idInt, nombre.trim(), precioD)
                            }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) { Text("Enviar") }
            }
        }
    }
}

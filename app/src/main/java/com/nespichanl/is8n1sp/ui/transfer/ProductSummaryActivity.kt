package com.nespichanl.is8n1sp.ui.transfer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.TopAppBar

class ProductSummaryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val id = intent.getIntExtra("EXTRA_ID", -1)
        val nombre = intent.getStringExtra("EXTRA_NOMBRE") ?: ""
        val precio = intent.getDoubleExtra("EXTRA_PRECIO", 0.0)

        setContent {
            ProductSummaryScreen(
                id = id, nombre = nombre, precio = precio,
                onBack = { finish() }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductSummaryScreen(id: Int, nombre: String, precio: Double, onBack: () -> Unit) {
    Scaffold(
        topBar = { TopAppBar(title = { Text("Datos ingresados") }) }
    ) { inner ->
        Column(
            Modifier.padding(inner).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ElevatedCard(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text("ID: $id", style = MaterialTheme.typography.bodyLarge)
                    Text("Producto: $nombre", style = MaterialTheme.typography.bodyLarge)
                    Text("Precio: $precio", style = MaterialTheme.typography.bodyLarge)
                }
            }

            Button(onClick = onBack, modifier = Modifier.fillMaxWidth()) {
                Text("Volver")
            }
        }
    }
}

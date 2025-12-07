package com.nespichanl.is8n1sp.ui.s14

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsertScreen(
    viewModel: S14CrudViewModel,
    onBack: () -> Unit,
    onInsertedGoToList: () -> Unit
) {
    val ctx = LocalContext.current

    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var comentario by remember { mutableStateOf("") }
    var edadText by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("borrar10") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6200EE),
                    titleContentColor = Color.White
                )
            )
        }
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .padding(horizontal = 16.dp, vertical = 24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = comentario,
                onValueChange = { comentario = it },
                label = { Text("Comentario") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = edadText,
                onValueChange = { edadText = it.filter { c -> c.isDigit() } },
                label = { Text("Edad") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    val edad = edadText.toIntOrNull()
                    if (nombre.isBlank() || correo.isBlank() || edad == null) {
                        Toast.makeText(
                            ctx,
                            "Completa Nombre, Correo y Edad",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@Button
                    }

                    viewModel.insert(
                        nombre = nombre,
                        correo = correo,
                        comentario = comentario,
                        edad = edad
                    )

                    Toast.makeText(ctx, "Insertado correctamente", Toast.LENGTH_SHORT).show()

                    // Limpiamos y vamos a la lista
                    nombre = ""
                    correo = ""
                    comentario = ""
                    edadText = ""
                    onInsertedGoToList()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF6200EE),
                    contentColor = Color.White
                )
            ) {
                Text("INSERTAR")
            }
        }
    }
}

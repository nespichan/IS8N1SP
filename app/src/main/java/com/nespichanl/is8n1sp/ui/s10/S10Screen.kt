package com.nespichanl.is8n1sp.ui.s10

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.nespichanl.is8n1sp.model.s10.data.local.entity.Elector
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment

import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun S10Screen(
    onContinue: () -> Unit,
) {
    val ctx = LocalContext.current
    val vm = remember { S10ViewModel(ctx) }
    val ui by vm.state.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("S11 – SQLite + Hilos") }) }) { p ->
        Column(Modifier.padding(p).padding(16.dp)) {
            OutlinedTextField(
                value = ui.dni, onValueChange = { vm.onChange(dni = it.take(8)) },
                label = { Text("dni") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = ui.nombre, onValueChange = { vm.onChange(nombre = it) }, label = { Text("nombre y apellido") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = ui.colegio, onValueChange = { vm.onChange(colegio = it) }, label = { Text("nombre del colegio") }, modifier = Modifier.fillMaxWidth())
            Spacer(Modifier.height(8.dp))
            OutlinedTextField(value = ui.mesa, onValueChange = { vm.onChange(mesa = it) }, label = { Text("número de mesa") }, modifier = Modifier.fillMaxWidth())

            Spacer(Modifier.height(12.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                Button(onClick = vm::alta, modifier = Modifier.weight(1f)) { Text("Alta") }
                OutlinedButton(onClick = vm::consultar, modifier = Modifier.weight(1f)) { Text("Consultar DNI") }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = vm::baja, modifier = Modifier.weight(1f)) { Text("Baja") }
                Button(onClick = vm::modificar, modifier = Modifier.weight(1f)) { Text("Modificar") }
            }
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = vm::limpiar, modifier = Modifier.weight(1f)) { Text("Limpiar") }
                Button(onClick = onContinue, modifier = Modifier.weight(1f)) { Text("Finalizar") }
            }

            if (ui.mensaje != null && ui.msgType != null) {
                Spacer(Modifier.height(8.dp))
                MessageBar(
                    text = ui.mensaje!!,
                    type = ui.msgType!!,
                    onDismiss = { vm.clearMessage() }
                )
            }

            Spacer(Modifier.height(12.dp))
            Text("Registros:", style = MaterialTheme.typography.titleSmall)
            LazyColumn {
                items(ui.lista, key = { it.id }) { e: Elector ->
                    ListItem(
                        headlineContent = { Text(e.nombre) },
                        supportingContent = { Text("${e.dni} • ${e.colegio} • mesa ${e.mesa}") }
                    )
                    HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)
                }
            }
        }
    }
}

@Composable
private fun MessageBar(
    text: String,
    type: MsgType,
    onDismiss: () -> Unit
) {
    val (bg, fg, icon) = when (type) {
        MsgType.Success -> Triple(Color(0xFFE8F5E9), Color(0xFF1B5E20), Icons.Default.CheckCircle)
        MsgType.Warning -> Triple(Color(0xFFFFF3E0), Color(0xFFEF6C00), Icons.Default.Warning)
        MsgType.Error   -> Triple(Color(0xFFFFEBEE), Color(0xFFC62828), Icons.Default.Error)
        MsgType.Info    -> Triple(Color(0xFFE3F2FD), Color(0xFF1565C0), Icons.Default.Info)
    }
    Surface(
        color = bg,
        contentColor = fg,
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(icon, contentDescription = null)
            Spacer(Modifier.width(8.dp))
            Text(text, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
            IconButton(onClick = onDismiss) { Icon(Icons.Default.Close, contentDescription = "Cerrar") }
        }
    }
}


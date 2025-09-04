package com.nespichanl.is8n1sp.ui.members

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.nespichanl.is8n1sp.R

data class Member(val nombre: String, val rol: String)

@Composable
fun MembersScreen(onContinue: () -> Unit) {
    val integrantes = listOf(
        Member("Cermeño Franco, Nomar", "Delegado"),
        Member("Luyo Hernandez, Leslie", ""),
        Member("Altamirano Paiba, Dario", ""),
        Member("Espichán Lévano, Néstor", ""),
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(R.drawable.g2_appmovil),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Text("Grupo 2 - Integrantes", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(16.dp))

        integrantes.forEach {
            Text("${it.nombre} - ${it.rol}")
        }

        Spacer(Modifier.height(24.dp))
        Button(onClick = onContinue) {
            Text("Continuar")
        }
    }
}

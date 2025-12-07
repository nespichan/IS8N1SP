package com.nespichanl.is8n1sp.ui.s14

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel

class S14CrudActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme {
                val vm: S14CrudViewModel = viewModel()
                var current by rememberSaveable { mutableStateOf<S14Screen>(S14Screen.Desktop) }

                when (current) {
                    is S14Screen.Desktop -> DesktopScreen(
                        onListar = { current = S14Screen.List },
                        onInsertar = { current = S14Screen.Insert },
                        onPedidos = { /* TODO */ },
                        onCaja = { /* TODO */ },
                        onCerrarSesion = { finish() },
                        onSalir = { finish() }
                    )

                    is S14Screen.List -> ListScreen(
                        viewModel = vm,
                        onBack = { current = S14Screen.Desktop }
                    )

                    is S14Screen.Insert -> InsertScreen(
                        viewModel = vm,
                        onBack = { current = S14Screen.Desktop },
                        onInsertedGoToList = {
                            current = S14Screen.List
                        }
                    )
                }
            }
        }
    }
}

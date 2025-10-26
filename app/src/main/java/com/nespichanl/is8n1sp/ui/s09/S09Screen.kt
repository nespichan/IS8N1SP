package com.nespichanl.is8n1sp.ui.s09

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import java.math.BigInteger

// ---------- STATE ----------
data class S09State(
    val input: String = "",
    val isProcessing: Boolean = false,
    val progress: Float = 0f,                  // 0..1
    val sumEven: BigInteger? = null,
    val factorialShort: String? = null,        // representación abreviada
    val error: String? = null
)

// ---------- VIEWMODEL ----------
class S09ViewModel : ViewModel() {

    var ui by mutableStateOf(S09State())
        private set

    private val mainHandler = Handler(Looper.getMainLooper())

    fun onInputChange(txt: String) {
        ui = ui.copy(input = txt.filter { it.isDigit() })
    }

    fun calcular() {
        val n = ui.input.toIntOrNull()
        if (n == null || n < 0) {
            ui = ui.copy(error = "Ingrese un entero positivo", isProcessing = false)
            return
        }
        // Pequeño límite saludable para no colgar el teléfono
        if (n > 20000) {
            ui = ui.copy(error = "Para la demo limite a N ≤ 20000", isProcessing = false)
            return
        }

        ui = ui.copy(isProcessing = true, progress = 0f, error = null, sumEven = null, factorialShort = null)

        Thread {
            var sumEven = BigInteger.ZERO
            var fact = BigInteger.ONE

            val chunk = (n.coerceAtLeast(1) / 100).coerceAtLeast(1) // ~100 actualizaciones

            for (i in 1..n) {
                if (i % 2 == 0) sumEven += BigInteger.valueOf(i.toLong())
                fact *= BigInteger.valueOf(i.toLong())

                if (i % chunk == 0 || i == n) {
                    val p = i.toFloat() / n.coerceAtLeast(1).toFloat()
                    val factShort = shortenBigInteger(fact)
                    mainHandler.post {
                        ui = ui.copy(
                            progress = p,
                            sumEven = sumEven,
                            factorialShort = factShort
                        )
                    }
                }
            }

            mainHandler.post {
                ui = ui.copy(isProcessing = false)
            }
        }.start()
    }

    private fun shortenBigInteger(x: BigInteger, head: Int = 14, tail: Int = 14): String {
        val s = x.toString()
        return if (s.length <= head + tail + 5) s
        else "${s.take(head)}…${s.takeLast(tail)} (len=${s.length} díg.)"
    }
}

// ---------- SCREEN ----------
@Composable
fun S09Screen(
    onContinue: () -> Unit,
    modifier: Modifier = Modifier,
    vm: S09ViewModel = viewModel()
) {
    val state = vm.ui
    val scroll = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scroll),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("S09 · Actividades asíncronas", style = MaterialTheme.typography.titleLarge)
        Text(
            "Ingresa un entero positivo N. Se calculará en segundo plano:\n" +
                    "• Suma de pares hasta N\n• Factorial de N",
            style = MaterialTheme.typography.bodyMedium
        )

        OutlinedTextField(
            value = state.input,
            onValueChange = vm::onInputChange,
            label = { Text("N (entero positivo)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = { onContinue() },
                modifier = Modifier.weight(1f)
            ) { Text("Finalizar") }

            Button(
                onClick = vm::calcular,
                enabled = !state.isProcessing && state.input.isNotEmpty()
            ) { Text("Calcular") }

            if (state.isProcessing) {
                LinearProgressIndicator(
                    progress = { state.progress },
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically)
                )
            }
        }

        if (state.error != null) {
            Text(state.error, color = MaterialTheme.colorScheme.error)
        }

        HorizontalDivider(Modifier, DividerDefaults.Thickness, DividerDefaults.color)

        Text("Resultados", style = MaterialTheme.typography.titleMedium)

        RowResult(
            title = "Suma de pares ≤ N",
            value = state.sumEven?.toString() ?: "—"
        )

        RowResult(
            title = "Factorial(N)",
            value = state.factorialShort ?: "—"
        )

        if (!state.isProcessing && state.sumEven != null) {
            AssistChip(
                onClick = { /* podrías copiar al portapapeles si quieres */ },
                label = { Text("Listo ✔") }
            )
        }
    }
}

@Composable
private fun RowResult(title: String, value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Text(title, style = MaterialTheme.typography.labelLarge)
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}
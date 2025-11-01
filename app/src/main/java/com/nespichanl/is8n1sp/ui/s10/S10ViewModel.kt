package com.nespichanl.is8n1sp.ui.s10

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nespichanl.is8n1sp.model.s10.data.local.entity.Elector
import com.nespichanl.is8n1sp.util.ServiceLocatorS10
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

enum class MsgType { Success, Warning, Error, Info }

data class S10UiState(
    val dni: String = "",
    val nombre: String = "",
    val colegio: String = "",
    val mesa: String = "",
    val mensaje: String? = null,
    val msgType: MsgType? = null,
    val lista: List<Elector> = emptyList(),
    val cargando: Boolean = false
)

class S10ViewModel(context: Context) : ViewModel() {

    private val uc = ServiceLocatorS10.provideUseCases(context)

    private val _state = MutableStateFlow(S10UiState())
    val state: StateFlow<S10UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            uc.listar.stream().collect { l ->
                _state.update { it.copy(lista = l) }
            }
        }
    }

    fun onChange(dni: String? = null, nombre: String? = null, colegio: String? = null, mesa: String? = null) {
        _state.update {
            it.copy(
                dni = dni ?: it.dni,
                nombre = nombre ?: it.nombre,
                colegio = colegio ?: it.colegio,
                mesa = mesa ?: it.mesa,
                mensaje = null
            )
        }
    }

    private fun validar(): String? {
        val s = _state.value
        if (s.dni.length != 8 || !s.dni.all { it.isDigit() }) return "DNI inválido"
        if (s.nombre.isBlank()) return "Nombre requerido"
        if (s.colegio.isBlank()) return "Colegio requerido"
        if (s.mesa.isBlank()) return "Mesa requerida"
        return null
    }

    fun alta() {
        val error = validar()
        if (error != null) { toast(error); return }
        val e = with(_state.value) { Elector(dni = dni, nombre = nombre, colegio = colegio, mesa = mesa) }
        launchIO {
            uc.alta(e).fold(
                onSuccess = { show("Alta OK", MsgType.Success) },
                onFailure = { show("Error: ${it.message}", MsgType.Error) }
            )
        }
    }

    fun baja() {
        val dni = _state.value.dni
        if (dni.length != 8) { show("DNI inválido", MsgType.Warning); return }
        launchIO {
            uc.baja(dni).fold(
                onSuccess = { show("Baja OK", MsgType.Success) },
                onFailure = { show("Error: ${it.message}", MsgType.Error) }
            )
        }
    }

    fun consultar() {
        val dni = _state.value.dni
        if (dni.length != 8) { show("DNI inválido", MsgType.Warning); return }
        launchIO {
            uc.consultar(dni).fold(
                onSuccess = { e ->
                    if (e != null) {
                        _state.update { it.copy(nombre = e.nombre, colegio = e.colegio, mesa = e.mesa) }
                        show("Encontrado", MsgType.Success)
                    } else show("No existe", MsgType.Warning)
                },
                onFailure = { show("Error: ${it.message}", MsgType.Error) }
            )
        }
    }

    fun modificar() {
        val error = validar()
        if (error != null) { toast(error); return }
        val s = _state.value
        val nuevo = Elector(dni = s.dni, nombre = s.nombre, colegio = s.colegio, mesa = s.mesa)
        launchIO {
            uc.modificar(s.dni, nuevo).fold(
                onSuccess = { toast("Modificación OK") },
                onFailure = { toast("Error: ${it.message}") }
            )
        }
    }

    fun limpiar() {
        _state.update { old ->
            old.copy(
                dni = "",
                nombre = "",
                colegio = "",
                mesa = "",
                mensaje = null
            )
        }
    }

    private fun show(msg: String, type: MsgType) {
        _state.update { it.copy(mensaje = msg, msgType = type) }
    }

    fun clearMessage() {
        _state.update { it.copy(mensaje = null, msgType = null) }
    }

    private fun toast(msg: String) {
        _state.update { it.copy(mensaje = msg) }
    }

    private inline fun launchIO(crossinline block: suspend () -> Unit) {
        viewModelScope.launch { block() }
    }
}

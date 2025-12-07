package com.nespichanl.is8n1sp.ui.s14

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nespichanl.is8n1sp.data.repository.CrudRepositoryImpl
import com.nespichanl.is8n1sp.domain.model.Person
import com.nespichanl.is8n1sp.domain.repository.CrudRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class S14CrudViewModel(
    private val repository: CrudRepository = CrudRepositoryImpl()
) : ViewModel() {

    val persons: StateFlow<List<Person>> =
        repository.persons.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            emptyList()
        )

    init {
        viewModelScope.launch {
            repository.refresh()
        }
    }

    fun insert(nombre: String, correo: String, comentario: String, edad: Int) {
        viewModelScope.launch {
            val p = Person(
                id = 0,
                nombre = nombre.trim(),
                correo = correo.trim(),
                comentario = comentario.trim(),
                edad = edad
            )
            repository.insert(p)
        }
    }
}

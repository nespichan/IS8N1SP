package com.nespichanl.is8n1sp.data.repository

import com.nespichanl.is8n1sp.data.remote.RetrofitClient
import com.nespichanl.is8n1sp.data.remote.dto.CrudRequest
import com.nespichanl.is8n1sp.data.remote.dto.PersonDto
import com.nespichanl.is8n1sp.domain.model.Person
import com.nespichanl.is8n1sp.domain.repository.CrudRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CrudRepositoryImpl : CrudRepository {

    private val api = RetrofitClient.api

    private val _persons = MutableStateFlow<List<Person>>(emptyList())
    override val persons = _persons.asStateFlow()

    override suspend fun refresh() {
        // op = "list"
        val response = api.execute(
            CrudRequest(op = "list")
        )

        // Asumimos siempre 200 y ok=true, si no hay data, mantenemos la que estaba
        val list = response.data?.mapIndexed { index, dto ->
            dto.toDomain(index + 1)
        }.orEmpty()

        if (list.isNotEmpty()) {
            _persons.value = list
        }
    }

    override suspend fun insert(person: Person) {
        // Llamada al servicio solo para “avisar” del insert
        api.execute(
            CrudRequest(
                op = "insert",
                data = PersonDto(
                    id = person.id,
                    nombre = person.nombre,
                    correo = person.correo,
                    comentario = person.comentario,
                    edad = person.edad
                )
            )
        )

        // Guardamos en memoria (lista viva mientras la app siga)
        val current = _persons.value.toMutableList()
        val nextId = if (current.isEmpty()) 1 else (current.maxOf { it.id } + 1)
        current.add(person.copy(id = nextId))
        _persons.value = current
    }

    private fun PersonDto.toDomain(defaultId: Int): Person =
        Person(
            id = this.id ?: defaultId,
            nombre = nombre,
            correo = correo,
            comentario = comentario,
            edad = edad
        )
}

package com.nespichanl.is8n1sp.domain.repository

import com.nespichanl.is8n1sp.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface CrudRepository {
    val persons: Flow<List<Person>>

    suspend fun refresh()            // list
    suspend fun insert(person: Person)
}

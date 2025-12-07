package com.nespichanl.is8n1sp.data.remote.dto

data class PersonDto(
    val id: Int?,
    val nombre: String,
    val correo: String,
    val comentario: String,
    val edad: Int
)

data class CrudRequest(
    val op: String,          // "list" | "insert"
    val data: PersonDto? = null
)

data class CrudResponse<T>(
    val ok: Boolean,
    val message: String?,
    val data: T?
)

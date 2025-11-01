package com.nespichanl.is8n1sp.model.s10.data.repo

import com.nespichanl.is8n1sp.model.s10.data.local.entity.Elector
import kotlinx.coroutines.flow.Flow

interface ElectorRepository {
    fun stream(): Flow<List<Elector>>
    suspend fun alta(e: Elector): Result<Unit>
    suspend fun baja(dni: String): Result<Unit>
    suspend fun modificarPorDni(dni: String, nuevo: Elector): Result<Unit>
    suspend fun consultar(dni: String): Result<Elector?>
}

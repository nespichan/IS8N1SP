package com.nespichanl.is8n1sp.model.s10.data.repo

import com.nespichanl.is8n1sp.model.s10.data.local.dao.ElectorDao
import com.nespichanl.is8n1sp.model.s10.data.local.entity.Elector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class ElectorRepositoryImpl(
    private val dao: ElectorDao
) : ElectorRepository {

    override fun stream(): Flow<List<Elector>> = dao.streamAll()

    override suspend fun alta(e: Elector): Result<Unit> = runIO {
        dao.insert(e); Unit
    }

    override suspend fun baja(dni: String): Result<Unit> = runIO {
        val rows = dao.deleteByDni(dni)
        if (rows == 0) error("DNI no encontrado")
        Unit
    }

    override suspend fun modificarPorDni(dni: String, nuevo: Elector): Result<Unit> = runIO {
        val current = dao.getByDni(dni) ?: error("DNI no encontrado")
        dao.update(nuevo.copy(id = current.id))
        Unit
    }

    override suspend fun consultar(dni: String): Result<Elector?> = runIO {
        dao.getByDni(dni)
    }
}

private suspend inline fun <T> runIO(crossinline block: suspend () -> T): Result<T> =
    try {
        Result.success(withContext(Dispatchers.IO) { block() })
    } catch (t: Throwable) {
        Result.failure(t)
    }

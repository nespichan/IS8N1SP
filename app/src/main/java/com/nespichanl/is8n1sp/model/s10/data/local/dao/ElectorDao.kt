package com.nespichanl.is8n1sp.model.s10.data.local.dao

import androidx.room.*
import com.nespichanl.is8n1sp.model.s10.data.local.entity.Elector
import kotlinx.coroutines.flow.Flow

@Dao
interface ElectorDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(elector: Elector): Long   // ALTA (falla si DNI existe por índice único)

    @Update
    suspend fun update(elector: Elector): Int    // MODIFICAR por id (buscamos por DNI y pisamos)

    @Query("DELETE FROM elector WHERE dni = :dni")
    suspend fun deleteByDni(dni: String): Int    // BAJA segura

    @Query("SELECT * FROM elector WHERE dni = :dni LIMIT 1")
    suspend fun getByDni(dni: String): Elector?

    @Query("SELECT * FROM elector ORDER BY nombre")
    fun streamAll(): Flow<List<Elector>>
}

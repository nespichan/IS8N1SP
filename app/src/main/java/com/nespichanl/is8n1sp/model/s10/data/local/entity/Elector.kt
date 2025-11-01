package com.nespichanl.is8n1sp.model.s10.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "elector",
    indices = [Index(value = ["dni"], unique = true)]
)
data class Elector(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dni: String,                      // 8 dígitos
    val nombre: String,
    val colegio: String,
    val mesa: String
)

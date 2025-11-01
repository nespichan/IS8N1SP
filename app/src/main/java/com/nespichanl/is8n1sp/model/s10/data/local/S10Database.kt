package com.nespichanl.is8n1sp.model.s10.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nespichanl.is8n1sp.model.s10.data.local.dao.ElectorDao
import com.nespichanl.is8n1sp.model.s10.data.local.entity.Elector

@Database(
    entities = [Elector::class],
    version = 1,
    exportSchema = true
)
abstract class S10Database : RoomDatabase() {
    abstract fun electorDao(): ElectorDao

    companion object {
        fun build(context: Context): S10Database =
            Room.databaseBuilder(context, S10Database::class.java, "s10.db")
                .fallbackToDestructiveMigration()   // para la tarea; en prod usa Migrations
                .build()
    }
}

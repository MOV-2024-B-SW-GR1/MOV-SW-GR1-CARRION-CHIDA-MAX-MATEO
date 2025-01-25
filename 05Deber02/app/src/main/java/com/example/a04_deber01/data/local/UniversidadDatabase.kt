package com.example.a04_deber01.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.a04_deber01.data.model.Carrera
import com.example.a04_deber01.data.model.Facultad

@Database(
    entities = [Facultad::class, Carrera::class],
    version = 3,
    exportSchema = false
)
abstract class UniversidadDatabase : RoomDatabase() {

    abstract fun facultadDao(): FacultadDao
    abstract fun carreraDao(): CarreraDao

    companion object {
        @Volatile
        private var INSTANCE: UniversidadDatabase? = null


        fun getDatabase(context: Context): UniversidadDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UniversidadDatabase::class.java,
                    "universidad_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

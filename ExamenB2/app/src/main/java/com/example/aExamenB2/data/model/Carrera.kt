package com.example.a04_deber01.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "carreras")
data class Carrera(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    @ColumnInfo(name = "anio_inicio") val anioInicio: Int,
    val acreditada: Boolean,
    @ColumnInfo(name = "creditos_totales") val creditosTotales: Int,
    val mensualidad: Double,
    @ColumnInfo(name = "facultad_id") val facultadId: Int
)
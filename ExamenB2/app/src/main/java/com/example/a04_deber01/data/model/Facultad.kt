package com.example.a04_deber01.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "facultades")
data class Facultad(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombre: String,
    @ColumnInfo(name = "fecha_creacion") val fechaCreacion: String,
    val activa: Boolean,
    @ColumnInfo(name = "numero_departamentos") val numeroDepartamentos: Int,
    @ColumnInfo(name = "presupuesto_anual") val presupuestoAnual: Double
)
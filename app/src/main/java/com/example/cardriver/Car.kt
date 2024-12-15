package com.example.cardriver

import androidx.room.*

@Entity
data class Car(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "location") val location: String?,
    @ColumnInfo(name = "yearManufacture") val yearManufacture: String?,
    @ColumnInfo(name = "carMake") val carMake: String?,
    @ColumnInfo(name = "transmission") val transmission: String?,
    @ColumnInfo(name = "mileage") val mileage: Int,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "imagesB64") val imagesB64: String? // массив из B64 строк, который сам закодирован под B64
)

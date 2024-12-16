package com.example.cardriver

import androidx.room.*

@Entity
data class Rent(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "userLogin") val userID: String,
    @ColumnInfo(name = "carID") val carID: Int,
    @ColumnInfo(name = "carRentDateStart") val carRentDateStart: Long, // in UNIX
    @ColumnInfo(name = "carRentDateEnd") val carRentDateEnd: Long,
)

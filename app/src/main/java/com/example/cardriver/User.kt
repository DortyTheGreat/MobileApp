package com.example.cardriver

import androidx.room.*

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name = "pass") val pass: String?
)

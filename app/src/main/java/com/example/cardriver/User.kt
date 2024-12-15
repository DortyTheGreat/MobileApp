package com.example.cardriver

import androidx.room.*

@Entity
data class User(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "login") val login: String?,
    @ColumnInfo(name = "pass") val pass: String?,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "surname") val surname: String?,
    @ColumnInfo(name = "patronymic") val patronymic: String?,
    @ColumnInfo(name = "gender") val gender: String?, // 'male' or 'female'
    @ColumnInfo(name = "joinDate") val joinDate: String?,
    @ColumnInfo(name = "profileB64") val profileB64: String?,
    @ColumnInfo(name = "favoriteCarsB64") val favoriteCarsB64: String?
)

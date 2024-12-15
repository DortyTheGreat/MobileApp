package com.example.cardriver

import androidx.room.*



@Database(entities = [User::class, Car::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun CarDao(): CarDao
}
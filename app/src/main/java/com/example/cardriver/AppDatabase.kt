package com.example.cardriver

import androidx.room.*



@Database(entities = [User::class, Car::class, Rent::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun carDao(): CarDao
    abstract fun rentDao(): RentDao
}
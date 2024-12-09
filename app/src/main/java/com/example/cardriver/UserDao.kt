package com.example.cardriver

import androidx.room.*


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE login LIKE :login_ AND " +
            "pass LIKE :pass_ LIMIT 1")
    fun findByName(login_: String, pass_: String): User

    @Query("INSERT INTO user (login, pass)" +
            "VALUES (:login_, :pass_)")
    fun Add(login_: String, pass_: String)

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}
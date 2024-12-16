package com.example.cardriver

import androidx.room.*


@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE login LIKE :login_ LIMIT 1")
    fun findByLogin(login_: String): User

    @Query("SELECT * FROM user WHERE login LIKE :login_ AND " +
            "pass LIKE :pass_ LIMIT 1")
    fun findByLoginPass(login_: String, pass_: String): User

    @Query("INSERT INTO user (login, pass, name, surname, patronymic, gender, profileB64)" +
            "VALUES (:login_, :pass_, :name_, :surname_, :patronymic_, :gender_, :profileB64_)")
    fun Add(login_: String?, pass_: String?, name_: String?, surname_: String?, patronymic_: String?,
            gender_ : String?, profileB64_ : String?)


    @Query("UPDATE user SET favoriteCarsB64 = :NewFavStr WHERE login LIKE :login_")
    fun updateFavorite(login_: String, NewFavStr: String): Int

    @Query("UPDATE user SET pass = :pass_ WHERE login LIKE :login_")
    fun updatePass(login_: String, pass_: String): Int

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
}
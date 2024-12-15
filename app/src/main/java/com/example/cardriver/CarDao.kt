package com.example.cardriver

import androidx.room.*


@Dao
interface CarDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<Car>
    /*
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

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
     */
}
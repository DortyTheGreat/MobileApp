package com.example.cardriver

import androidx.room.*


@Dao
interface RentDao {
    @Query("SELECT * FROM rent")
    fun getAll(): List<Rent>

    @Query("INSERT INTO rent (userLogin, carID, carRentDateStart, carRentDateEnd)" +
            "VALUES (:userLogin_, :carID_, :carRentDateStart_, :carRentDateEnd_)")
    fun Add(userLogin_: String, carID_ : Int, carRentDateStart_ : Long, carRentDateEnd_ : Long)

    @Query("SELECT * FROM rent WHERE userLogin LIKE :login_")
    fun getAllByLogin(login_: String): List<Rent>

    @Query("SELECT * FROM rent WHERE uid LIKE :UID LIMIT 1")
    fun findByUID(UID: Int): Rent

    @Query("DELETE FROM rent WHERE uid = :UID")
    fun removeByUID(UID: Int): Int

    /*
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

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)
     */
}
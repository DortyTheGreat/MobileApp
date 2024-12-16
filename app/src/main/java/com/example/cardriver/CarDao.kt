package com.example.cardriver

import androidx.room.*


@Dao
interface CarDao {
    @Query("SELECT * FROM car")
    fun getAll(): List<Car>

    @Query("INSERT INTO car (location, yearManufacture, mark, model, transmission, mileage, description," +
            " ownerLogin, imagesB64)" +
            "VALUES (:location_, :yearManufacture_, :mark_, :model_, :transmission_, :mileage_, " +
            ":description_, :ownerLogin_, :imagesB64_)")
    fun Add(location_: String?, yearManufacture_: String?, mark_: String?, model_: String?, transmission_: String?,
            mileage_ : Int, description_ : String?, ownerLogin_ : String?, imagesB64_ : String?)

    @Query("SELECT * FROM car WHERE uid LIKE :UID LIMIT 1")
    fun findByUID(UID: Int): Car

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
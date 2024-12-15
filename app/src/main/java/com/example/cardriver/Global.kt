package com.example.cardriver

import androidx.room.ColumnInfo

object Global {
    var current_session_email: String? = null

    // Данные для регистрации
    var login: String? = null;
    var pass: String? = null;
    var name: String? = null;
    var surname: String? = null;
    var patronymic: String? = null;
    var gender: String? = null;
    var profileB64: String? = null;

    fun clear(){
        current_session_email = login
        login = null
        pass = null
        name = null
        surname = null
        patronymic = null
        gender = null
        profileB64 = null
    }

    // Данные по аренде
    var location: String? = null;
    var yearManufacture: String? = null;
    var mark: String? = null;
    var model: String? = null;
    var transmission: String? = null;
    var mileage: Int = 0;
    var description: String? = "default desc";
    var ownerID: Int = 0;
    var imagesB64: String? = null; // массив из B64 строк, который сам закодирован под B64

    fun clearCar(){




        location = null;
        yearManufacture = null;
        mark = null;
        model = null;
        transmission = null;
        mileage = 0;
        description = "default desc";
        imagesB64 = null; // массив из B64 строк, который сам закодирован под B64
    }
}


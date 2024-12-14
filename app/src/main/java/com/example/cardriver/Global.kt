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
}


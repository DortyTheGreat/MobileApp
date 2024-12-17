package com.example.cardriver

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar

class Profile : AppCompatActivity() {
    private lateinit var button_reconnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<LinearLayout>(R.id.change_pass_ll).setOnClickListener{
            startActivity(Intent(this, ChangePassword::class.java))
        }

        try {
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()

            val userDao = db.userDao()

            var email: String = "123";
            if (Global.current_session_email == null) return
            else email = Global.current_session_email!!;
            val a = userDao.findByLogin(email)

            if (a == null) return

            findViewById<TextView>(R.id.Name_display).setText(a.surname + " " + a.name + " " + a.patronymic)
            findViewById<TextView>(R.id.Email_display).setText(a.login)
            findViewById<TextView>(R.id.Gmail_display).setText(a.login)

            findViewById<TextView>(R.id.Gender_display).setText("Неопределено")

            if (a.gender == "male")
                findViewById<TextView>(R.id.Gender_display).setText("Мужской")
            if (a.gender == "female")
                findViewById<TextView>(R.id.Gender_display).setText("Женский")
            //change_pass_ll

            val base64Image = a.profileB64

            // Удалите префикс "data:image/png;base64," если он есть
            val base64String = base64Image?.substringAfter(",")

            // Декодируйте строку Base64 в массив байтов
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)

            // Преобразуйте массив байтов в Bitmap
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            // Установите Bitmap в ImageView
            findViewById<ImageView>(R.id.Profile_display).setImageBitmap(decodedByte)


            findViewById<LinearLayout>(R.id.leave_ll).setOnClickListener{
                Global.current_session_email = null
                startActivity(Intent(this, Join::class.java))
                finish()
            }

        }catch (e: Exception) {
            Snackbar.make(findViewById(R.id.main), e.toString(), Snackbar.LENGTH_SHORT).show()
            return
        }

    }


}
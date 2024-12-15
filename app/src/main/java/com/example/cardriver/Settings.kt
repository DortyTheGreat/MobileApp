package com.example.cardriver

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowInsetsCompat

import androidx.activity.enableEdgeToEdge

import androidx.core.view.ViewCompat
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar

import android.util.Base64
import android.graphics.BitmapFactory
import android.widget.LinearLayout
import com.google.android.material.bottomnavigation.BottomNavigationView

class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)

        findViewById<LinearLayout>(R.id.Rental_layout).setOnClickListener{
            startActivity(Intent(this, Rental1::class.java))
            finish()
        }

        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.navigation_settings

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    // Respond to navigation item 1 reselection
                    startActivity(Intent(this, Booking::class.java))
                    true
                }
                else -> false
            }
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

            val base64Image = a.profileB64

            // Удалите префикс "data:image/png;base64," если он есть
            val base64String = base64Image?.substringAfter(",")

            // Декодируйте строку Base64 в массив байтов
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)

            // Преобразуйте массив байтов в Bitmap
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            // Установите Bitmap в ImageView
            findViewById<ImageView>(R.id.Profile_display).setImageBitmap(decodedByte)

        }catch (e: Exception) {
            //Snackbar.make(findViewById(R.id.main), e.toString(), Snackbar.LENGTH_SHORT).show()
            return
        }
    }



}
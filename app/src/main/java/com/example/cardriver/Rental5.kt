package com.example.cardriver

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.cardriver.Register3.Companion.PASSPORT_PHOTO_REQUEST_CODE

class Rental5 : AppCompatActivity() {
    private lateinit var button_reconnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rental5)


        button_reconnect = findViewById<Button>(R.id.button_start)

        button_reconnect.setOnClickListener {
            startActivity(Intent(this, Settings::class.java))
            finish()
        }
    }

}
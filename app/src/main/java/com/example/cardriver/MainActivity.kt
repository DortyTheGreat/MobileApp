package com.example.cardriver

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.util.Base64
import android.graphics.BitmapFactory
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        /**
         * https://www.base64-image.de/ <- to test
        // Ваша строковая переменная с Base64 закодированным изображением
        val base64Image = "data:image/png;base64,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"

        // Удалите префикс "data:image/png;base64," если он есть
        val base64String = base64Image.substringAfter(",")

        // Декодируйте строку Base64 в массив байтов
        val decodedString = Base64.decode(base64String, Base64.DEFAULT)

        // Преобразуйте массив байтов в Bitmap
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

        // Установите Bitmap в ImageView
        findViewById<ImageView>(R.id.splshscrn).setImageBitmap(decodedByte);
        */


        Handler(Looper.getMainLooper()).postDelayed({
            if (isNetworkAvailable()) {
                startActivity(Intent(this, Onboarding::class.java))
            } else {
                startActivity(Intent(this, NoEthernet::class.java))
            }
            finish()
        }, 3000)
    }


    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities != null && (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
    }
}
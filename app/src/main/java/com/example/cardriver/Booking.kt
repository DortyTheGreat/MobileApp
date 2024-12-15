package com.example.cardriver

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class Booking : AppCompatActivity() {
    private lateinit var button_reconnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking)

        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.navigation_home

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_settings -> {
                    // Respond to navigation item 1 reselection
                    startActivity(Intent(this, Settings::class.java))
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

            val carDao = db.carDao()
            Snackbar.make(findViewById(R.id.main), "length: " + carDao.getAll().size.toString(), Snackbar.LENGTH_SHORT).show()
            val data = carDao.getAll()

            val linearLayout = findViewById<LinearLayout>(R.id.car_list)
            val inflater = LayoutInflater.from(this)

            for (item in data) {
                // Инфлейтим шаблон
                val itemView = inflater.inflate(R.layout.item_car, linearLayout, false)

                // Настраиваем текст в TextView
                val textView_car_model = itemView.findViewById<TextView>(R.id.car_model)
                textView_car_model.text = item.model

                val textView_car_mark = itemView.findViewById<TextView>(R.id.car_mark)
                textView_car_mark.text = item.mark

                /*
                // Настраиваем действие кнопки
                val button = itemView.findViewById<Button>(R.id.item_button)
                button.setOnClickListener {
                    Toast.makeText(this, "$item кнопка нажата", Toast.LENGTH_SHORT).show()
                }
                 */

                // Добавляем инфлейтированный вид в LinearLayout
                linearLayout.addView(itemView)
            }

        } catch (e: Exception) {
            Snackbar.make(findViewById(R.id.main), e.toString(), Snackbar.LENGTH_SHORT).show()
            return
        }




    }


}
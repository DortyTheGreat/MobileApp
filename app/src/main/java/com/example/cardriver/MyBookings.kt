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
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

class MyBookings : AppCompatActivity() {
    private lateinit var button_reconnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_bookings)


        try {

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()

            val rentDao = db.rentDao()
            val carDao = db.carDao()
            //Snackbar.make(findViewById(R.id.main), "length: " + rentDao.getAll().size.toString(), Snackbar.LENGTH_SHORT).show()
            val data = rentDao.getAllByLogin(Global.current_session_email!!)

            val linearLayout = findViewById<LinearLayout>(R.id.book_list)
            val inflater = LayoutInflater.from(this)

            for (item in data) {

                if (item.userID != Global.current_session_email) continue

                // Инфлейтим шаблон
                val itemView = inflater.inflate(R.layout.item_booking, linearLayout, false)

                // Настраиваем текст в TextView
                val textView_car_model = itemView.findViewById<TextView>(R.id.car_name)
                textView_car_model.text = carDao.findByUID(item.carID).model

                val textView_book_data = itemView.findViewById<TextView>(R.id.book_data)

                ZoneId.systemDefault()

                // Господи, как же ненавижу тот факт, что чел в красной футболке был прав...
                // ZoneOffset.UTC
                // ZoneId.systemDefault().rules.getOffset(LocalDateTime.now())
                val restoredDateTime = LocalDateTime.ofEpochSecond(item.carRentDateStart, 0, ZoneOffset.UTC )
                val formatter = DateTimeFormatter.ofPattern("HH:mm dd MMMM yyyy", Locale("ru"))

                val formattedCurrentDateTime = restoredDateTime.format(formatter)


                textView_book_data.text = "Начало: " + formattedCurrentDateTime

                itemView.findViewById<LinearLayout>(R.id.item_booking_ll).setOnClickListener{
                    val intent = Intent(this, BookingCancel::class.java)
                    intent.putExtra("book_id", item.uid);
                    startActivity(intent)
                }

                linearLayout.addView(itemView)

            }


        } catch (e: Exception) {
            Snackbar.make(findViewById(R.id.main), e.toString(), Snackbar.LENGTH_SHORT).setTextMaxLines(10).show()
            return
        }




    }


}
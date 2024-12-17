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
import java.time.temporal.ChronoUnit
import java.util.Locale

class BookingCancel : AppCompatActivity() {
    private lateinit var button_reconnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking_cancel)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.backArrow2).setOnClickListener {
            onBackPressed() // Вернуться назад
        }

        try {

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()



            val carDao = db.carDao()
            val userDao = db.userDao()
            val rentDao = db.rentDao()
            //Snackbar.make(findViewById(R.id.main), "length: " + carDao.getAll().size.toString(), Snackbar.LENGTH_SHORT).show()

            val item_rent = rentDao.findByUID( intent.getIntExtra("book_id", 0))

            val item_car = carDao.findByUID(
                item_rent.carID
            )

            val item_user = userDao.findByLogin(
                item_rent.userID
            )

            findViewById<TextView>(R.id.car_adress).text = item_car.location
            findViewById<TextView>(R.id.car_model).text = item_car.model
            findViewById<TextView>(R.id.car_mark).text = item_car.mark
            findViewById<TextView>(R.id.car_transmission).text = item_car.transmission
            findViewById<TextView>(R.id.car_owner).text = item_user.surname + " " + item_user.name + item_user.patronymic

            val restoredDateTimeStart = LocalDateTime.ofEpochSecond(item_rent.carRentDateStart, 0, ZoneOffset.UTC )
            val restoredDateTimeEnd = LocalDateTime.ofEpochSecond(item_rent.carRentDateEnd, 0, ZoneOffset.UTC )
            val formatter = DateTimeFormatter.ofPattern("HH:mm dd MMMM yyyy", Locale("ru"))

            val formattedCurrentDateTimeStart = restoredDateTimeStart.format(formatter)
            val formattedCurrentDateTimeEnd = restoredDateTimeEnd.format(formatter)

            findViewById<TextView>(R.id.car_rent_start).text = formattedCurrentDateTimeStart
            findViewById<TextView>(R.id.car_rent_end).text = formattedCurrentDateTimeEnd




            val textView_car_image = findViewById<ImageView>(R.id.car_image)
            val lst : List<String> = decodeBase64StringToList(item_car.imagesB64!!)

            // Удалите префикс "data:image/png;base64," если он есть
            val base64String = lst[0].substringAfter(",")

            // Декодируйте строку Base64 в массив байтов
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)

            // Преобразуйте массив байтов в Bitmap
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            // Установите Bitmap в ImageView
            textView_car_image.setImageBitmap(decodedByte);





            findViewById<Button>(R.id.button_start).setOnClickListener{

                val rentDao = db.rentDao()
                rentDao.removeByUID(intent.getIntExtra("book_id", 0))

                startActivity(Intent(this, MyBookings::class.java))

            }


        } catch (e: Exception) {
            Snackbar.make(findViewById(R.id.main), e.toString(), Snackbar.LENGTH_SHORT).setTextMaxLines(10).show()
            return
        }


    }

    fun decodeBase64StringToList(base64String: String): List<String> {
        // Декодируем base64 строку в массив байтов
        val byteArray = Base64.decode(base64String, Base64.DEFAULT)

        // Преобразуем массив байтов в строку
        val combinedString = byteArray.toString(Charsets.UTF_8)

        // Разделяем строку на элементы списка
        return combinedString.split("#")
    }


}
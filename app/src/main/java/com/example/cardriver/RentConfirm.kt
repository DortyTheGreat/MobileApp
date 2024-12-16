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
import java.time.format.DateTimeFormatter
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Locale
import java.time.ZoneOffset

class RentConfirm : AppCompatActivity() {
    private lateinit var button_reconnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rent_confirm)


        try {

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()



            val carDao = db.carDao()
            Snackbar.make(findViewById(R.id.main), "length: " + carDao.getAll().size.toString(), Snackbar.LENGTH_SHORT).show()
            val item = carDao.findByUID(intent.getIntExtra("car_id", 0))

            findViewById<TextView>(R.id.car_adress).text = item.location
            findViewById<TextView>(R.id.car_model).text = item.model
            findViewById<TextView>(R.id.car_mark).text = item.mark
            findViewById<TextView>(R.id.car_transmission).text = item.transmission



            val textView_car_image = findViewById<ImageView>(R.id.car_image)
            val lst : List<String> = decodeBase64StringToList(item.imagesB64!!)

            // Удалите префикс "data:image/png;base64," если он есть
            val base64String = lst[0].substringAfter(",")

            // Декодируйте строку Base64 в массив байтов
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)

            // Преобразуйте массив байтов в Bitmap
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            // Установите Bitmap в ImageView
            textView_car_image.setImageBitmap(decodedByte);


            val currentDateTime = LocalDateTime.now(ZoneId.systemDefault())

            // Получение даты и времени через три дня
            val futureDateTime = currentDateTime.plus(3, ChronoUnit.DAYS)

            // Форматирование даты и времени в нужный формат
            val formatter = DateTimeFormatter.ofPattern("HH:mm dd MMMM yyyy", Locale("ru"))

            val formattedCurrentDateTime = currentDateTime.format(formatter)
            val formattedFutureDateTime = futureDateTime.format(formatter)

            findViewById<TextView>(R.id.car_rent_start).text = formattedCurrentDateTime
            findViewById<TextView>(R.id.car_rent_end).text = formattedFutureDateTime

            findViewById<Button>(R.id.button_start).setOnClickListener{

                val rentDao = db.rentDao()
                rentDao.Add(
                    Global.current_session_email!!, intent.getIntExtra("car_id", 0),
                    currentDateTime.toEpochSecond(ZoneOffset.UTC ),
                    futureDateTime.toEpochSecond(ZoneOffset.UTC) )
                    startActivity(Intent(this, RentDone::class.java))

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
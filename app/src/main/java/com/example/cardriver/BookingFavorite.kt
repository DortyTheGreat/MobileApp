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
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Room
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar
import android.util.Base64
import android.graphics.BitmapFactory
import android.widget.EditText

class BookingFavorite : AppCompatActivity() {
    private lateinit var button_reconnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_booking_favorite)



        findViewById<BottomNavigationView>(R.id.bottom_navigation).selectedItemId = R.id.navigation_favorites

        findViewById<BottomNavigationView>(R.id.bottom_navigation).setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.navigation_settings -> {
                    // Respond to navigation item 1 reselection
                    startActivity(Intent(this, Settings::class.java))
                    true
                }
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

            val carDao = db.carDao()
            //Snackbar.make(findViewById(R.id.main), "length: " + carDao.getAll().size.toString(), Snackbar.LENGTH_SHORT).show()
            val data = carDao.getAll()

            val linearLayout = findViewById<LinearLayout>(R.id.car_list)
            val inflater = LayoutInflater.from(this)

            showFilteredData(data, inflater, linearLayout, "", true, db)


        } catch (e: Exception) {
            Snackbar.make(findViewById(R.id.main), e.toString(), Snackbar.LENGTH_SHORT).show()
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


    fun showFilteredData(data : List<Car>, inflater: LayoutInflater, linearLayout : LinearLayout,
                         stringSearchRequest : String, onlyFavorite : Boolean, db : AppDatabase){

        linearLayout.removeAllViews()
        for (item in data) {
            if (stringSearchRequest != "")
                if (item.mark != stringSearchRequest) continue

            if (onlyFavorite){
                val userDao = db.userDao()

                if (userDao.findByLogin(Global.current_session_email!!).favoriteCarsB64 == null) continue

                val lst : List<String> = decodeBase64StringToList(userDao.findByLogin(Global.current_session_email!!).favoriteCarsB64!!)
                if (!lst.contains(item.uid.toString())) continue
            }

            // Инфлейтим шаблон
            val itemView = inflater.inflate(R.layout.item_car, linearLayout, false)

            // Настраиваем текст в TextView
            val textView_car_model = itemView.findViewById<TextView>(R.id.car_model)
            textView_car_model.text = item.model

            val textView_car_mark = itemView.findViewById<TextView>(R.id.car_mark)
            textView_car_mark.text = item.mark

            val textView_car_transmission = itemView.findViewById<TextView>(R.id.car_transmission)
            textView_car_transmission.text = item.transmission

            val textView_car_image = itemView.findViewById<ImageView>(R.id.car_image)
            val lst : List<String> = decodeBase64StringToList(item.imagesB64!!)

            // Удалите префикс "data:image/png;base64," если он есть
            val base64String = lst[0].substringAfter(",")

            // Декодируйте строку Base64 в массив байтов
            val decodedString = Base64.decode(base64String, Base64.DEFAULT)

            // Преобразуйте массив байтов в Bitmap
            val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

            // Установите Bitmap в ImageView
            textView_car_image.setImageBitmap(decodedByte);



            // Настраиваем действие кнопки
            val button = itemView.findViewById<Button>(R.id.book_button)
            button.setOnClickListener {

                val intent = Intent(this, RentConfirm::class.java)
                intent.putExtra("car_id", item.uid);
                startActivity(intent)
            }

            val button2 = itemView.findViewById<Button>(R.id.details_button)
            button2.setOnClickListener {
                val intent = Intent(this, Details::class.java)
                intent.putExtra("car_id", item.uid);
                startActivity(intent)
            }


            // Добавляем инфлейтированный вид в LinearLayout
            linearLayout.addView(itemView)
        }
    }

}
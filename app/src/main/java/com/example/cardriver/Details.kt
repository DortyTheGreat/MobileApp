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
import com.google.android.material.snackbar.Snackbar

class Details : AppCompatActivity() {
    private lateinit var button_reconnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_details)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // car_adress, car_description, car_name, photo



        try {

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()



            val carDao = db.carDao()
            //Snackbar.make(findViewById(R.id.main), "length: " + carDao.getAll().size.toString(), Snackbar.LENGTH_SHORT).show()
            val item = carDao.findByUID(intent.getIntExtra("car_id", 0))

            findViewById<TextView>(R.id.car_adress).text = item.location
            findViewById<TextView>(R.id.car_description).text = item.description
            findViewById<TextView>(R.id.car_name).text = item.model


            val lst : List<String> = decodeBase64StringToList(item.imagesB64!!)

            val linearLayout = findViewById<LinearLayout>(R.id.photo_container)
            val inflater = LayoutInflater.from(this)

            findViewById<Button>(R.id.button_start).setOnClickListener{
                val intent2 = Intent(this, RentConfirm::class.java)
                intent2.putExtra("car_id", intent.getIntExtra("car_id", 0));
                startActivity(intent2)
            }

            for (item in lst) {


                // Инфлейтим шаблон
                val itemView = inflater.inflate(R.layout.item_car_image, linearLayout, false)

                // Удалите префикс "data:image/png;base64," если он есть
                val base64String = item.substringAfter(",")

                // Декодируйте строку Base64 в массив байтов
                val decodedString = Base64.decode(base64String, Base64.DEFAULT)

                // Преобразуйте массив байтов в Bitmap
                val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)

                // Установите Bitmap в ImageView
                itemView.findViewById<ImageView>(R.id.car_image).setImageBitmap(decodedByte);
                linearLayout.addView(itemView)
            }




            if (isFavorited(db, item)){
                findViewById<ImageView>(R.id.favorite).setImageResource(R.drawable.ic_love_full)
            }else{
                findViewById<ImageView>(R.id.favorite).setImageResource(R.drawable.ic_love_empty)
            }

            findViewById<ImageView>(R.id.favorite).setOnClickListener{

                changeFav(db, item)
                if (isFavorited(db, item)){
                    findViewById<ImageView>(R.id.favorite).setImageResource(R.drawable.ic_love_full)
                }else{
                    findViewById<ImageView>(R.id.favorite).setImageResource(R.drawable.ic_love_empty)
                }
            }


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

    fun isFavorited(db : AppDatabase, item : Car) : Boolean{

        val userDao = db.userDao()

        if (userDao.findByLogin(Global.current_session_email!!).favoriteCarsB64 == null) return false

        val lst : List<String> = decodeBase64StringToList(userDao.findByLogin(Global.current_session_email!!).favoriteCarsB64!!)
        return lst.contains(item.uid.toString())


    }

    fun changeFav(db : AppDatabase, item : Car){

        var stringList : MutableList<String> = arrayListOf()

        val userDao = db.userDao()
        if (userDao.findByLogin(Global.current_session_email!!).favoriteCarsB64 != null){
            stringList = decodeBase64StringToList(userDao.findByLogin(Global.current_session_email!!).favoriteCarsB64!!).toMutableList()
        }

        if (!isFavorited(db, item)){
            stringList.add(item.uid.toString())
        }else{
            stringList.remove(item.uid.toString())
        }

        val combinedString = stringList.joinToString(separator = "#")
        val byteArray = combinedString.toByteArray(Charsets.UTF_8)



        val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)
        userDao.updateFavorite(Global.current_session_email!!, base64String)
    }


}
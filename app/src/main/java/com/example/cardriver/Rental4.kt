package com.example.cardriver

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
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
import com.example.cardriver.Register3.Companion.LICENSE_PHOTO_REQUEST_CODE
import com.example.cardriver.Register3.Companion.PASSPORT_PHOTO_REQUEST_CODE
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class Rental4 : AppCompatActivity() {
    private lateinit var button_reconnect: Button

    var S0: String? = null;
    var S1: String? = null;
    var S2: String? = null;
    var S3: String? = null;
    var S4: String? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rental4)


        button_reconnect = findViewById<Button>(R.id.button_start)

        button_reconnect.setOnClickListener {
            startActivity(Intent(this, Settings::class.java))

            val stringList: MutableList<String> = mutableListOf()
            if (S0 != null) stringList.add(S0!!)
            if (S1 != null) stringList.add(S1!!)
            if (S2 != null) stringList.add(S2!!)
            if (S3 != null) stringList.add(S3!!)
            if (S4 != null) stringList.add(S4!!)

            val combinedString = stringList.joinToString(separator = "#")
            val byteArray = combinedString.toByteArray(Charsets.UTF_8)
            val base64String = Base64.encodeToString(byteArray, Base64.DEFAULT)
            Global.imagesB64 = base64String

            try {

                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "database-name"
                ).allowMainThreadQueries().build()

                val CarDao = db.carDao()

                /// location_: String?, yearManufacture_: String?, mark_: String?, model_: String?, transmission_: String?,
                //            mileage_ : Int, description_ : String?, ownerID_ : Int, imagesB64_ : String?
                val a = CarDao.Add(Global.location, Global.yearManufacture, Global.mark, Global.model,
                    Global.transmission, Global.mileage, Global.description, Global.current_session_email, Global.imagesB64)

                Global.clear()
                startActivity(Intent(this, Rental5::class.java))


            } catch (e: Exception) {
                Snackbar.make(findViewById(R.id.main), e.toString(), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Global.clearCar()



            finish()
        }
        button_reconnect.setEnabled(false)

        findViewById<ImageView>(R.id.photo).setOnClickListener {
            selectImageFromGallery(0)
        }
        findViewById<ImageView>(R.id.photo1).setOnClickListener {
            selectImageFromGallery(1)
        }
        findViewById<ImageView>(R.id.photo2).setOnClickListener {
            selectImageFromGallery(2)
        }
        findViewById<ImageView>(R.id.photo3).setOnClickListener {
            selectImageFromGallery(3)
        }
        findViewById<ImageView>(R.id.photo4).setOnClickListener {
            selectImageFromGallery(4)
        }
    }

    // Функция для выбора изображения из галереи
    private fun selectImageFromGallery(requestCode: Int) {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, requestCode)
    }

    // Обработка результата выбора изображения
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && data != null) {
            val selectedImageUri = data.data
            when (requestCode) {
                0 -> {
                    findViewById<ImageView>(R.id.photo).setImageURI(selectedImageUri)
                    val imageUri: Uri? = data.data
                    imageUri?.let {
                        S0 = convertUriToBase64(it)
                    }
                }
                1 -> {
                    findViewById<ImageView>(R.id.photo1).setImageURI(selectedImageUri)
                    val imageUri: Uri? = data.data
                    imageUri?.let {
                        S1 = convertUriToBase64(it)
                    }
                }
                2 -> {
                    findViewById<ImageView>(R.id.photo2).setImageURI(selectedImageUri)
                    val imageUri: Uri? = data.data
                    imageUri?.let {
                        S2 = convertUriToBase64(it)
                    }
                }
                3 -> {
                    findViewById<ImageView>(R.id.photo3).setImageURI(selectedImageUri)
                    val imageUri: Uri? = data.data
                    imageUri?.let {
                        S3 = convertUriToBase64(it)
                    }
                }
                4 -> {
                    findViewById<ImageView>(R.id.photo4).setImageURI(selectedImageUri)
                    val imageUri: Uri? = data.data
                    imageUri?.let {
                        S4 = convertUriToBase64(it)
                    }
                }

            }
            check_data()
        }
    }

    // Метод для преобразования URI в Base64
    private fun convertUriToBase64(uri: Uri): String? {
        try {
            val inputStream = contentResolver.openInputStream(uri)
            val bytes = inputStream?.readBytes() ?: return null
            return Base64.encodeToString(bytes, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun check_data(){
        button_reconnect.setEnabled(false)

        if (S0 == null && S1 == null && S2 == null && S3 == null && S4 == null) return

        button_reconnect.setEnabled(true)
    }

}
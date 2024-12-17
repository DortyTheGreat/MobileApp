package com.example.cardriver

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import android.widget.Toast

import android.net.Uri

import android.util.Base64
import android.graphics.BitmapFactory
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar

class Register3 : AppCompatActivity() {

    private lateinit var buttonNext: Button
    private lateinit var uploadPassportPhotoButton: ImageView
    private lateinit var uploadLicensePhotoButton: ImageView
    private lateinit var backArrow: ImageView
    private lateinit var licenseNumberInput: TextInputEditText
    private lateinit var issueDateInput: TextInputEditText
    private lateinit var licenseNumberLayout: TextInputLayout
    private lateinit var issueDateLayout: TextInputLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg3)

        // Инициализация элементов интерфейса
        buttonNext = findViewById(R.id.button8)
        uploadPassportPhotoButton = findViewById(R.id.uploadPassportPhotoButton)
        uploadLicensePhotoButton = findViewById(R.id.uploadLicensePhotoButton)
        backArrow = findViewById(R.id.backArrow)
        licenseNumberInput = findViewById(R.id.TextInputEditText1)
        issueDateInput = findViewById(R.id.zxcc) // Исправьте ID на тот, что используется для TextInputEditText
        licenseNumberLayout = findViewById(R.id.textInputLayout6) // Layout для установки ошибок
        issueDateLayout = findViewById(R.id.textInputLayout7) // Layout для установки ошибок

        findViewById<ImageView>(R.id.backArrow).setOnClickListener {
            onBackPressed() // Вернуться назад
        }

        // Обработка нажатия на кнопку "Далее"
        buttonNext.setOnClickListener {
            val licenseNumber = licenseNumberInput.text.toString()
            val issueDate = issueDateInput.text.toString()

            // Сброс ошибок перед проверкой
            licenseNumberLayout.error = null
            issueDateLayout.error = null

            // Проверка данных перед переходом к следующему шагу
            if (licenseNumber.isEmpty()) {
                licenseNumberLayout.error = "Введите номер удостоверения"
            }
            if (issueDate.isEmpty()) {
                issueDateLayout.error = "Введите дату выдачи"
            }

            if (licenseNumber.isNotEmpty() && issueDate.isNotEmpty()) {
                // Перейти на следующий экран
                val intent = Intent(this, Booking::class.java)

                try {

                    val db = Room.databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java, "database-name"
                    ).allowMainThreadQueries().build()

                    val userDao = db.userDao()

                    val a = userDao.Add(Global.login, Global.pass, Global.name, Global.surname,
                        Global.patronymic, Global.gender, Global.profileB64)

                    Global.clear()
                    startActivity(Intent(this, Settings::class.java))


                } catch (e: Exception) {
                    Snackbar.make(findViewById(R.id.main), e.toString(), Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                startActivity(intent)
            }
        }

        // Обработка нажатия на стрелку назад
        backArrow.setOnClickListener {
            finish() // Вернуться назад
        }

        // Обработка нажатия на кнопку загрузки фото паспорта
        uploadPassportPhotoButton.setOnClickListener {
            selectImageFromGallery(PASSPORT_PHOTO_REQUEST_CODE)
        }

        // Обработка нажатия на кнопку загрузки фото водительского удостоверения
        uploadLicensePhotoButton.setOnClickListener {
            selectImageFromGallery(LICENSE_PHOTO_REQUEST_CODE)
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
                PASSPORT_PHOTO_REQUEST_CODE -> {

                    //uploadPassportPhotoButton.setImageURI(selectedImageUri)
                    uploadPassportPhotoButton.setImageResource(R.drawable.image_downloaded)

                    val imageUri: Uri? = data.data
                    imageUri?.let {
                        val base64String = convertUriToBase64(it)
                        Global.profileB64 = base64String;
                    }



                }
                LICENSE_PHOTO_REQUEST_CODE -> {
                    //uploadLicensePhotoButton.setImageURI(selectedImageUri)
                    uploadLicensePhotoButton.setImageResource(R.drawable.image_downloaded)
                }
            }
        }
    }

    companion object {
        const val PASSPORT_PHOTO_REQUEST_CODE = 1
        const val LICENSE_PHOTO_REQUEST_CODE = 2
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
}

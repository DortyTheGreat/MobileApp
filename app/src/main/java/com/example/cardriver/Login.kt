package com.example.cardriver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.text.InputType

import androidx.room.*
import com.google.android.material.snackbar.Snackbar
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Получаем главный layout с id main
        val mainLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)

        // Устанавливаем listener для обработки системных вставок (system bars)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Получаем элементы TextInputEditText внутри TextInputLayout
        val email = findViewById<TextInputEditText>(R.id.textInputEditText_surname)
        val pass = findViewById<TextInputEditText>(R.id.textInputEditText_date)

        val button_login_google = findViewById<Button>(R.id.button_login_google)
        val button_login = findViewById<Button>(R.id.button_login)
        val textView_register = findViewById<TextView>(R.id.textView_register)
        val pass_hide = findViewById<ImageView>(R.id.pass_hide)

        pass.inputType = 131201

        pass_hide.setOnClickListener{
            if (pass.inputType == 131201){
                pass.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }else{
                pass.inputType = 131201
            }
        }

        button_login.setOnClickListener {

            val ema = email.getText().toString()
            val pas = pass.getText().toString()

            if (isValidEmail(ema) && isValidPass(pas) ){

                try {

                    val db = Room.databaseBuilder(
                        applicationContext,
                        AppDatabase::class.java, "database-name"
                    ).allowMainThreadQueries().build()

                    val userDao = db.userDao()

                    val a = userDao.findByName(ema,pas)

                    if (a == null){
                        Snackbar.make(findViewById(R.id.main), "Такого пользователя нет", Snackbar.LENGTH_SHORT).show()
                        return@setOnClickListener
                    }


                    startActivity(Intent(this, Home::class.java))


                } catch (e: Exception) {
                    Snackbar.make(findViewById(R.id.main), e.toString(), Snackbar.LENGTH_SHORT).show()
                    return@setOnClickListener
                }



                //startActivity(Intent(this, Home::class.java))
                //finish()
            }
        }

        button_login_google.setOnClickListener {
            // TODO: google auth
        }

        textView_register.setOnClickListener{
            startActivity(Intent(this, Register1::class.java))
        }



    }

    fun isValidEmail(target : String): Boolean {
        if (target.isEmpty()) {
            return false;
        }

        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();

    }

    fun isValidPass(target : String): Boolean {
        if (target.isEmpty()) {
            return false;
        }

        return true;

    }
}
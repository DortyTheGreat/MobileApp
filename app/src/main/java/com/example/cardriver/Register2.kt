package com.example.cardriver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.ImageView
import android.widget.RadioButton
import com.google.android.material.snackbar.Snackbar
import java.time.format.DateTimeFormatter
import java.time.LocalDate

class Register2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg2)


        val mainLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)


        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.backArrow).setOnClickListener {
            onBackPressed() // Вернуться назад
        }


        val surname = findViewById<TextInputEditText>(R.id.textInputEditText_surname)
        val name = findViewById<TextInputEditText>(R.id.textInputEditText_name)
        val name3 = findViewById<TextInputEditText>(R.id.textInputEditText_name3)
        val birth = findViewById<TextInputEditText>(R.id.textInputEditText_date)

        val button_login = findViewById<Button>(R.id.button_login)


        button_login.setOnClickListener {

            val _surname = surname.getText().toString()
            val _name = name.getText().toString()
            val _birth = birth.getText().toString()
            val gn_m = findViewById<RadioButton>(R.id.radioButton_male).isChecked()
            val gn_f = findViewById<RadioButton>(R.id.radioButton_female).isChecked()

            if (_surname.isEmpty()){
                Snackbar.make(findViewById(R.id.main), "Некорректная Фамилия", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (_name.isEmpty()){
                Snackbar.make(findViewById(R.id.main), "Некорректное имя", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                val localDate = LocalDate.parse(_birth, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            } catch (e: Exception) {
                Snackbar.make(findViewById(R.id.main), "Некорректная дата", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!(gn_m || gn_f)){
                Snackbar.make(findViewById(R.id.main), "Выберите пол", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Global.name = _name;
            Global.surname = _surname;
            Global.patronymic = name3.getText().toString();

            if (gn_m)
                Global.gender = "male";

            if (gn_f)
                Global.gender = "female";

            startActivity(Intent(this, Register3::class.java))
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
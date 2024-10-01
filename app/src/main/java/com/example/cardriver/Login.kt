package com.example.cardriver

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import com.google.android.material.textfield.TextInputLayout

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
        val textInput1 = findViewById<TextInputEditText>(R.id.textInputEditText1)
        val textInput2 = findViewById<TextInputEditText>(R.id.textInputEditText2)

        val button6 = findViewById<Button>(R.id.button6)
        val button7 = findViewById<Button>(R.id.button7)

        button6.setOnClickListener {

        }

        button7.setOnClickListener {

        }
    }
}
package com.example.cardriver

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Button

class Join : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_join)

        // Настройка окна с учётом отступов для системных панелей
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val buttonLog = findViewById<Button>(R.id.button_login)
        buttonLog.setOnClickListener {
            startActivity(Intent(this@Join, Login::class.java))
        }

        val buttonReg = findViewById<Button>(R.id.button_reg)
        buttonReg.setOnClickListener {
            startActivity(Intent(this@Join, Register1::class.java))
        }
    }
}
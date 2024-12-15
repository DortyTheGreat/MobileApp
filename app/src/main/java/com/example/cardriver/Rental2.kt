package com.example.cardriver

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import android.text.TextWatcher
import android.text.Editable

class Rental2 : AppCompatActivity() {
    private lateinit var button_reconnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rental2)


        button_reconnect = findViewById<Button>(R.id.button_start)

        button_reconnect.setEnabled(false)

        findViewById<TextInputEditText>(R.id.textInputEditText_email).addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Действие перед изменением текста
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Действие при изменении текста
                check_data()
            }

            override fun afterTextChanged(s: Editable?) {
                // Действие после изменения текста
            }
        })

        button_reconnect.setOnClickListener {
            Global.location = findViewById<TextInputEditText>(R.id.textInputEditText_email).getText().toString()
            startActivity(Intent(this, Rental3::class.java))
            finish()
        }
    }

    fun check_data(){
        button_reconnect.setEnabled(false)

        if (findViewById<TextInputEditText>(R.id.textInputEditText_email).getText()!!.length < 10) return

        button_reconnect.setEnabled(true)
    }
}
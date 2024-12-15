package com.example.cardriver

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class Rental3 : AppCompatActivity() {
    private lateinit var button_reconnect: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_rental3)


        button_reconnect = findViewById<Button>(R.id.button_start)

        button_reconnect.setEnabled(false)

        val checker = object :
            TextWatcher {
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
        }

        findViewById<TextInputEditText>(R.id.textInputEditText_year).addTextChangedListener(checker)
        findViewById<TextInputEditText>(R.id.textInputEditText_mark).addTextChangedListener(checker)
        findViewById<TextInputEditText>(R.id.textInputEditText_model).addTextChangedListener(checker)
        findViewById<TextInputEditText>(R.id.textInputEditText_mileage).addTextChangedListener(checker)
        findViewById<TextInputEditText>(R.id.textInputEditText_description).addTextChangedListener(checker)


        button_reconnect.setOnClickListener {
            Global.yearManufacture = findViewById<TextInputEditText>(R.id.textInputEditText_year).getText().toString()
            Global.mark = findViewById<TextInputEditText>(R.id.textInputEditText_mark).getText().toString()
            Global.model = findViewById<TextInputEditText>(R.id.textInputEditText_model).getText().toString()
            Global.mileage = findViewById<TextInputEditText>(R.id.textInputEditText_mileage).getText().toString().toInt()
            Global.description = findViewById<TextInputEditText>(R.id.textInputEditText_description).getText().toString()
            Global.transmission = findViewById<Spinner>(R.id.spinner).getSelectedItem().toString()

            startActivity(Intent(this, Rental4::class.java))
            finish()
        }
    }

    // to-do: check data type
    fun check_data(){
        button_reconnect.setEnabled(false)

        if (findViewById<TextInputEditText>(R.id.textInputEditText_year).getText()!!.length < 1) return
        if (findViewById<TextInputEditText>(R.id.textInputEditText_mark).getText()!!.length < 1) return
        if (findViewById<TextInputEditText>(R.id.textInputEditText_model).getText()!!.length < 1) return
        if (findViewById<TextInputEditText>(R.id.textInputEditText_mileage).getText()!!.length < 1) return
        //if (findViewById<TextInputEditText>(R.id.textInputEditText_description).getText()!!.length < 1) return
        //if (findViewById<Spinner>(R.id.spinner).get) return

        button_reconnect.setEnabled(true)
    }
}
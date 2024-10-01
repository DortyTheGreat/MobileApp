package com.example.cardriver

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar
import android.text.InputType
import android.widget.CheckBox

class Register1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg1)

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
        val pass = findViewById<TextInputEditText>(R.id.textInputEditText_name)
        val pass_repeat = findViewById<TextInputEditText>(R.id.textInputEditText_date)

        val button_login = findViewById<Button>(R.id.button_login)

        val pass_hide = findViewById<ImageView>(R.id.pass_hide)
        val pass_hide2 = findViewById<ImageView>(R.id.pass_hide2)
        val rules_cb = findViewById<CheckBox>(R.id.rules_checkbox)


        pass.inputType = 131201
        pass_repeat.inputType = 131201
        pass_hide.setOnClickListener{
            if (pass.inputType == 131201){
                pass.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                pass_repeat.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }else{
                pass.inputType = 131201
                pass_repeat.inputType = 131201
            }
        }

        pass_hide2.setOnClickListener{
            if (pass.inputType == 131201){
                pass.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                pass_repeat.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }else{
                pass.inputType = 131201
                pass_repeat.inputType = 131201
            }
        }

        button_login.setOnClickListener {

            val ema = email.getText().toString()
            val pas = pass.getText().toString()
            val pas2 = pass_repeat.getText().toString()

            if (!isValidEmail(ema)){
                Snackbar.make(findViewById(R.id.main), "Некорректный email", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pas != pas2){
                Snackbar.make(findViewById(R.id.main), "Пароли должны совпадать", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPass(pas)){
                Snackbar.make(findViewById(R.id.main), "Пароль некорректный", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!rules_cb.isChecked()){
                Snackbar.make(findViewById(R.id.main), "Вы обязаны согласиться с правилами", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            startActivity(Intent(this, Register2::class.java))
            finish()
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
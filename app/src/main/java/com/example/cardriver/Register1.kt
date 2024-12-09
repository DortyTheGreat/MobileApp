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
import androidx.room.Room

class Register1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reg1)

        R.id.textInputEditText_pass

        // Получаем главный layout с id main
        val mainLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)

        val backArrow = findViewById<ImageView>(R.id.backArrow) // Стрелка назад

        backArrow.setOnClickListener {
            onBackPressed() // Вернуться назад
        }

        // Устанавливаем listener для обработки системных вставок (system bars)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Получаем элементы TextInputEditText внутри TextInputLayout
        val email = findViewById<TextInputEditText>(R.id.textInputEditText_email)
        val pass = findViewById<TextInputEditText>(R.id.textInputEditText_pass)
        val pass_repeat = findViewById<TextInputEditText>(R.id.textInputEditText_pass_repeat)

        val button_login = findViewById<Button>(R.id.button_login)

        val pass_hide = findViewById<ImageView>(R.id.pass_hide)
        val pass_hide2 = findViewById<ImageView>(R.id.pass_hide2)
        val rules_cb = findViewById<CheckBox>(R.id.checkBox)


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

            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "database-name"
            ).allowMainThreadQueries().build()

            val userDao = db.userDao()

            val a = userDao.findByName(ema,pas)

            if (!(a == null)){
                Snackbar.make(findViewById(R.id.main), "Такая почта уже задействована", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            userDao.Add(ema, pas)

            startActivity(Intent(this, Register2::class.java))
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
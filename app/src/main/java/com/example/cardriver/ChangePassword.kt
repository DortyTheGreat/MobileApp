package com.example.cardriver

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Database
import androidx.room.Room
import androidx.room.Room.databaseBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText


class ChangePassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_changepass)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<ImageView>(R.id.backArrow).setOnClickListener {
            onBackPressed() // Вернуться назад
        }

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
        val pass_orig = findViewById<TextInputEditText>(R.id.textInputEditText_email)
        val pass = findViewById<TextInputEditText>(R.id.textInputEditText_pass)
        val pass_repeat = findViewById<TextInputEditText>(R.id.textInputEditText_pass_repeat)

        val button_login = findViewById<Button>(R.id.button_login)

        val pass_hide = findViewById<ImageView>(R.id.pass_hide)
        val pass_hide2 = findViewById<ImageView>(R.id.pass_hide2)
        val pass_hide3 = findViewById<ImageView>(R.id.pass_hide3)


        pass.inputType = 131201
        pass_repeat.inputType = 131201
        pass_orig.inputType = 131201
        pass_hide.setOnClickListener{
            if (pass.inputType == 131201){
                pass.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                pass_repeat.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                pass_orig.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }else{
                pass.inputType = 131201
                pass_repeat.inputType = 131201
                pass_orig.inputType = 131201
            }
        }

        pass_hide2.setOnClickListener{
            if (pass.inputType == 131201){
                pass.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                pass_repeat.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                pass_orig.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }else{
                pass.inputType = 131201
                pass_repeat.inputType = 131201
                pass_orig.inputType = 131201
            }
        }

        pass_hide3.setOnClickListener{
            if (pass.inputType == 131201){
                pass.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                pass_repeat.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
                pass_orig.inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            }else{
                pass.inputType = 131201
                pass_repeat.inputType = 131201
                pass_orig.inputType = 131201
            }
        }

        button_login.setOnClickListener {

            val pas_orig = pass_orig.getText().toString()
            val pas = pass.getText().toString()
            val pas2 = pass_repeat.getText().toString()



            if (pas != pas2){
                Snackbar.make(findViewById(R.id.main), "Пароли должны совпадать", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!isValidPass(pas)){
                Snackbar.make(findViewById(R.id.main), "Новый пароль некорректный", Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            try {
                val db = Room.databaseBuilder(
                    applicationContext,
                    AppDatabase::class.java, "database-name"
                ).allowMainThreadQueries().build()

                val userDao = db.userDao()

                val a = userDao.findByLogin(Global.current_session_email!!)

                if (a.pass != pas_orig){
                    Snackbar.make(findViewById(R.id.main),
                        "Неверный старый пароль",
                        Snackbar.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                userDao.updatePass(Global.current_session_email!!, pas)


            }catch (e: Exception) {
                Snackbar.make(findViewById(R.id.main), e.toString(), Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }




            startActivity(Intent(this, Profile::class.java))
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
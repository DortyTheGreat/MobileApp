package com.example.cardriver

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2

class Onboarding : AppCompatActivity() {
    private lateinit var viewPager2: ViewPager2
    private lateinit var viewpager: viewpager
    private lateinit var imageView: ImageView
    private lateinit var nextButton: Button
    private lateinit var skipButton: Button
    private lateinit var textView: TextView
    private lateinit var textView6: TextView
    private lateinit var textView7: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        // Найти ViewPager2, ImageView, TextView и кнопки по ID
        viewPager2 = findViewById(R.id.viewPager2)
        imageView = findViewById(R.id.imageViewScroll)
        textView6 = findViewById(R.id.textView6)
        textView7 = findViewById(R.id.textView7)

        nextButton = findViewById(R.id.button_dalee)
        skipButton = findViewById(R.id.button_skip)

        // Список изображений для ViewPager2
        val imagesForPager = listOf(
            R.drawable.onb1,
            R.drawable.onb2,
            R.drawable.onb3,
        )

        // Список изображений для ImageView (отдельные изображения)
        val imagesForImageView = listOf(
            R.drawable.scroll1,
            R.drawable.scroll2,
            R.drawable.scroll3,
        )

        // Список текстов для TextView6
        val textsForTextView6 = listOf(
            "Аренда автомобилей",
            "Безопасно и удобно",
            "Лучшие предложения"
        )

        // Список текстов для TextView7
        val textsForTextView7 = listOf(
            "Открой для себя удобный и доступный способ передвижения",
            "Арендуй автомобиль и наслаждайся его удобством",
            "Выбирай понравившееся среди сотен доступных автомобилей"
        )

        // Настроить адаптер для ViewPager2
        viewpager = viewpager(imagesForPager)
        viewPager2.adapter = viewpager

        // Callback для смены страницы
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                // Меняем изображение и текст в зависимости от позиции ViewPager2
                imageView.setImageResource(imagesForImageView[position])
                textView6.text = textsForTextView6[position] // Меняем текст в TextView6
                textView7.text = textsForTextView7[position] // Меняем текст в TextView7

                // Если на последней странице, меняем текст кнопки на "Завершить"
                if (position == imagesForPager.size - 1) {
                    nextButton.text = "Поехали"
                } else {
                    nextButton.text = "Далее"
                }
            }
        })

        // Обработка кнопки "Пропустить"
        skipButton.setOnClickListener {
            startActivity(Intent(this, Join::class.java))
            finish()
        }

        nextButton.setOnClickListener {
            if (viewPager2.currentItem < imagesForPager.size - 1) {
                viewPager2.currentItem = viewPager2.currentItem + 1
            } else {
                startActivity(Intent(this, Join::class.java))
                finish()
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}
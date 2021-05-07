package com.galeopsis.myfirstmvvmapplication.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.galeopsis.myfirstmvvmapplication.databinding.ActivitySetTheme2Binding

class SetThemeActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySetTheme2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetTheme2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        Intent(this@SetThemeActivity, MainActivity::class.java)
        initListeners()
    }

    private fun initListeners() {
        with(binding) {

            one.setOnClickListener { oneClick() }
            two.setOnClickListener { twoClick() }
        }
    }

    private fun oneClick() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("theme", "one")
        startActivity(intent)
    }

    private fun twoClick() {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("theme", "two")
        startActivity(intent)
    }
}
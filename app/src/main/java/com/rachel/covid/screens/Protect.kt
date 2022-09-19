package com.rachel.covid.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.rachel.covid.MainActivity
import com.rachel.covid.R

class Protect : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_protect)

        btnBack()
        supportActionBar?.hide()
    }

    private fun btnBack() {
        val btnBack = findViewById<Button>(R.id.btnArrowBack)
        btnBack.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}
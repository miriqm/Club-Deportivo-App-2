package com.example.clubdeportivoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivoapp.R.id.btnVolver


class PantallaCarnet : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_pantlla_carnet)

        val btnVolver = findViewById<Button>(btnVolver)

        btnVolver.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)

        }
    }
}


package com.example.clubdeportivoapp


import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivoapp.R.id.btnVolver



class PantallaMorosos : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_morosos)

         lateinit var lvMorosos: ListView
         lateinit var databaseHelper: DatabaseHelper

         databaseHelper = DatabaseHelper(this)  // Inicializa DatabaseHelper
         lvMorosos = findViewById(R.id.lvSociosMorosos)

         val listaMorosos = databaseHelper.getSociosMorosos(this)
         val morososAdapter = ArrayAdapter(this,android.R.layout.simple_list_item_1, listaMorosos)

         lvMorosos.adapter = morososAdapter


            val btnVolver = findViewById<Button>(btnVolver)

        btnVolver.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }

    }
}
package com.example.clubdeportivoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivoapp.R.id.btnInscribir
import com.example.clubdeportivoapp.R.id.btnSalir
import com.example.clubdeportivoapp.R.layout.activity_pantalla_principal

class PantallaPrincipal : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_pantalla_principal)
        val btnInscribir = findViewById<Button>(btnInscribir)

        val btnPagarCuota = findViewById<Button>(R.id.btnPagarCuota) //se debe importar una clase para capturar el boton


        btnInscribir.setOnClickListener {
            val intent = Intent(this, PantallaInscripciones::class.java)
            startActivity(intent)
        }

        val btnSalir = findViewById<Button>(btnSalir)
        btnSalir.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnPagarCuota.setOnClickListener {
            val intent = Intent(this, PantallaPagoActivity::class.java)
            startActivity(intent)
        }


    }
}

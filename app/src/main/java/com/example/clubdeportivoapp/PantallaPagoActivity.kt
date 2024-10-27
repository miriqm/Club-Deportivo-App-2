package com.example.clubdeportivoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivoapp.R.id.btnVolver

class PantallaPagoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_pago)

        val btnVolver = findViewById<Button>(btnVolver)



        btnVolver.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }



        val btnComprobante = findViewById<Button>(R.id.btnComprobante)

        btnComprobante.setOnClickListener {
            val intent = Intent(this, PantallaComprobante::class.java)
            startActivity(intent)
        }

    }
}
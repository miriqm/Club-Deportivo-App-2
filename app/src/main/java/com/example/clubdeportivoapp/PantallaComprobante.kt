package com.example.clubdeportivoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivoapp.R.id.btnVolver

class PantallaComprobante : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pantalla_comprobante)

        dbHelper = DatabaseHelper(this)
        val idSocio = intent.getStringExtra("idSocio")
        val fechaPago = intent.getStringExtra("fechaPago")
        val monto = intent.getStringExtra("monto")
        val txtNombre = findViewById<EditText>(R.id.txtNombre)

        if(idSocio != null) {
            val nombreCompleto = dbHelper.obtenerNombreCompleto(idSocio)
            txtNombre.setText(nombreCompleto)
        }
        findViewById<EditText>(R.id.txtIdSocio).setText(idSocio)
        findViewById<EditText>(R.id.txtFechaPago).setText(fechaPago)
        findViewById<EditText>(R.id.txtMonto).setText(monto)



        val btnVolver = findViewById<Button>(btnVolver)

        btnVolver.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }


    }


}
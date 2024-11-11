package com.example.clubdeportivoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivoapp.R.id.btnVolver


class PantallaCarnet : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantlla_carnet)

         dbHelper = DatabaseHelper(this)

        val txtDNI = findViewById<EditText>(R.id.txtDNI)
        val txtNombre = findViewById<TextView>(R.id.txtNombre)
        val txtNumeroSocio = findViewById<TextView>(R.id.txtNumeroSocio)
        val txtFechaRegistro = findViewById<TextView>(R.id.txtFechaRegistro)
        val btnBuscar = findViewById<Button>(R.id.btnBuscar)
        val btnVolver = findViewById<Button>(btnVolver)

        btnBuscar.setOnClickListener {
            val dni = txtDNI.text.toString()

            if (dni.isNotEmpty()) {
                val dbHelper = DatabaseHelper(this)
                val cursor = dbHelper.obtenerDatosSocio(dni)

                if (cursor != null && cursor.moveToFirst()) {
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_NOMBRE))
                    val apellido = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_APELLIDO))
                    val idSocio = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_ID_SOCIO))
                    val fechaPago = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COLUMN_FECHA_PAGO))


                    txtNombre.setText("$nombre $apellido")
                    txtNumeroSocio.setText(idSocio)
                    txtFechaRegistro.setText(fechaPago)
                } else {

                    Toast.makeText(this, "No se encontró ningún socio con ese DNI", Toast.LENGTH_SHORT).show()
                }

                cursor?.close()
                dbHelper.close()
            } else {
                Toast.makeText(this, "Por favor, ingrese un DNI", Toast.LENGTH_SHORT).show()
            }
        }

        btnVolver.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)

        }
    }

}






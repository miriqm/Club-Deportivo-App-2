package com.example.clubdeportivoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivoapp.R.layout.activity_pantalla_inscripciones

class PantallaInscripciones : AppCompatActivity() {
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activity_pantalla_inscripciones)

        dbHelper = DatabaseHelper(this)
        val btnIngresar = findViewById<Button>(R.id.btnIngresar)
        val txtNombre = findViewById<EditText>(R.id.txtNombre)
        val txtApellido = findViewById<EditText>(R.id.txtApellido)
        val txtDNI = findViewById<EditText>(R.id.txtDNI)
        val txtDireccion = findViewById<EditText>(R.id.txtDireccion)
        val txtEmail = findViewById<EditText>(R.id.txtEmail)
        val rgOptions = findViewById<RadioGroup>(R.id.rgOptions)
        val checkAptoFisico = findViewById<CheckBox>(R.id.checkAptoFisico)
        val btnVolver = findViewById<Button>(R.id.btnVolver)
        val btnLimpiar = findViewById<Button>(R.id.btnLimpiar)

        val rbSocio = findViewById<RadioButton>(R.id.rbSocio)
        val rbNoSocio = findViewById<RadioButton>(R.id.rbNoSocio)


        btnIngresar.setOnClickListener {
            val nombre = txtNombre.text.toString()
            val apellido = txtApellido.text.toString()
            val dni = txtDNI.text.toString()
            val direccion = txtDireccion.text.toString()
            val email = txtEmail.text.toString()
            val esSocio = rbSocio.isChecked
            val aptoFisico = checkAptoFisico.isChecked

            if (nombre.isEmpty() || apellido.isEmpty() || dni.isEmpty() || direccion.isEmpty() || email.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if(rbSocio.isChecked) {
                Toast.makeText(this, "Opción: Socio", Toast.LENGTH_LONG).show()
            } else if(rbNoSocio.isChecked){
                Toast.makeText(this, "Opción: NoSocio", Toast.LENGTH_LONG).show()
            } else{
                Toast.makeText(this, "Seleccioná por favor una opción", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (!aptoFisico) {
                Toast.makeText(this, "Debes tener el Apto Físico para registrarte", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            
            val result = dbHelper.insertarSocio(nombre, apellido, dni, direccion, email,
                esSocio, aptoFisico)

            if (result != (-1L)) {
                val idSocio = result.toString()
                Toast.makeText(this, "Socio registrado exitosamente", Toast.LENGTH_LONG).show()
                val intent = Intent(this, PantallaPagoActivity::class.java).apply {
                    putExtra("idSocio", idSocio)
                }
               startActivity(intent)
            } else {
                Toast.makeText(this, "Error al registrar el socio", Toast.LENGTH_LONG).show()
            }

        }
            btnVolver.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }
            btnLimpiar.setOnClickListener {
            txtNombre.text.clear()
            txtApellido.text.clear()
            txtDNI.text.clear()
            txtDireccion.text.clear()
            txtEmail.text.clear()
            rgOptions.clearCheck()
            checkAptoFisico.isChecked = false
        }

    }
}
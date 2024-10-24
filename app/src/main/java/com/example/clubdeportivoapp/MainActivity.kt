package com.example.clubdeportivoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.clubdeportivoapp.R.id.btnIngresar
import com.example.clubdeportivoapp.R.id.txtPass
import com.example.clubdeportivoapp.R.id.txtUsuario

class MainActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

         databaseHelper = DatabaseHelper(this)
         databaseHelper.insertarUsuarioAdmin()
        val txtUsuario = findViewById<EditText>(txtUsuario)
        val txtPass = findViewById<EditText>(txtPass)
        val btnIngresar = findViewById<Button>(btnIngresar)

        btnIngresar.setOnClickListener {
            val usuario = txtUsuario.text.toString()
            val contrasena = txtPass.text.toString()

            if (validarUsuario(usuario, contrasena)) {
                Toast.makeText(this, "Ingreso exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, PantallaPrincipal::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun validarUsuario(usuario: String, contrasena: String): Boolean {
        val db = databaseHelper.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM " + DatabaseHelper.TABLE_USUARIOS + " WHERE " + DatabaseHelper.COLUMN_NOMBRE_USUARIO + " = ? AND " + DatabaseHelper.COLUMN_CONTRASENA + " = ?",
            arrayOf(usuario, contrasena)
        )
        val isValid = cursor.count > 0
        cursor.close()
        return isValid
    }

    }

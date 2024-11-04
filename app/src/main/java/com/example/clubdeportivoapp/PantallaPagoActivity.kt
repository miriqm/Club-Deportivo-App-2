package com.example.clubdeportivoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivoapp.R.id.btnRealizarCobro
import com.example.clubdeportivoapp.R.id.btnVolver

class PantallaPagoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_pago)

        dbHelper = DatabaseHelper(this)

        val rbEfectivo = findViewById<RadioButton>(R.id.rbEfectivo)
        val rbTarjeta = findViewById<RadioButton>(R.id.rbTarjeta)
        val txtMonto = findViewById<EditText>(R.id.txtMonto)
        val btnPagar =findViewById<Button>(R.id.btnPagar)
        val btnComprobante = findViewById<Button>(R.id.btnComprobante)

        val monto = txtMonto.text.toString()
        val btnVolver = findViewById<Button>(btnVolver)

        btnPagar.setOnClickListener{
            var metodoPago = ""
            if(rbEfectivo.isChecked){
                Toast.makeText(this,"Opcion: Efectivo", Toast.LENGTH_SHORT).show()
            } else if(rbTarjeta.isChecked){
                Toast.makeText(this,"Opcion: Tarjeta", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Selecciona por favor una opcion", Toast.LENGTH_SHORT).show()
            }
            val result = dbHelper.agregarMetodoPago(metodoPago)
            if (result != -1L) {
                Toast.makeText(this, "Método de pago guardado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al guardar el método de pago", Toast.LENGTH_SHORT).show()
            }

            if(monto.isEmpty()){
                Toast.makeText(this, "Por favor,ingrese el importe a pagar", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }


        btnComprobante.setOnClickListener {
            val intent = Intent(this, PantallaComprobante::class.java)
            startActivity(intent)
        }

        btnVolver.setOnClickListener {
            val intent = Intent(this, PantallaPrincipal::class.java)
            startActivity(intent)
        }

    }
}
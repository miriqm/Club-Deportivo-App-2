package com.example.clubdeportivoapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.clubdeportivoapp.R.id.btnPagar
import com.example.clubdeportivoapp.R.id.btnVolver
import com.example.clubdeportivoapp.R.id.cbSocio
import com.example.clubdeportivoapp.R.id.rbNoSocio
import com.example.clubdeportivoapp.R.id.rbSocio
import com.example.clubdeportivoapp.R.id.txtIdCliente

class PantallaPagoActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantalla_pago)

        dbHelper = DatabaseHelper(this)
        val txtIdCliente = findViewById<EditText>(R.id.txtIdCliente)
        val rbEfectivo = findViewById<RadioButton>(R.id.rbEfectivo)
        val rbTarjeta = findViewById<RadioButton>(R.id.rbTarjeta)
        val txtMonto = findViewById<EditText>(R.id.txtMonto)
        val btnPagar =findViewById<Button>(R.id.btnPagar)
        val btnComprobante = findViewById<Button>(R.id.btnComprobante)
        val btnVolver = findViewById<Button>(btnVolver)
        val cbSocio = findViewById<RadioButton>(R.id.cbSocio)
        val cbNoSocio = findViewById<RadioButton>(R.id.cbNoSocio)
        val txtFecha = findViewById<EditText>(R.id.txtFecha)

        btnPagar.setOnClickListener{
            val idCliente = txtIdCliente.text.toString()
            val fechaPago = txtFecha.text.toString()
            val monto = txtMonto.text.toString()
            if (idCliente.isEmpty()) {
                Toast.makeText(this, "Por favor, ingrese el ID del cliente", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            if (!dbHelper.verificarCliente(idCliente)) {
                Toast.makeText(this, "Cliente no encontrado", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            var tipoCliente = ""
            if(cbSocio.isChecked){
                tipoCliente = "Socio"
                Toast.makeText(this,"Opcion: Socio", Toast.LENGTH_SHORT).show()
            } else if(cbNoSocio.isChecked){
                tipoCliente = "NoSocio"
                Toast.makeText(this,"Opcion: NoSocio", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Selecciona por favor una opcion", Toast.LENGTH_SHORT).show()
            }

            var metodoPago = " "
            if(rbEfectivo.isChecked){
                metodoPago = "Efectivo"
                Toast.makeText(this,"Opcion: Efectivo", Toast.LENGTH_SHORT).show()
            } else if(rbTarjeta.isChecked){
                metodoPago = "Tarjeta"
                Toast.makeText(this,"Opcion: Tarjeta", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this,"Selecciona por favor una opcion", Toast.LENGTH_SHORT).show()
            }

            val result = dbHelper.guardarPago(idCliente, tipoCliente, metodoPago, fechaPago, monto)

            if (result != -1L) {
                Toast.makeText(this, "Método de pago guardado", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Error al guardar el método de pago", Toast.LENGTH_SHORT).show()
            }

            if (fechaPago.isEmpty()) {
                Toast.makeText(this, "Por favor, ingresa la fecha de pago.", Toast.LENGTH_SHORT).show()
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

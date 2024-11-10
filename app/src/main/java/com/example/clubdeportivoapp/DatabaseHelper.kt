@file:Suppress("ConvertToStringTemplate")

package com.example.clubdeportivoapp


import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    private val context: Context = context
    companion object {
        private const val DATABASE_NAME = "ClubDeportivo.db"
        private const val DATABASE_VERSION = 3

        //tabla Usuarios
        const val TABLE_USUARIOS = "Usuarios"
        const val COLUMN_ID_USUARIO = "id_usuario"
        const val COLUMN_NOMBRE_USUARIO = "nombre_usuario"
        const val COLUMN_CONTRASENA = "contrasena"

        //tabla socio/noSocio
        const val TABLE_SOCIOS = "Socios"
        const val COLUMN_ID_SOCIO = "id_socio"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APELLIDO = "apellido"
        const val COLUMN_DNI = "dni"
        const val COLUMN_DIRECCION = "direccion"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_ES_SOCIO = "es_socio"
        const val COLUMN_APTO_FISICO = "apto_fisico"

        //tabla pagos
        const val TABLE_PAGOS = "Pagos"
        const val COLUMN_ID_PAGO = "id_pago"
        const val COLUMN_ID_SOCIO_PAGO = "id_socio"
        const val COLUMN_TIPO_PAGO = "tipo_Cliente"
        const val COLUMN_METODO_PAGO = "metodo_pago"
        const val COLUMN_FECHA_PAGO = "fecha_pago"
        const val COLUMN_FECHA_VENCIMIENTO_PAGO = "fecha_vencimiento"
        const val COLUMN_MONTO_PAGO = "monto_pago"

        //Tabla de Carnet
        const val TABLE_CARNET = "Carnet"
        const val COLUMN_ID_CARNET = "id_carnet"
        const val COLUMN_ID_SOCIO_CARNET = "id_socio"
        const val COLUMN_FECHA_EMISION_CARNET = "fecha_emision"

        //Tabla de Morosos
        const val TABLE_MOROSOS = "Morosos"
        const val COLUMN_ID_MOROSO = "id_moroso"
        const val COLUMN_ID_SOCIO_MOROSO = "id_socio"
        const val COLUMN_FECHA_VENCIMIENTO = "fecha_vencimiento"
    }

    override fun onCreate(db: SQLiteDatabase) {

        val createUsuariosTable = ("CREATE TABLE " + TABLE_USUARIOS + " ("
                + COLUMN_ID_USUARIO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOMBRE_USUARIO + " TEXT, "
                + COLUMN_CONTRASENA + " TEXT)")
        db.execSQL(createUsuariosTable)

        val createSociosTable = ("CREATE TABLE " + TABLE_SOCIOS + " ("
                + COLUMN_ID_SOCIO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NOMBRE + " TEXT, "
                + COLUMN_APELLIDO + " TEXT, "
                + COLUMN_DNI + " TEXT, "
                + COLUMN_DIRECCION + " TEXT, "
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_ES_SOCIO + " INTEGER, "
                + COLUMN_APTO_FISICO + " INTEGER)")
        db.execSQL(createSociosTable)

        val createPagosTable = ("CREATE TABLE " + TABLE_PAGOS + " ("
                + COLUMN_ID_PAGO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ID_SOCIO_PAGO + " INTEGER, "
                + COLUMN_TIPO_PAGO + " TEXT, "
                + COLUMN_METODO_PAGO + " TEXT, "
                + COLUMN_FECHA_PAGO + " TEXT, "
                + COLUMN_FECHA_VENCIMIENTO_PAGO + " TEXT, "
                + COLUMN_MONTO_PAGO + " REAL)")
        db.execSQL(createPagosTable)

        val createCarnetTable = ("CREATE TABLE " + TABLE_CARNET + " ("
                + COLUMN_ID_CARNET + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ID_SOCIO_CARNET + " INTEGER, "
                + COLUMN_FECHA_EMISION_CARNET + " TEXT)")
        db.execSQL(createCarnetTable)

        val createMorososTable = ("CREATE TABLE " + TABLE_MOROSOS + " ("
                + COLUMN_ID_MOROSO + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_ID_SOCIO_MOROSO + " INTEGER, "
                + COLUMN_FECHA_VENCIMIENTO + " TEXT)")
        db.execSQL(createMorososTable)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USUARIOS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SOCIOS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PAGOS)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CARNET)
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOROSOS)
        onCreate(db)
    }


    fun ingresarUsuario() {
        val db = this.writableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USUARIOS WHERE $COLUMN_NOMBRE_USUARIO = 'admin'",
            null
        )
        val usuarioExiste = cursor.moveToFirst()  // Será true si hay al menos un resultado

        cursor.close()

        if (!usuarioExiste) {
            val values = ContentValues().apply {
                put(COLUMN_NOMBRE_USUARIO, "admin")
                put(COLUMN_CONTRASENA, "1234")
            }

            db.insert(TABLE_USUARIOS, null, values)
            Log.d("DBInsert", "Usuario 'admin' insertado en la tabla Usuarios")
        } else {
            Log.d("DBInsert", "Usuario 'admin' ya existe.")
        }
    }


//    fun ingresarUsuario() {
//        val db = this.writableDatabase
//        val values = ContentValues().apply {
//            put(COLUMN_NOMBRE_USUARIO, "admin")
//            put(COLUMN_CONTRASENA, "1234")
//        }
//        db.insert(TABLE_USUARIOS, null, values)
//    }


    fun insertarSocio(nombre: String, apellido: String, dni: String, direccion: String, email: String, esSocio: Boolean, aptoFisico: Boolean): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_APELLIDO, apellido)
            put(COLUMN_DNI, dni)
            put(COLUMN_DIRECCION, direccion)
            put(COLUMN_EMAIL, email)
            put(COLUMN_ES_SOCIO,if (esSocio) 1 else 0)
            put(COLUMN_APTO_FISICO, if (aptoFisico) 1 else 0)
        }
        val success = db.insert(TABLE_SOCIOS, null, values)
        return success
    }

    fun verificarCliente(idCliente: String): Boolean {

        val db = this.readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM " + TABLE_SOCIOS + " WHERE " + COLUMN_ID_SOCIO + " = '" + idCliente + "'",
            null
        )
        val exists = cursor.moveToFirst()

        cursor.close()
        return exists
    }

    fun guardarPago(idCliente: String, tipoCliente: String, metodoPago: String, fechaPago: String, fechaVencimiento: String, monto: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_ID_SOCIO, idCliente)
            put(COLUMN_TIPO_PAGO, tipoCliente)
            put(COLUMN_METODO_PAGO, metodoPago)
            put(COLUMN_FECHA_PAGO, fechaPago)
            put(COLUMN_FECHA_VENCIMIENTO_PAGO, fechaVencimiento)
            put(COLUMN_MONTO_PAGO, monto)
        }
        val success = db.insert(TABLE_PAGOS, null, values)
        db.close()
        return success
    }

    fun obtenerNombreCompleto(idSocio: String): String {
        var nombreCompleto = ""
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_NOMBRE, $COLUMN_APELLIDO FROM $TABLE_SOCIOS WHERE $COLUMN_ID_SOCIO = ?", arrayOf(idSocio))

        if (cursor.moveToFirst()) {

            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
            val apellido = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APELLIDO))

            nombreCompleto = "$nombre $apellido"
        }

        cursor.close()
        db.close()
        return nombreCompleto
    }

    // Funcion para pantalla morosos

    @RequiresApi(Build.VERSION_CODES.O)
    fun getSociosMorosos(context: Context): List<String> {
        val listaMorosos = mutableListOf<String>()
        val db = this.readableDatabase

        // Fecha de hoy en formato 'yyyy-MM-dd'
        val fechaHoy = LocalDate.now()

        // Consulta SQL con JOIN entre PAGOS y SOCIOS
        val query = """
        SELECT PAGOS.fecha_vencimiento, PAGOS.monto_pago, SOCIOS.nombre, SOCIOS.apellido, SOCIOS.id_socio
        FROM $TABLE_PAGOS AS PAGOS
        JOIN $TABLE_SOCIOS AS SOCIOS
        ON PAGOS.id_socio = SOCIOS.id_socio
        WHERE PAGOS.fecha_vencimiento = ?
        """

        try {

            val cursor = db.rawQuery(query, arrayOf("2023-11-10"))

            // Log para ver si la consulta devuelve datos
            Log.d("DBQuery", "Consulta ejecutada para fecha: $fechaHoy")

            if (cursor.moveToFirst()) {
                var count = 0
                do {
                    // Obtiene los valores de cada columna y forma una cadena con la información
                    val fechaVencimiento = cursor.getString(cursor.getColumnIndexOrThrow("fecha_vencimiento"))
                    val importe = cursor.getString(cursor.getColumnIndexOrThrow("monto_pago"))
                    val nombre = cursor.getString(cursor.getColumnIndexOrThrow("nombre"))
                    val apellido = cursor.getString(cursor.getColumnIndexOrThrow("apellido"))
                    val idSocio = cursor.getString(cursor.getColumnIndexOrThrow("id_socio"))

                    // Agrega la informacion como una sola cadena en la lista
                    listaMorosos.add(" $fechaVencimiento - $nombre $apellido -$idSocio - $$importe.-  ")
                    count++
                } while (cursor.moveToNext())


                Log.d("DBQuery", "Se encontraron $count socios morosos")
            } else {

                Toast.makeText(context, "No se encontraron socios morosos para la fecha $fechaHoy", Toast.LENGTH_LONG).show()
                Log.d("DBQuery", "No se encontraron socios morosos para la fecha $fechaHoy")
            }

            cursor.close()
        } catch (e: Exception) {
            Log.e("DBQuery", "Error ejecutando la consulta: ${e.message}")
        }

        return listaMorosos
    }

}
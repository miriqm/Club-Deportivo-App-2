@file:Suppress("ConvertToStringTemplate")

package com.example.clubdeportivoapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ClubDeportivo.db"
        private const val DATABASE_VERSION = 1
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
        const val COLUMN_TIPO_PAGO = "tipo_pago" //Mensual o Diario
        const val COLUMN_FECHA_PAGO = "fecha_pago"
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
                + COLUMN_TIPO_PAGO + " INTEGER, "
                + COLUMN_FECHA_PAGO + " TEXT, "
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


    fun insertarUsuarioAdmin() {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(DatabaseHelper.COLUMN_NOMBRE_USUARIO, "admin")
            put(DatabaseHelper.COLUMN_CONTRASENA, "1234")
        }
        db.insert(DatabaseHelper.TABLE_USUARIOS, null, values)
    }

    fun insertarSocio(nombre: String, apellido: String, dni: String, direccion: String, email: String, esSocio: Boolean, aptoFisico: Boolean): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_APELLIDO, apellido)
            put(COLUMN_DNI, dni)
            put(COLUMN_DIRECCION, direccion)
            put(COLUMN_EMAIL, email)
            put(COLUMN_ES_SOCIO, if (esSocio) 1 else 0)
            put(COLUMN_APTO_FISICO, if (aptoFisico) 1 else 0)
    }
        val success = db.insert(DatabaseHelper.TABLE_SOCIOS, null, values)
        return success
    }


}
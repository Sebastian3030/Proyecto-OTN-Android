package com.example.otn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CitaConfirmadaActivity : AppCompatActivity() {

    private lateinit var btnVolver: ImageView
    private lateinit var btnInicio: Button

    private lateinit var txtNombre: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtTelefono: TextView
    private lateinit var txtServicio: TextView
    private lateinit var txtFecha: TextView
    private lateinit var txtHora: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cita_confirmada)

        // REFERENCIAS
        btnVolver = findViewById(R.id.btnVolver)
        btnInicio = findViewById(R.id.btnInicio)

        txtNombre = findViewById(R.id.txtNombre)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtTelefono = findViewById(R.id.txtTelefono)
        txtServicio = findViewById(R.id.txtServicio)
        txtFecha = findViewById(R.id.txtFecha)
        txtHora = findViewById(R.id.txtHora)

        // DATOS RECIBIDOS
        val nombre =
            intent.getStringExtra("nombre") ?: ""

        val correo =
            intent.getStringExtra("correo") ?: ""

        val telefono =
            intent.getStringExtra("telefono") ?: ""

        val servicio =
            intent.getStringExtra("servicio") ?: ""

        val fecha =
            intent.getStringExtra("fecha") ?: ""

        val hora =
            intent.getStringExtra("hora") ?: ""

        // MOSTRAR DATOS
        txtNombre.text =
            "Nombre: $nombre"

        txtCorreo.text =
            "Correo: $correo"

        txtTelefono.text =
            "Teléfono: $telefono"

        txtServicio.text =
            "Servicio: $servicio"

        txtFecha.text =
            "Fecha: $fecha"

        txtHora.text =
            "Hora: $hora"

        // BOTON VOLVER
        btnVolver.setOnClickListener {

            finish()

        }

        // BOTON INICIO
        btnInicio.setOnClickListener {

            val intent = Intent(
                this,
                HomeActivity::class.java
            )

            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)

        }

    }

}
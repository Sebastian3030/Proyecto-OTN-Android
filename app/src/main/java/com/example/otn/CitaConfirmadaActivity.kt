package com.example.otn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class CitaConfirmadaActivity : AppCompatActivity() {

    private lateinit var btnVolver: ImageView
    private lateinit var btnInicio: Button
    private lateinit var btnVerHistorial: Button

    private lateinit var txtNombre: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtTelefono: TextView
    private lateinit var txtServicio: TextView
    private lateinit var txtFecha: TextView
    private lateinit var txtHora: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cita_confirmada)

        // REFERENCIAS VISTAS
        btnVolver = findViewById(R.id.btnVolver)
        btnInicio = findViewById(R.id.btnInicio)
        btnVerHistorial = findViewById(R.id.btnVerHistorial)

        txtNombre = findViewById(R.id.txtNombre)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtTelefono = findViewById(R.id.txtTelefono)
        txtServicio = findViewById(R.id.txtServicio)
        txtFecha = findViewById(R.id.txtFecha)
        txtHora = findViewById(R.id.txtHora)

        // EVITAR QUE EL PUNCH HOLE DE LA CÁMARA TAPARA LA TOPBAR
        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(
                view.paddingLeft,
                statusBarInsets.top,
                view.paddingRight,
                view.paddingBottom
            )
            insets
        }

        // ======================================================================
        // RECUPERACIÓN DE DATOS (Usando llaves seguras del companion object)
        // ======================================================================
        val nombre = intent.getStringExtra(EXTRA_NOMBRE) ?: "No disponible"
        val correo = intent.getStringExtra(EXTRA_CORREO) ?: "No disponible"
        val telefono = intent.getStringExtra(EXTRA_TELEFONO) ?: "No disponible"
        val servicio = intent.getStringExtra(EXTRA_SERVICIO) ?: "No disponible"
        val fecha = intent.getStringExtra(EXTRA_FECHA) ?: "No disponible"
        val hora = intent.getStringExtra(EXTRA_HORA) ?: "No disponible"

        // MOSTRAR DATOS EN LA INTERFAZ
        txtNombre.text = "Nombre: $nombre"
        txtCorreo.text = "Correo: $correo"
        txtTelefono.text = "Teléfono: $telefono"
        txtServicio.text = "Servicio: $servicio"
        txtFecha.text = "Fecha: $fecha"
        txtHora.text = "Hora: $hora"

        // BOTÓN VOLVER
        btnVolver.setOnClickListener {
            finish()
        }

        // BOTÓN INICIO (Limpia el stack para que no puedan regresar aquí con el botón físico)
        btnInicio.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
        }

        // BOTÓN VER HISTORIAL
        btnVerHistorial.setOnClickListener {
            val intent = Intent(this, HistorialCitasActivity::class.java).apply {
                putExtra(EXTRA_NOMBRE, nombre)
                putExtra(EXTRA_CORREO, correo)
                putExtra(EXTRA_TELEFONO, telefono)
                putExtra(EXTRA_SERVICIO, servicio)
                putExtra(EXTRA_FECHA, fecha)
                putExtra(EXTRA_HORA, hora)
            }
            startActivity(intent)
        }
    }

    // ======================================================================
    // LLAVES COMPANION OBJECT: Estándar profesional para no perder datos
    // ======================================================================
    companion object {
        const val EXTRA_NOMBRE = "extra_nombre"
        const val EXTRA_CORREO = "extra_correo"
        const val EXTRA_TELEFONO = "extra_telefono"
        const val EXTRA_SERVICIO = "extra_servicio"
        const val EXTRA_FECHA = "extra_fecha"
        const val EXTRA_HORA = "extra_hora"
    }
}
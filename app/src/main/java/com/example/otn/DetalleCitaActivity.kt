package com.example.otn

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetalleCitaActivity : AppCompatActivity() {

    private lateinit var btnVolver: ImageView

    private lateinit var btnConfirmar: Button
    private lateinit var btnFinalizar: Button

    private lateinit var txtNombre: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var txtTelefono: TextView
    private lateinit var txtServicio: TextView
    private lateinit var txtFecha: TextView
    private lateinit var txtHora: TextView
    private lateinit var txtEstado: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_cita)

        // REFERENCIAS

        btnVolver =
            findViewById(R.id.btnVolver)

        btnConfirmar =
            findViewById(R.id.btnConfirmar)

        btnFinalizar =
            findViewById(R.id.btnFinalizar)

        txtNombre =
            findViewById(R.id.txtNombre)

        txtCorreo =
            findViewById(R.id.txtCorreo)

        txtTelefono =
            findViewById(R.id.txtTelefono)

        txtServicio =
            findViewById(R.id.txtServicio)

        txtFecha =
            findViewById(R.id.txtFecha)

        txtHora =
            findViewById(R.id.txtHora)

        txtEstado =
            findViewById(R.id.txtEstado)

        // EVITAR QUE EL PUNCH HOLE DE LA CÁMARA TAPARA LA TOPBAR
        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(
                view.paddingLeft,
                statusBarInsets.top, // Se añade la medida exacta del agujero/barra superior
                view.paddingRight,
                view.paddingBottom
            )
            insets
        }

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

        var estado =
            intent.getStringExtra("estado") ?: "Pendiente"

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

        txtEstado.text =
            "Estado: $estado"

        // VOLVER

        btnVolver.setOnClickListener {

            finish()

        }

        // CONFIRMAR

        btnConfirmar.setOnClickListener {

            estado = "Confirmada"

            txtEstado.text =
                "Estado: $estado"

        }

        // FINALIZAR

        btnFinalizar.setOnClickListener {

            estado = "Finalizada"

            txtEstado.text =
                "Estado: $estado"

        }

    }

}
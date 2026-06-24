package com.example.otn

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
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

    // 🟢 ID necesario para identificar el documento en el Backend
    private var citaId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_cita)

        btnVolver = findViewById(R.id.btnVolver)
        btnConfirmar = findViewById(R.id.btnConfirmar)
        btnFinalizar = findViewById(R.id.btnFinalizar)

        txtNombre = findViewById(R.id.txtNombre)
        txtCorreo = findViewById(R.id.txtCorreo)
        txtTelefono = findViewById(R.id.txtTelefono)
        txtServicio = findViewById(R.id.txtServicio)
        txtFecha = findViewById(R.id.txtFecha)
        txtHora = findViewById(R.id.txtHora)
        txtEstado = findViewById(R.id.txtEstado)

        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // RECUPERACIÓN SEGURA DE DATOS
        citaId = intent.getStringExtra(EXTRA_DETALLE_ID)
        val nombre = intent.getStringExtra(EXTRA_DETALLE_NOMBRE) ?: ""
        val correo = intent.getStringExtra(EXTRA_DETALLE_CORREO) ?: ""
        val telefono = intent.getStringExtra(EXTRA_DETALLE_TELEFONO) ?: ""
        val servicio = intent.getStringExtra(EXTRA_DETALLE_SERVICIO) ?: ""
        val fecha = intent.getStringExtra(EXTRA_DETALLE_FECHA) ?: ""
        val hora = intent.getStringExtra(EXTRA_DETALLE_HORA) ?: ""
        var estado = intent.getStringExtra(EXTRA_DETALLE_ESTADO) ?: "Pendiente"

        txtNombre.text = "Nombre: $nombre"
        txtCorreo.text = "Correo: $correo"
        txtTelefono.text = "Teléfono: $telefono"
        txtServicio.text = "Servicio: $servicio"
        txtFecha.text = "Fecha: $fecha"
        txtHora.text = "Hora: $hora"
        txtEstado.text = "Estado: $estado"

        // 🟢 Validamos el estado inicial para pintar los botones correctos
        renderizarBotonesSegunEstado(estado)

        btnVolver.setOnClickListener { finish() }

        btnConfirmar.setOnClickListener {
            estado = "Confirmada"
            txtEstado.text = "Estado: $estado"
            renderizarBotonesSegunEstado(estado)

            // TODO: database.child("citas").child(citaId!!).child("estado").setValue(estado)
            Toast.makeText(this, "Cita confirmada con éxito", Toast.LENGTH_SHORT).show()
        }

        btnFinalizar.setOnClickListener {
            estado = "Finalizada"
            txtEstado.text = "Estado: $estado"
            renderizarBotonesSegunEstado(estado)

            // TODO: database.child("citas").child(citaId!!).child("estado").setValue(estado)
            Toast.makeText(this, "Cita marcada como finalizada", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 🟢 CORRECCIÓN DE INTERFAZ DE USUARIO:
     * Controla de manera inteligente la visibilidad de las acciones para evitar estados incongruentes.
     */
    private fun renderizarBotonesSegunEstado(estado: String) {
        when (estado.lowercase()) {
            "pendiente" -> {
                btnConfirmar.visibility = View.VISIBLE
                btnFinalizar.visibility = View.GONE // No puedes terminar algo no confirmado
            }
            "confirmada" -> {
                btnConfirmar.visibility = View.GONE // Ya está confirmada
                btnFinalizar.visibility = View.VISIBLE // Ahora sí se puede finalizar
            }
            "finalizada" -> {
                // Una cita completada no permite más acciones
                btnConfirmar.visibility = View.GONE
                btnFinalizar.visibility = View.GONE
            }
            else -> {
                btnConfirmar.visibility = View.GONE
                btnFinalizar.visibility = View.GONE
            }
        }
    }

    companion object {
        const val EXTRA_DETALLE_ID = "id_cita"
        const val EXTRA_DETALLE_NOMBRE = "nombre"
        const val EXTRA_DETALLE_CORREO = "correo"
        const val EXTRA_DETALLE_TELEFONO = "telefono"
        const val EXTRA_DETALLE_SERVICIO = "servicio"
        const val EXTRA_DETALLE_FECHA = "fecha"
        const val EXTRA_DETALLE_HORA = "hora"
        const val EXTRA_DETALLE_ESTADO = "estado"
    }
}
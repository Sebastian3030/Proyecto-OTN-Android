package com.example.otn

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ChatActivity : AppCompatActivity() {

    private lateinit var btnVolver: ImageView
    private lateinit var edtMensaje: EditText
    private lateinit var btnEnviar: Button
    private lateinit var layoutMensajes: LinearLayout
    private lateinit var scrollMensajes: ScrollView

    // VARIABLES BACKEND SIMULADAS (Fundamentales para saber quién habla con quién)
    private val idUsuarioActual = "USER_SEBAS_123"
    private val idVendedorOTratante = "VENDEDOR_OTN_456"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // REFERENCIAS VISTAS
        btnVolver = findViewById(R.id.btnVolver)
        edtMensaje = findViewById(R.id.edtMensaje)
        btnEnviar = findViewById(R.id.btnEnviar)
        layoutMensajes = findViewById(R.id.layoutMensajes)
        scrollMensajes = findViewById(R.id.scrollMensajes)

        val topBar = findViewById<LinearLayout>(R.id.topBar)
        val barraEnviar = findViewById<LinearLayout>(R.id.barraEnviar)

        // ======================================================================
        // CONTROL MAESTRO DE INSETS: FIJA LA TOPBAR Y SUBE LA BARRA DE ENVIAR
        // ======================================================================
        val vistaRaiz = scrollMensajes.parent as View

        ViewCompat.setOnApplyWindowInsetsListener(vistaRaiz) { _, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            val navigationBarsInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            // Ajuste dinámico de la cámara superior
            topBar.setPadding(
                topBar.paddingLeft,
                statusBarInsets.top,
                topBar.paddingRight,
                topBar.paddingBottom
            )

            // Espacio inferior calculando el teclado desplegado
            val espacioInferior = if (imeInsets.bottom > 0) imeInsets.bottom else navigationBarsInsets.bottom

            barraEnviar.setPadding(
                barraEnviar.paddingLeft,
                barraEnviar.paddingTop,
                barraEnviar.paddingRight,
                espacioInferior
            )

            bajarScroll()
            insets
        }

        // BOTÓN VOLVER
        btnVolver.setOnClickListener {
            finish()
        }

        // MENSAJES INICIALES DEL BOT VENDEDOR
        agregarMensajeVendedor("Hola, soy un bot por el momento :)")
        agregarMensajeVendedor("Hola 👋, gracias por comunicarte. ¿En qué puedo ayudarte?")

        // ======================================================================
        // ACCIÓN BOTÓN ENVIAR (PREPARADO PARA BASE DE DATOS)
        // ======================================================================
        btnEnviar.setOnClickListener {
            val textoMensaje = edtMensaje.text.toString().trim()

            if (textoMensaje.isNotEmpty()) {
                // 1. Mostrar visualmente de inmediato en la app
                agregarMensajeUsuario(textoMensaje)
                edtMensaje.setText("")

                // 2. ESTRUCTURA DE DATOS PREPARADA PARA EL BACKEND
                val timestampActual = System.currentTimeMillis() // Hora exacta del mensaje

                // ==================================================================
                // ZONA BACKEND: Así mapearás el mensaje para Firebase o tu API Rest
                // val mapaMensaje = hashMapOf(
                //     "idEmisor" to idUsuarioActual,
                //     "idReceptor" to idVendedorOTratante,
                //     "mensaje" to textoMensaje,
                //     "timestamp" to timestampActual
                // )
                // database.collection("chats").document("conversacion_1").collection("mensajes").add(mapaMensaje)
                // ==================================================================

                // 3. Respuesta automática simulada del Bot
                Handler(Looper.getMainLooper()).postDelayed({
                    val respuesta = obtenerRespuesta(textoMensaje)
                    agregarMensajeVendedor(respuesta)

                    // Cuando metas backend, el bot se borra y las respuestas vendrán del servidor en tiempo real
                }, 1000)
            }
        }
    }

    // RESPUESTAS AUTOMÁTICAS DEL BOT
    private fun obtenerRespuesta(mensaje: String): String {
        return when {
            mensaje.contains("hola", true) -> "Hola 👋 ¿Cómo estás?"
            mensaje.contains("bien", true) -> "Vale, me alegra"
            mensaje.contains("disponible", true) -> "Sí, el producto sigue disponible."
            mensaje.contains("precio", true) -> "El precio es: 4'500.000"
            mensaje.contains("ubicacion", true) || mensaje.contains("ubicación", true) -> "Nos encontramos en Puerto Tejada, Cauca."
            mensaje.contains("gracias", true) -> "Con mucho gusto 😊"
            else -> "Gracias por tu mensaje. Estaremos atentos a ayudarte."
        }
    }

    // DIBUJAR MENSAJE DEL USUARIO (EMISOR)
    private fun agregarMensajeUsuario(texto: String) {
        val mensaje = TextView(this).apply {
            text = texto
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#455A64"))
            setPadding(30, 20, 30, 20)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.END
                topMargin = 15
            }
        }
        layoutMensajes.addView(mensaje)
        bajarScroll()
    }

    // DIBUJAR MENSAJE DEL VENDEDOR (RECEPTOR)
    private fun agregarMensajeVendedor(texto: String) {
        val mensaje = TextView(this).apply {
            text = texto
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#263238"))
            setPadding(30, 20, 30, 20)
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.START
                topMargin = 15
            }
        }
        layoutMensajes.addView(mensaje)
        bajarScroll()
    }

    // BAJAR SCROLL AUTOMÁTICAMENTE
    private fun bajarScroll() {
        scrollMensajes.post {
            scrollMensajes.fullScroll(ScrollView.FOCUS_DOWN)
        }
    }
}
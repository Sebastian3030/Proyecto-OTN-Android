package com.example.otn

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ChatActivity : AppCompatActivity() {

    private lateinit var btnVolver: ImageView
    private lateinit var edtMensaje: EditText
    private lateinit var btnEnviar: Button
    private lateinit var layoutMensajes: LinearLayout
    private lateinit var scrollMensajes: ScrollView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        // REFERENCIAS
        btnVolver = findViewById(R.id.btnVolver)
        edtMensaje = findViewById(R.id.edtMensaje)
        btnEnviar = findViewById(R.id.btnEnviar)
        layoutMensajes = findViewById(R.id.layoutMensajes)
        scrollMensajes = findViewById(R.id.scrollMensajes)

        // BOTON VOLVER
        btnVolver.setOnClickListener {
            finish()
        }

        // MENSAJE INICIAL DEL VENDEDOR
        agregarMensajeVendedor(
            "Hola, Soy un boot por el momento :)"
        )
        agregarMensajeVendedor(
            "Hola 👋, gracias por comunicarte. ¿En qué puedo ayudarte? "
        )

        // ENVIAR MENSAJE
        btnEnviar.setOnClickListener {

            val mensaje = edtMensaje.text.toString().trim()

            if (mensaje.isNotEmpty()) {

                agregarMensajeUsuario(mensaje)

                edtMensaje.setText("")

                Handler(Looper.getMainLooper()).postDelayed({

                    val respuesta = obtenerRespuesta(mensaje)

                    agregarMensajeVendedor(respuesta)

                }, 1000)

            }
        }
    }

    // RESPUESTAS AUTOMATICAS
    private fun obtenerRespuesta(mensaje: String): String {

        return when {

            mensaje.contains("hola", true) ->
                "Hola 👋 ¿Cómo estás?"

            mensaje.contains("bien", true) ->
                "Vale, me alegra"

            mensaje.contains("disponible", true) ->
                "Sí, el producto sigue disponible."

            mensaje.contains("precio", true) ->
                "El precio es: 4'500.000"

            mensaje.contains("ubicacion", true) ||
                    mensaje.contains("ubicación", true) ->
                "Nos encontramos en puerto tejada, Cauca."

            mensaje.contains("gracias", true) ->
                "Con mucho gusto 😊"

            else ->
                "Gracias por tu mensaje. Estaremos atentos a ayudarte."
        }
    }

    // MENSAJE USUARIO
    private fun agregarMensajeUsuario(texto: String) {

        val mensaje = TextView(this)

        mensaje.text = texto
        mensaje.setTextColor(Color.WHITE)
        mensaje.setBackgroundColor(
            Color.parseColor("#455A64")
        )

        mensaje.setPadding(
            30,
            20,
            30,
            20
        )

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.gravity = Gravity.END
        params.topMargin = 15

        mensaje.layoutParams = params

        layoutMensajes.addView(mensaje)

        bajarScroll()
    }

    // MENSAJE VENDEDOR
    private fun agregarMensajeVendedor(texto: String) {

        val mensaje = TextView(this)

        mensaje.text = texto
        mensaje.setTextColor(Color.WHITE)
        mensaje.setBackgroundColor(
            Color.parseColor("#263238")
        )

        mensaje.setPadding(
            30,
            20,
            30,
            20
        )

        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        params.gravity = Gravity.START
        params.topMargin = 15

        mensaje.layoutParams = params

        layoutMensajes.addView(mensaje)

        bajarScroll()
    }

    // BAJAR AUTOMATICAMENTE
    private fun bajarScroll() {

        scrollMensajes.post {

            scrollMensajes.fullScroll(
                ScrollView.FOCUS_DOWN
            )

        }
    }
}
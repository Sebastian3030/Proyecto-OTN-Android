package com.example.otn

import android.graphics.Color
import android.os.Bundle
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
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChatActivity : AppCompatActivity() {

    private lateinit var btnVolver: ImageView
    private lateinit var edtMensaje: EditText
    private lateinit var btnEnviar: Button
    private lateinit var layoutMensajes: LinearLayout
    private lateinit var scrollMensajes: ScrollView

    private val idUsuarioActual = "USER_SEBAS_123"
    private val idVendedorOTratante = "VENDEDOR_OTN_456"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        btnVolver = findViewById(R.id.btnVolver)
        edtMensaje = findViewById(R.id.edtMensaje)
        btnEnviar = findViewById(R.id.btnEnviar)
        layoutMensajes = findViewById(R.id.layoutMensajes)
        scrollMensajes = findViewById(R.id.scrollMensajes)

        val topBar = findViewById<LinearLayout>(R.id.topBar)
        val barraEnviar = findViewById<LinearLayout>(R.id.barraEnviar)

        val vistaRaiz = scrollMensajes.parent as View
        ViewCompat.setOnApplyWindowInsetsListener(vistaRaiz) { _, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            val navigationBarsInsets = insets.getInsets(WindowInsetsCompat.Type.navigationBars())
            val imeInsets = insets.getInsets(WindowInsetsCompat.Type.ime())

            topBar.setPadding(topBar.paddingLeft, statusBarInsets.top, topBar.paddingRight, topBar.paddingBottom)
            val espacioInferior = if (imeInsets.bottom > 0) imeInsets.bottom else navigationBarsInsets.bottom
            barraEnviar.setPadding(barraEnviar.paddingLeft, barraEnviar.paddingTop, barraEnviar.paddingRight, espacioInferior)

            bajarScroll()
            insets
        }

        btnVolver.setOnClickListener { finish() }

        // Mensajes semilla del bot
        agregarMensajeVendedor("Hola, soy un bot por el momento :)")
        agregarMensajeVendedor("Hola 👋, gracias por comunicarte. ¿En qué puedo ayudarte?")

        btnEnviar.setOnClickListener {
            val textoMensaje = edtMensaje.text.toString().trim()

            if (textoMensaje.isNotEmpty()) {
                agregarMensajeUsuario(textoMensaje)
                edtMensaje.setText("")

                // 💡 CORRECCIÓN CRÍTICA: Cambiado a Corrutinas seguras ligadas al ciclo de vida
                lifecycleScope.launch {
                    delay(1000)
                    val respuesta = obtenerRespuesta(textoMensaje)
                    agregarMensajeVendedor(respuesta)
                }
            }
        }
    }

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

    private fun agregarMensajeUsuario(texto: String) {
        val mensaje = TextView(this).apply {
            text = texto
            setTextColor(Color.WHITE)
            // 💡 OPTIMIZACIÓN STYLING: Padding generoso y márgenes limpios
            setPadding(35, 22, 35, 22)
            setBackgroundResource(R.drawable.bg_burbuja_usuario) // Recuerda crear este shape con color #455A64 y corners redondeados
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.END
                topMargin = 16
                bottomMargin = 4
            }
        }
        layoutMensajes.addView(mensaje)
        bajarScroll()
    }

    private fun agregarMensajeVendedor(texto: String) {
        val mensaje = TextView(this).apply {
            text = texto
            setTextColor(Color.WHITE)
            setPadding(35, 22, 35, 22)
            setBackgroundResource(R.drawable.bg_burbuja_vendedor) // Shape con color #263238 y corners redondeados
            layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            ).apply {
                gravity = Gravity.START
                topMargin = 16
                bottomMargin = 4
            }
        }
        layoutMensajes.addView(mensaje)
        bajarScroll()
    }

    private fun bajarScroll() {
        scrollMensajes.post { scrollMensajes.fullScroll(ScrollView.FOCUS_DOWN) }
    }
}
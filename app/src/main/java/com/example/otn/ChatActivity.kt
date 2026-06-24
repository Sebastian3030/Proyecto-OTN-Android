package com.example.otn

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.UUID

class ChatActivity : AppCompatActivity() {

    private lateinit var btnVolver: ImageView
    private lateinit var edtMensaje: EditText
    private lateinit var btnEnviar: Button
    private lateinit var recyclerMensajes: RecyclerView

    private var listaDeMensajes = ArrayList<MensajeChat>()
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat) // Asegúrate de cambiar el ScrollView por un RecyclerView con id: recyclerMensajes

        btnVolver = findViewById(R.id.btnVolver)
        edtMensaje = findViewById(R.id.edtMensaje)
        btnEnviar = findViewById(R.id.btnEnviar)
        recyclerMensajes = findViewById(R.id.recyclerMensajes)

        val topBar = findViewById<LinearLayout>(R.id.topBar)
        val barraEnviar = findViewById<LinearLayout>(R.id.barraEnviar)

        // CONFIGURAR RECYCLERVIEW PARA CHAT
        val layoutManager = LinearLayoutManager(this)
        layoutManager.stackFromEnd = true // Hace que el chat empiece desde abajo
        recyclerMensajes.layoutManager = layoutManager

        chatAdapter = ChatAdapter(listaDeMensajes)
        recyclerMensajes.adapter = chatAdapter

        // Control de teclado inteligente
        ViewCompat.setOnApplyWindowInsetsListener(recyclerMensajes) { _, insets ->
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
        agregarMensajeAlChat("Hola, soy un bot por el momento :)", esUsuario = false)
        agregarMensajeAlChat("Hola 👋, gracias por comunicarte. ¿En qué puedo ayudarte?", esUsuario = false)

        btnEnviar.setOnClickListener {
            val textoMensaje = edtMensaje.text.toString().trim()

            if (textoMensaje.isNotEmpty()) {
                agregarMensajeAlChat(textoMensaje, esUsuario = true)
                edtMensaje.setText("")

                lifecycleScope.launch {
                    delay(1000)
                    val respuesta = obtenerRespuesta(textoMensaje)
                    agregarMensajeAlChat(respuesta, esUsuario = false)
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

    private fun agregarMensajeAlChat(texto: String, esUsuario: Boolean) {
        val nuevoMensaje = MensajeChat(
            id = UUID.randomUUID().toString(),
            texto = texto,
            esUsuarioActual = esUsuario,
            hora = "00:00"
        )
        listaDeMensajes.add(nuevoMensaje)

        // Notifica al adaptador que llegó un mensaje nuevo de forma eficiente
        chatAdapter.notifyItemInserted(listaDeMensajes.size - 1)
        bajarScroll()
    }

    private fun bajarScroll() {
        if (listaDeMensajes.isNotEmpty()) {
            recyclerMensajes.smoothScrollToPosition(listaDeMensajes.size - 1)
        }
    }
}
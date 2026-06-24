package com.example.otn

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL
// 🟢 REVISA AQUÍ: Usamos las herramientas nativas de Android para manejar JSON
import org.json.JSONArray

class TestApiActivity : AppCompatActivity() {

    private lateinit var btnDispararApi: Button
    private lateinit var txtResultadoApi: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_api)

        btnDispararApi = findViewById(R.id.btnDispararApi)
        txtResultadoApi = findViewById(R.id.txtResultadoApi)

        btnDispararApi.setOnClickListener {
            txtResultadoApi.text = "Solicitando productos al servidor..."
            ejecutarPruebaDeApi()
        }
    }

    private fun ejecutarPruebaDeApi() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = URL("https://fakestoreapi.com/products")
                val conexion = url.openConnection() as HttpURLConnection
                conexion.requestMethod = "GET"
                conexion.connectTimeout = 5000

                val codigoRespuesta = conexion.responseCode

                if (codigoRespuesta == HttpURLConnection.HTTP_OK) {
                    val cuerpoRespuesta = conexion.inputStream.bufferedReader().use { it.readText() }

                    // =========================================================================
                    // 🧙‍♂️ PROCESAMIENTO NATIVO (SIN LIBRERÍAS)
                    // =========================================================================

                    // 1. Convertimos el texto plano en un Arreglo JSON nativo (porque empieza con '[')
                    val jsonCompleto = JSONArray(cuerpoRespuesta)

                    // 2. Saltamos al hilo principal para pintar el resultado en la pantalla
                    withContext(Dispatchers.Main) {
                        if (jsonCompleto.length() > 0) {

                            // 🟢 EL TRUCO NATIVO: Extraemos el objeto de la posición 0 (el primero de la lista)
                            val primerProducto = jsonCompleto.getJSONObject(0)

                            // 3. Sacamos cada variable de forma manual usando su "llave" exacta del texto
                            val id = primerProducto.getInt("id")
                            val titulo = primerProducto.getString("title")
                            val precio = primerProducto.getDouble("price")
                            val categoria = primerProducto.getString("category")

                            // Pintamos de forma limpia en tu TextView
                            txtResultadoApi.text = "✅ FILTRADO NATIVO (SIN LIBRERÍAS)\n\n" +
                                    "ID: $id\n\n" +
                                    "Título: $titulo\n\n" +
                                    "Precio: $$precio\n\n" +
                                    "Categoría: $categoria"
                        } else {
                            txtResultadoApi.text = "⚠️ El servidor respondió bien, pero el arreglo está vacío."
                        }
                    }
                    // =========================================================================

                } else {
                    withContext(Dispatchers.Main) {
                        txtResultadoApi.text = "❌ ERROR EN EL SERVIDOR: $codigoRespuesta"
                    }
                }
                conexion.disconnect()

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    txtResultadoApi.text = "🚨 FALLO CRÍTICO:\n${e.localizedMessage}"
                }
            }
        }
    }
}
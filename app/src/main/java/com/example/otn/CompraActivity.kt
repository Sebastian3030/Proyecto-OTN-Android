package com.example.otn

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

class CompraActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

    private lateinit var btnMas: Button
    private lateinit var btnMenos: Button
    private lateinit var btnContinuar: Button

    private lateinit var txtCantidad: TextView
    private lateinit var txtSubtotal: TextView
    private lateinit var txtTotal: TextView

    private lateinit var spinnerPago: Spinner

    private var cantidad = 1

    private val precioUnitario = 4500000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compra)

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)

        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)


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

        // CONTROLES
        btnMas = findViewById(R.id.btnMas)
        btnMenos = findViewById(R.id.btnMenos)
        btnContinuar = findViewById(R.id.btnContinuar)

        txtCantidad = findViewById(R.id.txtCantidad)
        txtSubtotal = findViewById(R.id.txtSubtotal)
        txtTotal = findViewById(R.id.txtTotal)

        spinnerPago = findViewById(R.id.spinnerPago)

        // MENU HAMBURGUESA
        btnMenu.setOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)

        }

        // MENU LATERAL

        txtInicio.setOnClickListener {

            finish()

        }

        // POPUP MENÚ PERFIL - SOLUCIÓN DEFINITIVA MATERIAL 3 (COMO EL HOME)
        imgPerfil.setOnClickListener {
            // 1. Inflamos el menú usando un Contexto con el tema oscuro de tu App
            val contextoOscuro = androidx.appcompat.view.ContextThemeWrapper(this, R.style.PopupMenuStyle)
            val popupMenu = PopupMenu(contextoOscuro, imgPerfil)

            // 2. Agregamos las opciones
            popupMenu.menu.add(0, 0, 0, "👤 Sebastian")
            popupMenu.menu.add(0, 1, 1, "✏️ Editar perfil")
            popupMenu.menu.add(0, 2, 2, "🏢 Vincular negocio")
            popupMenu.menu.add(0, 3, 3, "❤️ Favoritos")
            popupMenu.menu.add(0, 4, 4, "🗒️ Historial de citas")
            popupMenu.menu.add(0, 5, 5, "📅 Agendar citas")
            popupMenu.menu.add(0, 6, 6, "📖 Historial de compras")
            popupMenu.menu.add(0, 7, 7, "💷 Historial de pagos")
            popupMenu.menu.add(0, 8, 8, "🚪 Cerrar sesión")

            // 3. Forzamos el color del texto a blanco puro
            for (i in 0 until popupMenu.menu.size()) {
                val menuItem = popupMenu.menu.getItem(i)
                val spanString = SpannableString(menuItem.title)
                spanString.setSpan(ForegroundColorSpan(Color.WHITE), 0, spanString.length, 0)
                menuItem.title = spanString
            }

            popupMenu.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    0 -> startActivity(Intent(this, ProfileActivity::class.java))
                    1 -> startActivity(Intent(this, EditarPerfilActivity::class.java))
                    2 -> { /* Ya estamos aquí */ }
                    3 -> startActivity(Intent(this, FavoritosActivity::class.java))
                    4 -> startActivity(Intent(this, HistorialCitasActivity::class.java))
                    5 -> startActivity(Intent(this, AgendarCitaActivity::class.java))
                    6 -> startActivity(Intent(this, HistorialComprasActivity::class.java))
                    7 -> startActivity(Intent(this, HistorialPagosActivity::class.java))
                    8 -> {
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    }
                }
                true
            }
            popupMenu.show()
        }




        txtMarketplace.setOnClickListener {

            Toast.makeText(
                this,
                "Marketplace",
                Toast.LENGTH_SHORT
            ).show()

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        txtProductos.setOnClickListener {

            Toast.makeText(
                this,
                "Productos",
                Toast.LENGTH_SHORT
            ).show()

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        txtCerrarSesion.setOnClickListener {

            Toast.makeText(
                this,
                "Sesión cerrada",
                Toast.LENGTH_SHORT
            ).show()

            finish()

        }

        // SPINNER
        val metodosPago = arrayOf(
            "Seleccionar método de pago",
            "Nequi",
            "Bancolombia",
            "Davivienda",
            "BBVA",
            "Banco de Bogotá",
            "Tarjeta Débito",
            "Tarjeta Crédito"
        )

        val adapter = ArrayAdapter(
            this,
            R.layout.item_spinner,
            metodosPago
        )

        adapter.setDropDownViewResource(
            R.layout.item_spinner_dropdown
        )

        spinnerPago.adapter = adapter

        actualizarTotal()

        // BOTON +
        btnMas.setOnClickListener {

            cantidad++

            actualizarTotal()

        }

        // BOTON -
        btnMenos.setOnClickListener {

            if (cantidad > 1) {

                cantidad--

                actualizarTotal()

            }

        }

        // CONTINUAR
        btnContinuar.setOnClickListener {

            if (spinnerPago.selectedItemPosition == 0) {

                Toast.makeText(
                    this,
                    "Seleccione un método de pago",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener

            }

            val metodoSeleccionado =
                spinnerPago.selectedItem.toString()

            val intent = Intent(
                this,
                PagoActivity::class.java
            )

            intent.putExtra(
                "metodoPago",
                metodoSeleccionado
            )

            intent.putExtra(
                "cantidad",
                cantidad
            )

            intent.putExtra(
                "total",
                cantidad * precioUnitario
            )

            startActivity(intent)

        }

    }

    private fun actualizarTotal() {

        val total = cantidad * precioUnitario

        txtCantidad.text = cantidad.toString()

        txtSubtotal.text =
            "Subtotal: $" +
                    String.format("%,d", total)
                        .replace(',', '.')

        txtTotal.text =
            "Total: $" +
                    String.format("%,d", total)
                        .replace(',', '.')

    }

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START)

        } else {

            super.onBackPressed()

        }

    }
}
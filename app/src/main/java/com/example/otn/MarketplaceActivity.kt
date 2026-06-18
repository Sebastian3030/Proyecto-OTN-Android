package com.example.otn

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

class MarketplaceActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    // MENU
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtPublicar: TextView
    private lateinit var txtCerrarSesion: TextView

    // BOTONES
    private lateinit var btnTodos: Button
    private lateinit var btnTecnologia: Button
    private lateinit var btnRopa: Button
    private lateinit var btnSpa: Button

    // CARDS
    private lateinit var cardTec1: View
    private lateinit var cardTec2: View
    private lateinit var cardRopa1: View
    private lateinit var cardRopa2: View
    private lateinit var cardSpa1: View
    private lateinit var cardSpa2: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)

        // TOPBAR
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // MENU
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtPublicar = findViewById(R.id.txtPublicar)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        // BOTONES
        btnTodos = findViewById(R.id.btnTodos)
        btnTecnologia = findViewById(R.id.btnTecnologia)
        btnRopa = findViewById(R.id.btnRopa)
        btnSpa = findViewById(R.id.btnSpa)

        // CARDS
        cardTec1 = findViewById(R.id.cardTec1)
        cardTec2 = findViewById(R.id.cardTec2)
        cardRopa1 = findViewById(R.id.cardRopa1)
        cardRopa2 = findViewById(R.id.cardRopa2)
        cardSpa1 = findViewById(R.id.cardSpa1)
        cardSpa2 = findViewById(R.id.cardSpa2)

        // ABRIR MENU
        btnMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        // MENU PERFIL OPTIMIZADO (FONDO OSCURO INTEGRADO)
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

        // NAVEGACIÓN MENÚ LATERAL
        txtInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtMarketplace.setOnClickListener {
            Toast.makeText(this, "Ya estás en Marketplace", Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtPublicar.setOnClickListener {
            startActivity(Intent(this, PublicarActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtCerrarSesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        // ========================================================
        // NUEVA NAVEGACIÓN: ENVIAR CLIC HACIA DETALLEPRODUCTOACTIVITY
        // ========================================================

        cardTec1.setOnClickListener {
            val intent = Intent(this, DetalleProductoActivity::class.java)
            intent.putExtra("nombre", "iPhone 15")
            intent.putExtra("precio", "$4.500.000")
            startActivity(intent)
        }

        cardTec2.setOnClickListener {
            val intent = Intent(this, DetalleProductoActivity::class.java)
            intent.putExtra("nombre", "Computador Gamer")
            intent.putExtra("precio", "$3.200.000")
            startActivity(intent)
        }

        cardRopa1.setOnClickListener {
            val intent = Intent(this, DetalleProductoActivity::class.java)
            intent.putExtra("nombre", "Chaqueta")
            intent.putExtra("precio", "$180.000")
            startActivity(intent)
        }

        cardRopa2.setOnClickListener {
            val intent = Intent(this, DetalleProductoActivity::class.java)
            intent.putExtra("nombre", "Vestido")
            intent.putExtra("precio", "$220.000")
            startActivity(intent)
        }

        cardSpa1.setOnClickListener {
            val intent = Intent(this, DetalleProductoActivity::class.java)
            intent.putExtra("nombre", "Spa relajante")
            intent.putExtra("precio", "$120.000")
            startActivity(intent)
        }

        cardSpa2.setOnClickListener {
            val intent = Intent(this, DetalleProductoActivity::class.java)
            intent.putExtra("nombre", "Masajes")
            intent.putExtra("precio", "$90.000")
            startActivity(intent)
        }

        // ==========================================
        // LÓGICA LOGRADA: RECIBIR E INTERPRETAR FILTRO
        // ==========================================
        val categoria = intent.getStringExtra("categoria")

        if (categoria != null) {
            when (categoria) {
                "tecnologia" -> {
                    ocultarTodo()
                    cardTec1.visibility = View.VISIBLE
                    cardTec2.visibility = View.VISIBLE
                    actualizarBotones(btnTecnologia)
                }
                "ropa" -> {
                    ocultarTodo()
                    cardRopa1.visibility = View.VISIBLE
                    cardRopa2.visibility = View.VISIBLE
                    actualizarBotones(btnRopa)
                }
                "spa" -> {
                    ocultarTodo()
                    cardSpa1.visibility = View.VISIBLE
                    cardSpa2.visibility = View.VISIBLE
                    actualizarBotones(btnSpa)
                }
                else -> {
                    mostrarTodos()
                    actualizarBotones(btnTodos)
                }
            }
        } else {
            mostrarTodos()
            actualizarBotones(btnTodos)
        }

        // ACCIONES DE FILTRO MANUAL DE BOTONES INTERNOS
        btnTodos.setOnClickListener {
            mostrarTodos()
            actualizarBotones(btnTodos)
        }

        btnTecnologia.setOnClickListener {
            ocultarTodo()
            cardTec1.visibility = View.VISIBLE
            cardTec2.visibility = View.VISIBLE
            actualizarBotones(btnTecnologia)
        }

        btnRopa.setOnClickListener {
            ocultarTodo()
            cardRopa1.visibility = View.VISIBLE
            cardRopa2.visibility = View.VISIBLE
            actualizarBotones(btnRopa)
        }

        btnSpa.setOnClickListener {
            ocultarTodo()
            cardSpa1.visibility = View.VISIBLE
            cardSpa2.visibility = View.VISIBLE
            actualizarBotones(btnSpa)
        }
    }

    private fun mostrarTodos() {
        cardTec1.visibility = View.VISIBLE
        cardTec2.visibility = View.VISIBLE
        cardRopa1.visibility = View.VISIBLE
        cardRopa2.visibility = View.VISIBLE
        cardSpa1.visibility = View.VISIBLE
        cardSpa2.visibility = View.VISIBLE
    }

    private fun ocultarTodo() {
        cardTec1.visibility = View.GONE
        cardTec2.visibility = View.GONE
        cardRopa1.visibility = View.GONE
        cardRopa2.visibility = View.GONE
        cardSpa1.visibility = View.GONE
        cardSpa2.visibility = View.GONE
    }

    private fun actualizarBotones(botonActivo: Button) {
        btnTodos.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#1AFFFFFF"))
        btnTecnologia.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#1AFFFFFF"))
        btnRopa.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#1AFFFFFF"))
        btnSpa.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#1AFFFFFF"))

        botonActivo.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#00BFFF"))
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
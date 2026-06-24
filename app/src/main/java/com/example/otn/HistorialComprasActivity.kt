package com.example.otn

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Clase modelo para estructurar cada ítem de compra
data class Compra(val id: String, val producto: String, val precio: String, val fecha: String)

class HistorialComprasActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView
    private lateinit var menuLateral: LinearLayout // Agregado para el control táctil

    // MENU LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

    // CONTENIDO
    private lateinit var recyclerCompras: RecyclerView
    private lateinit var cardSinCompras: LinearLayout

    // Arreglo para manejar la lista de datos dinámicos
    private var listaDeCompras = ArrayList<Compra>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_compras)

        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)
        menuLateral = findViewById(R.id.menuLateral) // Inicializado correctamente

        // ESCUDO DE CLICKS DEFENSIVO: Bloquea toques fantasma hacia el fondo de la pantalla
        menuLateral.setOnClickListener {
            // Deja este bloque vacío intencionalmente. Absorbe los eventos táctiles.
        }

        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        recyclerCompras = findViewById(R.id.recyclerCompras)
        cardSinCompras = findViewById(R.id.cardSinCompras)

        recyclerCompras.layoutManager = LinearLayoutManager(this)

        // VALIDACIÓN DINÁMICA DE ELEMENTOS
        verificarYMostrarCompras()

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        txtInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        txtMarketplace.setOnClickListener {
            startActivity(Intent(this, MarketplaceActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtProductos.setOnClickListener {
            startActivity(Intent(this, MisProductosActivity::class.java)) // Apuntado a MisProductosActivity para consistencia
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtCerrarSesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        // POPUP MENÚ PERFIL (Unificado)
        imgPerfil.setOnClickListener {
            val contextoOscuro = ContextThemeWrapper(this, R.style.PopupMenuStyle)
            val popupMenu = PopupMenu(contextoOscuro, imgPerfil)

            popupMenu.menu.add(0, 0, 0, "👤 Sebastian")
            popupMenu.menu.add(0, 1, 1, "✏️ Editar perfil")
            popupMenu.menu.add(0, 2, 2, "🏢 Vincular negocio")
            popupMenu.menu.add(0, 3, 3, "❤️ Favoritos")
            popupMenu.menu.add(0, 4, 4, "🗒️ Historial de citas")
            popupMenu.menu.add(0, 5, 5, "📅 Agendar citas")
            popupMenu.menu.add(0, 6, 6, "📖 Historial de compras")
            popupMenu.menu.add(0, 7, 7, "💷 Historial de pagos")
            popupMenu.menu.add(0, 8, 8, "🚪 Cerrar sesión")

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
                    2 -> startActivity(Intent(this, PublicarActivity::class.java))
                    3 -> startActivity(Intent(this, FavoritosActivity::class.java))
                    4 -> startActivity(Intent(this, HistorialCitasActivity::class.java))
                    5 -> startActivity(Intent(this, AgendarCitaActivity::class.java))
                    6 -> drawerLayout.closeDrawer(GravityCompat.START) // Ya estamos aquí, cerramos drawer
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
    }

    private fun verificarYMostrarCompras() {
        listaDeCompras.clear()

        // TODO: Aquí conectarás la lógica de tu base de datos local o API.
        // Si quieres probar cómo se ve con ítems comprados, descomenta las dos líneas de abajo:
        // listaDeCompras.add(Compra("1", "iPhone 17 Pro Max", "💰 Total: $4.500.000", "📅 Comprado el: 22 de Junio, 2026"))
        // listaDeCompras.add(Compra("2", "Chaqueta Cuero Premium", "💰 Total: $250.000", "📅 Comprado el: 18 de Junio, 2026"))

        if (listaDeCompras.isEmpty()) {
            // No hay compras: Muestra el letrero de advertencia y oculta el RecyclerView
            cardSinCompras.visibility = View.VISIBLE
            recyclerCompras.visibility = View.GONE
        } else {
            // Sí hay compras: Esconde el letrero vacío y activa la visualización por lista
            cardSinCompras.visibility = View.GONE
            recyclerCompras.visibility = View.VISIBLE

            // Asigna el adaptador de compras cargándole el listado correspondiente
            recyclerCompras.adapter = ComprasAdapter(listaDeCompras)
        }
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
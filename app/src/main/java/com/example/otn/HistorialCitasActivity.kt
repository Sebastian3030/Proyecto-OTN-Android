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

// Clase modelo para estructurar cada ítem de cita
data class Cita(val id: String, val servicio: String, val fechaHora: String, val estado: String)

class HistorialCitasActivity : AppCompatActivity() {

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
    private lateinit var recyclerCitas: RecyclerView
    private lateinit var cardSinCitas: LinearLayout

    // Arreglo para manejar la lista de datos dinámicos
    private var listaDeCitas = ArrayList<Cita>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_citas)

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

        recyclerCitas = findViewById(R.id.recyclerCitas)
        cardSinCitas = findViewById(R.id.cardSinCitas)

        recyclerCitas.layoutManager = LinearLayoutManager(this)

        // VALIDACIÓN DINÁMICA DE ELEMENTOS
        verificarYMostrarCitas()

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
            startActivity(Intent(this, MisProductosActivity::class.java)) // Apuntado a MisProductosActivity para consistencia con tu Home
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
                    4 -> drawerLayout.closeDrawer(GravityCompat.START) // Ya estamos aquí
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
    }

    private fun verificarYMostrarCitas() {
        listaDeCitas.clear()

        // TODO: Aquí conectarás la lógica de tu base de datos local o API.
        // Si quieres probar cómo se ve con ítems agendados, descomenta las dos líneas de abajo:
        // listaDeCitas.add(Cita("1", "Masaje Relajante SPA", "📅 25 de Octubre - 04:00 PM", "● Confirmada"))
        // listaDeCitas.add(Cita("2", "Limpieza Facial Profunda", "📅 30 de Octubre - 11:00 AM", "● Pendiente"))

        if (listaDeCitas.isEmpty()) {
            // No hay citas: Muestra el letrero de advertencia y oculta el RecyclerView
            cardSinCitas.visibility = View.VISIBLE
            recyclerCitas.visibility = View.GONE
        } else {
            // Sí hay citas: Esconde el letrero vacío y activa la visualización por lista
            cardSinCitas.visibility = View.GONE
            recyclerCitas.visibility = View.VISIBLE

            // Asigna el adaptador cargándole el listado correspondiente
            recyclerCitas.adapter = CitasAdapter(listaDeCitas)
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
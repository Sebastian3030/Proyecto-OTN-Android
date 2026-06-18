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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistorialComprasActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    // CONTROLADORES DE ESTADO DE LA LISTA
    private lateinit var cardSinCompras: LinearLayout
    private lateinit var recyclerCompras: RecyclerView

    // MENU LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_compras)

        // DRAWER Y TOPBAR
        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        // PARCHE PUNCH HOLE (EVITAR QUE LA CÁMARA TAPE LA TOPBAR)
        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(
                view.paddingLeft,
                statusBarInsets.top,
                view.paddingRight,
                view.paddingBottom
            )
            insets
        }

        // VISTAS DE CONTROL
        cardSinCompras = findViewById(R.id.cardSinCompras)
        recyclerCompras = findViewById(R.id.recyclerCompras)

        // CONFIGURACIÓN INICIAL DEL RECYCLERVIEW
        recyclerCompras.layoutManager = LinearLayoutManager(this)

        // --- NOTA PARA DESARROLLO ---
        // Por defecto dejamos 'cardSinCompras' visible. Cuando vincules tu base de datos o API:
        // Si hay compras: cardSinCompras.visibility = View.GONE; recyclerCompras.visibility = View.view.VISIBLE
        // Si no hay compras: cardSinCompras.visibility = View.VISIBLE; recyclerCompras.visibility = View.GONE

        // REFERENCIAS MENU LATERAL
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        // ==========================================
        // ACCIÓN MENÚ LATERAL (ABRIR)
        // ==========================================
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // ==========================================
        // CLICS MENÚ LATERAL (NAVEGACIÓN)
        // ==========================================
        txtInicio.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        txtMarketplace.setOnClickListener {
            val intent = Intent(this, MarketplaceActivity::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtProductos.setOnClickListener {
            val intent = Intent(this, PublicarActivity::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtCerrarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }

        // ==========================================
        // POPUP MENU PERFIL (SUPERIOR DERECHO)
        // ==========================================
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
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
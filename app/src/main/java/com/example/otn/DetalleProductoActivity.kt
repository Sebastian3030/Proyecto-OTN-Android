package com.example.otn

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
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

class DetalleProductoActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    // TOPBAR
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    // COMPONENTES DINÁMICOS Y CORAZÓN
    private lateinit var txtNombreProducto: TextView
    private lateinit var txtPrecio: TextView
    private lateinit var btnFavorito: ImageView
    private var esFavorito = false

    // BOTONES
    private lateinit var btnContactar: Button
    private lateinit var btnComprar: Button

    // MENU LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtPublicar: TextView
    private lateinit var txtCerrarSesion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)

        // TOPBAR
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        // COMPONENTES DINÁMICOS Y CORAZÓN
        txtNombreProducto = findViewById(R.id.txtNombreProducto)
        txtPrecio = findViewById(R.id.txtPrecio)

        // CORAZÓN VINCULADO E INICIALIZADO EN BLANCO
        btnFavorito = findViewById(R.id.btnFavorito)
        btnFavorito.setColorFilter(Color.WHITE) // 👈 Solución para el error del XML tint

        // BOTONES
        btnContactar = findViewById(R.id.btnContactar)
        btnComprar = findViewById(R.id.btnComprar)

        // RECIBIR DATOS DEL HOME O MARKETPLACE
        val nombre = intent.getStringExtra("nombre") ?: "iPhone 17 Pro Max"
        val precio = intent.getStringExtra("precio") ?: "$4.500.000"

        txtNombreProducto.text = nombre
        txtPrecio.text = precio

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

        // LÓGICA INTERACTIVA DEL CORAZÓN (FAVORITOS)
        btnFavorito.setOnClickListener {
            if (!esFavorito) {
                // Se rellena el corazón y se pinta de rojo
                btnFavorito.setImageResource(R.drawable.ic_corazon_lleno)
                btnFavorito.setColorFilter(Color.parseColor("#FF5252")) // Rojo brillante
                Toast.makeText(this, "Añadido a Favoritos ❤️", Toast.LENGTH_SHORT).show()
                esFavorito = true
            } else {
                // Vuelve a estar vacío y de color blanco
                btnFavorito.setImageResource(R.drawable.ic_corazon_vacio)
                btnFavorito.setColorFilter(Color.WHITE)
                Toast.makeText(this, "Eliminado de Favoritos", Toast.LENGTH_SHORT).show()
                esFavorito = false
            }
        }

        // MENU LATERAL
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtPublicar = findViewById(R.id.txtPublicar)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        // ABRIR MENU
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // MENU PERFIL
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

        // MENU LATERAL - INICIO
        txtInicio.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        // MENU LATERAL - MARKETPLACE
        txtMarketplace.setOnClickListener {
            val intent = Intent(this, MarketplaceActivity::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        // MENU LATERAL - PUBLICAR
        txtPublicar.setOnClickListener {
            val intent = Intent(this, PublicarActivity::class.java)
            startActivity(intent)
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        // MENU LATERAL - CERRAR SESION
        txtCerrarSesion.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // BOTON CONTACTAR
        btnContactar.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            startActivity(intent)
        }

        // BOTON COMPRAR
        btnComprar.setOnClickListener {
            val intent = Intent(this, CompraActivity::class.java)
            startActivity(intent)
        }
    }

    // BOTON ATRAS
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
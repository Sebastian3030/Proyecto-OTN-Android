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
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

class DetalleProductoActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    private lateinit var txtNombreProducto: TextView
    private lateinit var txtPrecio: TextView
    private lateinit var btnFavorito: ImageView
    private var esFavorito = false

    private lateinit var btnContactar: Button
    private lateinit var btnComprar: Button

    // MENÚ LATERAL UNIFICADO
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtPublicar: TextView // 🛠️ CORREGIDO: Cambiado de txtProductos a txtPublicar
    private lateinit var txtCerrarSesion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        // 1. VINCULAR VISTAS
        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        txtNombreProducto = findViewById(R.id.txtNombreProducto)
        txtPrecio = findViewById(R.id.txtPrecio)
        btnFavorito = findViewById(R.id.btnFavorito)

        btnContactar = findViewById(R.id.btnContactar)
        btnComprar = findViewById(R.id.btnComprar)

        // 2. RECUPERAR DATOS DEL INTENT
        val nombre = intent.getStringExtra("nombre") ?: "Producto OTN"
        val precio = intent.getStringExtra("precio") ?: "$0.00"

        txtNombreProducto.text = nombre
        txtPrecio.text = precio

        // 3. PARCHE PUNCH HOLE
        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // 4. LÓGICA DE FAVORITOS
        btnFavorito.setOnClickListener {
            if (!esFavorito) {
                btnFavorito.setImageResource(R.drawable.ic_corazon_lleno)
                btnFavorito.setColorFilter(Color.parseColor("#FF5252"))
                Toast.makeText(this, "Añadido a Favoritos ❤️", Toast.LENGTH_SHORT).show()
                esFavorito = true
            } else {
                btnFavorito.setImageResource(R.drawable.ic_corazon_vacio)
                btnFavorito.clearColorFilter()
                Toast.makeText(this, "Eliminado de Favoritos", Toast.LENGTH_SHORT).show()
                esFavorito = false
            }
        }

        // 5. ENLAZAR MENÚ LATERAL REAL DEL XML
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtPublicar = findViewById(R.id.txtPublicar) // 🛠️ CORREGIDO: ID coincide con tu XML real
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // 6. POPUP MENÚ PERFIL
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

        // NAVEGACIÓN DRAWER
        txtInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }

        txtMarketplace.setOnClickListener {
            startActivity(Intent(this, MarketplaceActivity::class.java))
            finish()
        }

        txtPublicar.setOnClickListener { // 🛠️ CORREGIDO
            startActivity(Intent(this, PublicarActivity::class.java))
            finish()
        }

        txtCerrarSesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        // BOTONES DE ACCIÓN
        btnContactar.setOnClickListener {
            try {
                startActivity(Intent(this, ChatActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "Pantalla de chat en desarrollo", Toast.LENGTH_SHORT).show()
            }
        }

        btnComprar.setOnClickListener {
            try {
                startActivity(Intent(this, CompraActivity::class.java))
            } catch (e: Exception) {
                Toast.makeText(this, "Pantalla de compra en desarrollo", Toast.LENGTH_SHORT).show()
            }
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
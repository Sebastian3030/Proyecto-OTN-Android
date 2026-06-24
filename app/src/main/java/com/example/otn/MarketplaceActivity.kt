package com.example.otn

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MarketplaceActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView
    private lateinit var etBuscador: EditText
    private lateinit var menuLateral: LinearLayout // 🟢 Control táctil del menú lateral

    // MENU LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtPublicar: TextView
    private lateinit var txtCerrarSesion: TextView

    // BOTONES FILTROS
    private lateinit var btnTodos: Button
    private lateinit var btnTecnologia: Button
    private lateinit var btnRopa: Button
    private lateinit var btnSpa: Button

    // RECYCLERVIEW DINÁMICO
    private lateinit var rvMarketplace: RecyclerView
    private var listaCompletaProductos = ArrayList<Producto>()
    private var listaFiltradaProductos = ArrayList<Producto>()
    private lateinit var adapter: ProductosHomeAdapter

    // 🟢 Estados globales para permitir filtrado cruzado (Categoría + Texto)
    private var categoriaSeleccionadaActual: String? = null
    private var textoBusquedaActual: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        // 1. VINCULAR VISTAS EXACTAS
        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)
        etBuscador = findViewById(R.id.etBuscador)
        menuLateral = findViewById(R.id.menuLateral) // 🟢 Inicializado

        // Escudo de clics defensivo
        menuLateral.setOnClickListener { }

        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtPublicar = findViewById(R.id.txtPublicar)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        btnTodos = findViewById(R.id.btnTodos)
        btnTecnologia = findViewById(R.id.btnTecnologia)
        btnRopa = findViewById(R.id.btnRopa)
        btnSpa = findViewById(R.id.btnSpa)

        rvMarketplace = findViewById(R.id.rvMarketplace)

        // 2. CONFIGURAR EL RECYCLERVIEW EN GRILLA (2 Columnas)
        rvMarketplace.layoutManager = GridLayoutManager(this, 2)

        generarProductosMock()

        // Inicialización limpia
        adapter = ProductosHomeAdapter(listaFiltradaProductos)
        rvMarketplace.adapter = adapter

        // 3. PARCHE PUNCH HOLE
        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        btnMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        // 5. NAVEGACIÓN MENÚ LATERAL CORREGIDA
        txtInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
            finish() // 🟢 Mantiene el Stack limpio, pero el Home siempre debe poder relanzarse
        }
        txtMarketplace.setOnClickListener { drawerLayout.closeDrawer(GravityCompat.START) }
        txtPublicar.setOnClickListener {
            startActivity(Intent(this, PublicarActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
            finish()
        }
        txtCerrarSesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        // 6. POPUP MENU PERFIL UNIFICADO
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
                    2 -> {
                        startActivity(Intent(this, PublicarActivity::class.java))
                        finish()
                    }
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

        // 7. LÓGICA DE FILTRADO UNIFICADA
        categoriaSeleccionadaActual = intent.getStringExtra("categoria")
        aplicarFiltrosCombinados()

        btnTodos.setOnClickListener {
            categoriaSeleccionadaActual = null
            aplicarFiltrosCombinados()
        }
        btnTecnologia.setOnClickListener {
            categoriaSeleccionadaActual = "tecnologia"
            aplicarFiltrosCombinados()
        }
        btnRopa.setOnClickListener {
            categoriaSeleccionadaActual = "ropa"
            aplicarFiltrosCombinados()
        }
        btnSpa.setOnClickListener {
            categoriaSeleccionadaActual = "spa"
            aplicarFiltrosCombinados()
        }

        // 8. LÓGICA DEL BUSCADOR EN TIEMPO REAL
        etBuscador.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                textoBusquedaActual = s.toString().lowercase().trim()
                aplicarFiltrosCombinados()
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun generarProductosMock() {
        listaCompletaProductos.clear()
        listaCompletaProductos.add(Producto("1", "iPhone 15", "$4.500.000", "tecnologia", R.drawable.iphone_17))
        listaCompletaProductos.add(Producto("2", "Computador Gamer", "$3.200.000", "tecnologia", R.drawable.sonidos))
        listaCompletaProductos.add(Producto("3", "Chaqueta Moderna", "$180.000", "ropa", R.drawable.vestido))
        listaCompletaProductos.add(Producto("4", "Vestido Elegante", "$220.000", "ropa", R.drawable.vestido))
        listaCompletaProductos.add(Producto("5", "Spa Relajante", "$120.000", "spa", R.drawable.masajes))
        listaCompletaProductos.add(Producto("6", "Masajes Reductores", "$90.000", "spa", R.drawable.masajes))
    }

    // 🟢 SOLUCIÓN MAESTRA: Este método unifica ambos filtros para que trabajen en equipo
    private fun aplicarFiltrosCombinados() {
        listaFiltradaProductos.clear()

        val resultado = listaCompletaProductos.filter { producto ->
            val coincideCategoria = if (categoriaSeleccionadaActual == null) true
            else producto.categoria.lowercase() == categoriaSeleccionadaActual!!.lowercase()

            val coincideTexto = if (textoBusquedaActual.isEmpty()) true
            else producto.nombre.lowercase().contains(textoBusquedaActual)

            coincideCategoria && coincideTexto
        }

        listaFiltradaProductos.addAll(resultado)
        adapter.notifyDataSetChanged()

        // Actualiza visualmente los botones según el estado
        when (categoriaSeleccionadaActual) {
            null -> actualizarEstiloBotones(btnTodos)
            "tecnologia" -> actualizarEstiloBotones(btnTecnologia)
            "ropa" -> actualizarEstiloBotones(btnRopa)
            "spa" -> actualizarEstiloBotones(btnSpa)
        }
    }

    private fun actualizarEstiloBotones(botonActivo: Button) {
        val colorInactivo = ColorStateList.valueOf(Color.parseColor("#1AFFFFFF"))
        btnTodos.backgroundTintList = colorInactivo
        btnTecnologia.backgroundTintList = colorInactivo
        btnRopa.backgroundTintList = colorInactivo
        btnSpa.backgroundTintList = colorInactivo

        botonActivo.backgroundTintList = ColorStateList.valueOf(Color.parseColor("#00BFFF"))
    }

    // 🟢 CORRECCIÓN: Si el usuario da atrás y el drawer está cerrado, vuelve al Home limpiamente
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }
}
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
import android.widget.Toast
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
    private lateinit var adapter: ProductosHomeAdapter // Reutilizamos tu adaptador de productos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        // 1. VINCULAR VISTAS EXACTAS DE TU XML
        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)
        etBuscador = findViewById(R.id.etBuscador)

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

        // Cargar la lista base de prueba (simulando tu BD)
        generarProductosMock()

        // Inicializamos el adaptador con la lista filtrada (al inicio muestra todos)
        listaFiltradaProductos.addAll(listaCompletaProductos)
        adapter = ProductosHomeAdapter(listaFiltradaProductos)
        rvMarketplace.adapter = adapter

        // 3. PARCHE PUNCH HOLE (Muesca de la cámara)
        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // 4. ACCIÓN BOTÓN MENÚ LATERAL
        btnMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        // 5. NAVEGACIÓN MENÚ LATERAL
        txtInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        txtMarketplace.setOnClickListener { drawerLayout.closeDrawer(GravityCompat.START) }
        txtPublicar.setOnClickListener {
            startActivity(Intent(this, PublicarActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
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

        // 7. LÓGICA DE FILTRADO POR BOTONES
        val categoriaInicial = intent.getStringExtra("categoria")
        filtrarPorCategoria(categoriaInicial)

        btnTodos.setOnClickListener { filtrarPorCategoria(null) }
        btnTecnologia.setOnClickListener { filtrarPorCategoria("tecnologia") }
        btnRopa.setOnClickListener { filtrarPorCategoria("ropa") }
        btnSpa.setOnClickListener { filtrarPorCategoria("spa") }

        // 8. LÓGICA DEL BUSCADOR DE TEXTO (Para buscar en tiempo real)
        etBuscador.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                buscarProductoPorTexto(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Simulador de datos de tu Backend
    private fun generarProductosMock() {
        listaCompletaProductos.clear()
        listaCompletaProductos.add(Producto("1", "iPhone 15", "$4.500.000", "tecnologia", R.drawable.iphone_17))
        listaCompletaProductos.add(Producto("2", "Computador Gamer", "$3.200.000", "tecnologia", R.drawable.sonidos))
        listaCompletaProductos.add(Producto("3", "Chaqueta Moderna", "$180.000", "ropa", R.drawable.vestido))
        listaCompletaProductos.add(Producto("4", "Vestido Elegante", "$220.000", "ropa", R.drawable.vestido))
        listaCompletaProductos.add(Producto("5", "Spa Relajante", "$120.000", "spa", R.drawable.masajes))
        listaCompletaProductos.add(Producto("6", "Masajes Reductores", "$90.000", "spa", R.drawable.masajes))
    }

    private fun filtrarPorCategoria(categoria: String?) {
        listaFiltradaProductos.clear()
        if (categoria == null) {
            listaFiltradaProductos.addAll(listaCompletaProductos)
            actualizarEstiloBotones(btnTodos)
        } else {
            val filtrados = listaCompletaProductos.filter { it.categoria.lowercase() == categoria.lowercase() }
            listaFiltradaProductos.addAll(filtrados)
            when (categoria) {
                "tecnologia" -> actualizarEstiloBotones(btnTecnologia)
                "ropa" -> actualizarEstiloBotones(btnRopa)
                "spa" -> actualizarEstiloBotones(btnSpa)
            }
        }
        adapter.notifyDataSetChanged()
    }

    private fun buscarProductoPorTexto(texto: String) {
        val query = texto.lowercase().trim()
        listaFiltradaProductos.clear()
        if (query.isEmpty()) {
            listaFiltradaProductos.addAll(listaCompletaProductos)
        } else {
            val filtrados = listaCompletaProductos.filter { it.nombre.lowercase().contains(query) }
            listaFiltradaProductos.addAll(filtrados)
        }
        adapter.notifyDataSetChanged()
    }

    private fun actualizarEstiloBotones(botonActivo: Button) {
        val colorInactivo = ColorStateList.valueOf(Color.parseColor("#1AFFFFFF"))
        btnTodos.backgroundTintList = colorInactivo
        btnTecnologia.backgroundTintList = colorInactivo
        btnRopa.backgroundTintList = colorInactivo
        btnSpa.backgroundTintList = colorInactivo

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
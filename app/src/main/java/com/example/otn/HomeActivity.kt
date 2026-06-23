package com.example.otn

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
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
import androidx.viewpager2.widget.ViewPager2

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    // CARRUSEL ANIMADO
    private lateinit var viewPagerCarrusel: ViewPager2
    private val sliderHandler = Handler(Looper.getMainLooper())
    private lateinit var sliderRunnable: Runnable

    // MENÚ LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

    // CATEGORÍAS
    private lateinit var cardTecnologia: LinearLayout
    private lateinit var cardRopa: LinearLayout
    private lateinit var cardSpa: LinearLayout

    // RECYCLERVIEW
    private lateinit var rvProductosHome: RecyclerView
    private var listaDeProductos = ArrayList<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // 1. INICIALIZAR VISTAS
        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        // 2. PARCHE PUNCH HOLE (MUESCA DE CÁMARA)
        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // 3. CONFIGURAR CARRUSEL DINÁMICO
        viewPagerCarrusel = findViewById(R.id.viewPagerCarrusel)
        val listaImagenes = listOf(
            R.drawable.vestido,
            R.drawable.sonidos,
            R.drawable.iphone_17,
            R.drawable.masajes
        )

        viewPagerCarrusel.adapter = CarruselAdapter(listaImagenes) { position ->
            val intent = Intent(this, DetalleProductoActivity::class.java).apply {
                putExtra("nombre", "Promoción Especial #${position + 1}")
                putExtra("precio", "Ver detalles")
            }
            startActivity(intent)
        }

        sliderRunnable = Runnable {
            if (listaImagenes.isNotEmpty()) {
                var itemActual = viewPagerCarrusel.currentItem + 1
                if (itemActual >= listaImagenes.size) itemActual = 0
                viewPagerCarrusel.currentItem = itemActual
                sliderHandler.postDelayed(sliderRunnable, 4000)
            }
        }

        // 4. CONFIGURAR RECYCLERVIEW VERTICAL
        rvProductosHome = findViewById(R.id.rvProductosHome)
        rvProductosHome.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Llamado al método de carga controlado
        cargarProductosDesdeBD()

        // 5. ENLAZAR COMPONENTES DE NAVEGACIÓN Y CATEGORÍAS
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)
        cardTecnologia = findViewById(R.id.cardTecnologia)
        cardRopa = findViewById(R.id.cardRopa)
        cardSpa = findViewById(R.id.cardSpa)

        btnMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }
        txtInicio.setOnClickListener { drawerLayout.closeDrawer(GravityCompat.START) }

        txtMarketplace.setOnClickListener {
            startActivity(Intent(this, MarketplaceActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtProductos.setOnClickListener {
            startActivity(Intent(this, MisProductosActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtCerrarSesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        cardTecnologia.setOnClickListener { abrirMarketplaceConFiltro("tecnologia") }
        cardRopa.setOnClickListener { abrirMarketplaceConFiltro("ropa") }
        cardSpa.setOnClickListener { abrirMarketplaceConFiltro("spa") }

        // 6. POPUP MENÚ PERFIL UNIFICADO MATERIAL 3
        imgPerfil.setOnClickListener {
            val contextWrapper = ContextThemeWrapper(this, R.style.PopupMenuStyle)
            val popupMenu = PopupMenu(contextWrapper, imgPerfil)

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
    }

    /**
     * Simulación de respuesta de API reducida a 2 elementos requeridos.
     * Mantiene intacta la estructura del modelo global "Producto".
     */
    private fun cargarProductosDesdeBD() {
        listaDeProductos.clear()

        // Mantenemos solo 2 productos con la estructura limpia del objeto listo para JSON/BD
        listaDeProductos.add(Producto("1", "iPhone 17 Pro", "$4.500.000", "tecnologia", R.drawable.iphone_17))
        listaDeProductos.add(Producto("2", "Vestido Elegante", "$120.000", "ropa", R.drawable.vestido))

        // Al presionar cualquiera, el ProductosHomeAdapter gestionará la navegación automática hacia el Detalle
        rvProductosHome.adapter = ProductosHomeAdapter(listaDeProductos)
    }

    private fun abrirMarketplaceConFiltro(categoria: String) {
        val intent = Intent(this, MarketplaceActivity::class.java).apply {
            putExtra("categoria", categoria)
        }
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, 4000)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
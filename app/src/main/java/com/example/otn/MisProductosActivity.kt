package com.example.otn

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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

class MisProductosActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView
    private lateinit var rvMisProductos: RecyclerView

    // MENU LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

    private var misProductosList = ArrayList<Producto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_productos)

        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)
        rvMisProductos = findViewById(R.id.rvMisProductos)

        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        // PARCHE PUNCH HOLE
        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // CONFIGURAR RECYCLERVIEW REUTILIZANDO MODELO REAL
        rvMisProductos.layoutManager = LinearLayoutManager(this)

        // Simulador de tus productos subidos (Backend ready)
        misProductosList.clear()
        misProductosList.add(Producto("1", "iPhone 17 Pro", "$4.500.000", "tecnologia", R.drawable.iphone_17))
        misProductosList.add(Producto("3", "AirPods Max", "$1.250.000", "tecnologia", R.drawable.airpods))
        misProductosList.add(Producto("4", "Vestido Elegante", "$220.000", "ropa", R.drawable.vestido))

        rvMisProductos.adapter = MisProductosAdapter(misProductosList)

        btnMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        // POPUP PERFIL OSCURO UNIFICADO
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

        // EVENTOS MENÚ LATERAL
        txtInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
        txtMarketplace.setOnClickListener {
            startActivity(Intent(this, MarketplaceActivity::class.java))
            finish()
        }
        txtProductos.setOnClickListener { drawerLayout.closeDrawer(GravityCompat.START) }
        txtCerrarSesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }

    // ADAPTER REFACTORIZADO
    inner class MisProductosAdapter(private val productos: List<Producto>) :
        RecyclerView.Adapter<MisProductosAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val txtNombre: TextView = view.findViewById(R.id.txtItemNombre)
            val txtPrecio: TextView = view.findViewById(R.id.txtItemPrecio)
            val txtUbicacion: TextView = view.findViewById(R.id.txtItemUbicacion)
            val imgProducto: ImageView = view.findViewById(R.id.imgItemProducto)
            val btnEditar: Button = view.findViewById(R.id.btnItemEditar)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val v = LayoutInflater.from(parent.context).inflate(R.layout.item_mis_productos, parent, false)
            return ViewHolder(v)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val prod = productos[position]
            holder.txtNombre.text = prod.nombre
            holder.txtPrecio.text = prod.precio
            holder.txtUbicacion.text = "Disponible" // Cambiado por un estado dinámico por defecto

            holder.imgProducto.setImageResource(prod.imagen)

            holder.btnEditar.setOnClickListener {
                val intent = Intent(this@MisProductosActivity, EditarProductoActivity::class.java).apply {
                    putExtra("EXTRA_ID", prod.id)
                    putExtra("EXTRA_NOMBRE", prod.nombre)
                    putExtra("EXTRA_PRECIO", prod.precio)
                    putExtra("EXTRA_CATEGORIA", prod.categoria)
                    putExtra("EXTRA_IMAGEN", prod.imagen)
                }
                this@MisProductosActivity.startActivity(intent)
            }
        }

        override fun getItemCount() = productos.size
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
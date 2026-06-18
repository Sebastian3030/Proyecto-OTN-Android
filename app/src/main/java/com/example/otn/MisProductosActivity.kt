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
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

    // 1. ESTRUCTURA DE DATOS ACTUALIZADA CON IMAGEN
    data class ProductoTemporal(val nombre: String, val precio: String, val ubicacion: String, val imagenRes: Int)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mis_productos)

        // 2. INICIALIZAR VISTAS
        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)
        rvMisProductos = findViewById(R.id.rvMisProductos)

        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        // 3. EVITAR QUE EL PUNCH HOLE DE LA CÁMARA TAPARA LA TOPBAR
        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // 4. CONFIGURAR RECYCLERVIEW CON TUS PRODUCTOS E IMÁGENES REALES
        rvMisProductos.layoutManager = LinearLayoutManager(this)

        val listaEjemplo = listOf(
            ProductoTemporal("iPhone 17 Pro", "$ 4.500.000", "Bogotá, Colombia", R.drawable.iphone_17),
            ProductoTemporal("airpods", "$ 1.250.000", "Medellín, Colombia", R.drawable.airpods),
            ProductoTemporal("Vestido Inteligente", "$ 580.000", "Cali, Colombia", R.drawable.vestido)
        )

        rvMisProductos.adapter = MisProductosAdapter(listaEjemplo)

        // 5. ABRIR MENÚ LATERAL
        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        // 6. POPUP MENÚ PERFIL CON EL CONTEXTO OSCURO CORREGIDO
        imgPerfil.setOnClickListener {
            val contextoOscuro = androidx.appcompat.view.ContextThemeWrapper(this, R.style.PopupMenuStyle)
            val popupMenu = PopupMenu(contextoOscuro, imgPerfil)

            popupMenu.menu.add(0, 0, 0, "👤 Sebastian")
            popupMenu.menu.add(0, 1, 1, "✏️ Editar perfil")
            popupMenu.menu.add(0, 2, 2, "🏢 Vincular negocio / Publicar")
            popupMenu.menu.add(0, 3, 3, "📦 Mis Productos")
            popupMenu.menu.add(0, 4, 4, "❤️ Favoritos")
            popupMenu.menu.add(0, 5, 5, "🗒️ Historial de citas")
            popupMenu.menu.add(0, 6, 6, "📅 Agendar citas")
            popupMenu.menu.add(0, 7, 7, "📖 Historial de compras")
            popupMenu.menu.add(0, 8, 8, "💷 Historial de pagos")
            popupMenu.menu.add(0, 9, 9, "🚪 Cerrar sesión")

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
                    3 -> { /* Ya estamos aquí */ }
                    4 -> startActivity(Intent(this, FavoritosActivity::class.java))
                    5 -> startActivity(Intent(this, HistorialCitasActivity::class.java))
                    6 -> startActivity(Intent(this, AgendarCitaActivity::class.java))
                    7 -> startActivity(Intent(this, HistorialComprasActivity::class.java))
                    8 -> startActivity(Intent(this, HistorialPagosActivity::class.java))
                    9 -> {
                        startActivity(Intent(this, MainActivity::class.java))
                        finishAffinity()
                    }
                }
                true
            }
            popupMenu.show()
        }

        // 7. EVENTOS DE NAVEGACIÓN MENÚ LATERAL
        txtInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtMarketplace.setOnClickListener {
            startActivity(Intent(this, MarketplaceActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtProductos.setOnClickListener {
            startActivity(Intent(this, PublicarActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtCerrarSesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }

    // ADAPTER INTERNO CORREGIDO CON ENTREGA DE IMÁGENES REALES
    inner class MisProductosAdapter(private val productos: List<ProductoTemporal>) :
        RecyclerView.Adapter<MisProductosAdapter.ViewHolder>() {

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val txtNombre: TextView = view.findViewById(R.id.txtItemNombre)
            val txtPrecio: TextView = view.findViewById(R.id.txtItemPrecio)
            val txtUbicacion: TextView = view.findViewById(R.id.txtItemUbicacion)
            val imgProducto: ImageView = view.findViewById(R.id.imgItemProducto) // Recuadro de la foto
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
            holder.txtUbicacion.text = prod.ubicacion

            // Reemplaza el avatar por la imagen real asignada en la lista
            holder.imgProducto.setImageResource(prod.imagenRes)

            holder.btnEditar.setOnClickListener {
                Toast.makeText(this@MisProductosActivity, "Abriendo edición para: ${prod.nombre}", Toast.LENGTH_SHORT).show()
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
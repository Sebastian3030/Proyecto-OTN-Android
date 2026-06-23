package com.example.otn

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

class EditarProductoActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    private lateinit var imgPrevisualizacion: ImageView
    private lateinit var btnSeleccionarImagen: LinearLayout
    private lateinit var etNombreProducto: EditText
    private lateinit var etPrecioProducto: EditText
    private lateinit var etUbicacionProducto: EditText
    private lateinit var spinnerCategoria: Spinner
    private lateinit var etDescripcionProducto: EditText
    private lateinit var btnGuardarCambios: Button
    private lateinit var btnEliminarProducto: Button

    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

    private val registrarGaleria = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        uri?.let {
            imgPrevisualizacion.setImageURI(it)
            imgPrevisualizacion.scaleType = ImageView.ScaleType.CENTER_CROP
            Toast.makeText(this, "Imagen actualizada", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar_producto)

        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        imgPrevisualizacion = findViewById(R.id.imgPrevisualizacion)
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        etNombreProducto = findViewById(R.id.etNombreProducto)
        etPrecioProducto = findViewById(R.id.etPrecioProducto)
        etUbicacionProducto = findViewById(R.id.etUbicacionProducto)
        spinnerCategoria = findViewById(R.id.spinnerCategoria)
        etDescripcionProducto = findViewById(R.id.etDescripcionProducto)
        btnGuardarCambios = findViewById(R.id.btnGuardarCambios)
        btnEliminarProducto = findViewById(R.id.btnEliminarProducto)

        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        val categorias = listOf("Tecnología", "Ropa", "Spa", "Otros")
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, categorias)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adapterSpinner

        val nombreDestinatario = intent.getStringExtra("EXTRA_NOMBRE") ?: ""
        val precioDestinatario = intent.getStringExtra("EXTRA_PRECIO") ?: ""
        val ubicacionDestinatario = intent.getStringExtra("EXTRA_UBICACION") ?: ""
        val imagenRecurso = intent.getIntExtra("EXTRA_IMAGEN", R.drawable.perfil)

        etNombreProducto.setText(nombreDestinatario)
        etPrecioProducto.setText(precioDestinatario.replace("$", "").replace(".", "").trim())
        etUbicacionProducto.setText(ubicacionDestinatario)
        imgPrevisualizacion.setImageResource(imagenRecurso)

        etDescripcionProducto.setText("Este es un producto publicado originalmente en la app OTN. Se encuentra en excelente estado.")

        btnSeleccionarImagen.setOnClickListener {
            registrarGaleria.launch("image/*")
        }

        btnGuardarCambios.setOnClickListener {
            val nuevoNombre = etNombreProducto.text.toString().trim()
            val nuevoPrecio = etPrecioProducto.text.toString().trim()

            if (nuevoNombre.isEmpty() || nuevoPrecio.isEmpty()) {
                Toast.makeText(this, "Por favor llena los campos obligatorios", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "¡Cambios guardados con éxito en OTN!", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

        btnEliminarProducto.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("🚨 ¿Eliminar publicación?")
                .setMessage("Esta acción no se puede deshacer. Tu producto desaparecerá del marketplace de OTN.")
                .setPositiveButton("Sí, Eliminar") { _, _ ->
                    Toast.makeText(this, "Publicación eliminada", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .setNegativeButton("Cancelar", null)
                .show()
        }

        btnMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

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

        // POPUP MENÚ PERFIL (Unificado con bucle for de soporte para estilos heredados)
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

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}
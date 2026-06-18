package com.example.otn

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewGroup
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
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

class PublicarActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView
    private lateinit var btnPublicar: Button

    // ELEMENTOS DEL FORMULARIO
    private lateinit var btnSeleccionarImagen: LinearLayout
    private lateinit var imgPrevisualizacion: ImageView
    private lateinit var etNombre: EditText
    private lateinit var spinnerCategoria: Spinner
    private lateinit var etPrecio: EditText
    private lateinit var etContacto: EditText
    private lateinit var etUbicacion: EditText // 👈 AGREGADO: Variable de Ubicación
    private lateinit var etDescripcion: EditText

    private var imagenSeleccionadaUri: Uri? = null

    // MENU LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

    // REGISTRO DE GALERÍA (Forma moderna en Android)
    private val abrirGaleriaLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            imagenSeleccionadaUri = uri
            imgPrevisualizacion.setImageURI(uri)
            imgPrevisualizacion.scaleType = ImageView.ScaleType.CENTER_CROP
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicar)

        // 1. INICIALIZAR VISTAS DEL FORMULARIO
        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        imgPrevisualizacion = findViewById(R.id.imgPrevisualizacion)
        etNombre = findViewById(R.id.etNombre)
        spinnerCategoria = findViewById(R.id.spinnerCategoria)
        etPrecio = findViewById(R.id.etPrecio)
        etContacto = findViewById(R.id.etContacto)
        etUbicacion = findViewById(R.id.etUbicacion) // 👈 AGREGADO: Vinculación con el XML
        etDescripcion = findViewById(R.id.etDescripcion)
        btnPublicar = findViewById(R.id.btnPublicar)

        // 2. CONFIGURAR EL SPINNER DE CATEGORÍAS (Texto blanco para fondo oscuro)
        val opcionesCategorias = arrayOf("Selecciona una categoría", "Tecnología", "Ropa", "SPA")
        val adapterSpinner = object : ArrayAdapter<String>(
            this,
            android.R.layout.simple_spinner_item,
            opcionesCategorias
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent) as TextView
                view.setTextColor(Color.WHITE)
                return view
            }

            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                view.setTextColor(Color.WHITE)
                view.setBackgroundColor(Color.parseColor("#0E1A2B"))
                return view
            }
        }
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategoria.adapter = adapterSpinner

        // 3. EVENTO PARA CARGAR IMAGEN DESDE LA GALERÍA
        btnSeleccionarImagen.setOnClickListener {
            abrirGaleriaLauncher.launch("image/*")
        }

        // 4. EVITAR QUE EL PUNCH HOLE DE LA CÁMARA TAPARA LA TOPBAR
        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // 5. ENLAZAR COMPONENTES DEL MENÚ LATERAL
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        btnMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

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

        // 6. VALIDACIONES Y BOTÓN PUBLICAR ESTRICTO
        btnPublicar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val precio = etPrecio.text.toString().trim()
            val contacto = etContacto.text.toString().trim()
            val ubicacion = etUbicacion.text.toString().trim() // 👈 AGREGADO: Obtención de texto limpio
            val descripcion = etDescripcion.text.toString().trim()
            val categoriaSeleccionada = spinnerCategoria.selectedItemPosition

            // Validar que suban una foto
            if (imagenSeleccionadaUri == null) {
                Toast.makeText(this, "Por favor, agrega una imagen para tu producto", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Validar campos vacíos estándar
            if (nombre.isEmpty()) {
                etNombre.error = "El nombre del producto es obligatorio"
                etNombre.requestFocus()
                return@setOnClickListener
            }

            if (categoriaSeleccionada == 0) {
                Toast.makeText(this, "Por favor, selecciona una categoría válida", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (precio.isEmpty()) {
                etPrecio.error = "Debes asignar un precio"
                etPrecio.requestFocus()
                return@setOnClickListener
            }

            if (contacto.isEmpty()) {
                etContacto.error = "El número de contacto es obligatorio"
                etContacto.requestFocus()
                return@setOnClickListener
            }

            if (contacto.length < 7) {
                etContacto.error = "Ingresa un número de contacto válido (mínimo 7 dígitos)"
                etContacto.requestFocus()
                return@setOnClickListener
            }

            // 👈 AGREGADO: Validación estricta para el campo Ubicación
            if (ubicacion.isEmpty()) {
                etUbicacion.error = "La ubicación es obligatoria para publicar"
                etUbicacion.requestFocus()
                return@setOnClickListener
            }

            if (descripcion.isEmpty()) {
                etDescripcion.error = "Agrega una breve descripción del producto"
                etDescripcion.requestFocus()
                return@setOnClickListener
            }

            // Si pasa todos los filtros, publica con éxito
            Toast.makeText(this, "¡Producto '$nombre' en '$ubicacion' publicado correctamente!", Toast.LENGTH_LONG).show()

            // Redirigir de vuelta al Marketplace tras publicar
            val intent = Intent(this, MarketplaceActivity::class.java)
            startActivity(intent)
            finish()
        }

        // NAVEGACIÓN DEL MENÚ LATERAL
        txtInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtMarketplace.setOnClickListener {
            startActivity(Intent(this, MarketplaceActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtProductos.setOnClickListener {
            Toast.makeText(this, "Ya estás en Publicar", Toast.LENGTH_SHORT).show()
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        txtCerrarSesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
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
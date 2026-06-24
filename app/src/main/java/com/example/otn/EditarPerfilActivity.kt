package com.example.otn

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout

class EditarPerfilActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView
    private lateinit var btnGuardar: Button
    private lateinit var menuLateral: LinearLayout // 🟢 Variable agregada para control táctil

    private lateinit var btnSeleccionarImagen: LinearLayout
    private lateinit var imgPrevisualizacion: ImageView
    private var imagenSeleccionadaUri: Uri? = null

    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etTelefono: EditText
    private lateinit var etCiudad: EditText
    private lateinit var etDescripcion: EditText

    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

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
        setContentView(R.layout.activity_editar_perfil)

        // Inicializar componentes base
        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)
        btnGuardar = findViewById(R.id.btnGuardar)
        menuLateral = findViewById(R.id.menuLateral) // 🟢 Inicializado

        // ESCUDO DE CLICKS DEFENSIVO PARA EL MENÚ LATERAL
        menuLateral.setOnClickListener { }

        btnSeleccionarImagen = findViewById(R.id.btnSeleccionarImagen)
        imgPrevisualizacion = findViewById(R.id.imgPrevisualizacion)

        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etTelefono = findViewById(R.id.etTelefono)
        etCiudad = findViewById(R.id.etCiudad)
        etDescripcion = findViewById(R.id.etDescripcion)

        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        btnSeleccionarImagen.setOnClickListener {
            abrirGaleriaLauncher.launch("image/*")
        }

        btnMenu.setOnClickListener {
            drawerLayout.openDrawer(GravityCompat.START)
        }

        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // POPUP MENÚ PERFIL (Tema Oscuro Material 3 Unificado)
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
                    0 -> {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        finish()
                    }
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

        // ACCIÓN GUARDAR CAMBIOS
        btnGuardar.setOnClickListener {
            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val telefono = etTelefono.text.toString().trim()
            val ciudad = etCiudad.text.toString().trim()
            val descripcion = etDescripcion.text.toString().trim()

            if (nombre.isEmpty()) {
                etNombre.error = "El nombre es obligatorio"
                etNombre.requestFocus()
                return@setOnClickListener
            }
            if (correo.isEmpty()) {
                etCorreo.error = "El correo es obligatorio"
                etCorreo.requestFocus()
                return@setOnClickListener
            }
            if (telefono.isEmpty()) {
                etTelefono.error = "El teléfono es obligatorio"
                etTelefono.requestFocus()
                return@setOnClickListener
            }
            if (telefono.length < 7) {
                etTelefono.error = "Ingresa un teléfono válido"
                etTelefono.requestFocus()
                return@setOnClickListener
            }
            if (ciudad.isEmpty()) {
                etCiudad.error = "La ciudad es obligatoria"
                etCiudad.requestFocus()
                return@setOnClickListener
            }

            // Aquí irá el mapeo del objeto / Multipart para subir la imagen a Firebase Storage o AWS
            Toast.makeText(this, "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show()
            finish()
        }

        // NAVEGACIÓN CAJÓN LATERAL HOMOLOGADA
        txtInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
            finish() // 🟢 Parche de memoria aplicado
        }

        txtMarketplace.setOnClickListener {
            startActivity(Intent(this, MarketplaceActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
            finish() // 🟢 Parche de memoria aplicado
        }

        txtProductos.setOnClickListener {
            // 🟢 CORRECCIÓN: Apunta a la lista de mis productos para mantener consistencia con los demás flujos
            startActivity(Intent(this, MisProductosActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
            finish()
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
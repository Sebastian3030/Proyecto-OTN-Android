package com.example.otn

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ContextThemeWrapper
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import java.util.Calendar

class AgendarCitaActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    private lateinit var spinnerServicio: Spinner
    private lateinit var spinnerHora: Spinner
    private lateinit var txtFecha: TextView
    private lateinit var btnAgendar: Button

    private lateinit var edtNombre: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var edtTelefono: EditText

    // MENÚ LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_cita)

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        val topBar = findViewById<LinearLayout>(R.id.topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBar) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // REFERENCIAS FORMULARIO
        spinnerServicio = findViewById(R.id.spinnerServicio)
        spinnerHora = findViewById(R.id.spinnerHora)
        txtFecha = findViewById(R.id.txtFecha)
        btnAgendar = findViewById(R.id.btnAgendar)
        edtNombre = findViewById(R.id.edtNombre)
        edtCorreo = findViewById(R.id.edtCorreo)
        edtTelefono = findViewById(R.id.edtTelefono)

        // REFERENCIAS MENÚ LATERAL
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        btnMenu.setOnClickListener { drawerLayout.openDrawer(GravityCompat.START) }

        // NAVEGACIÓN LATERAL HOMOLOGADA
        txtInicio.setOnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        txtMarketplace.setOnClickListener {
            startActivity(Intent(this, MarketplaceActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        txtProductos.setOnClickListener {
            // 💡 CORRECCIÓN: Apunta correctamente al Historial de Publicaciones del usuario
            startActivity(Intent(this, MisProductosActivity::class.java))
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        txtCerrarSesion.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }

        // POPUP MENÚ PERFIL (Mantenemos tu lógica impecable del Home)
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
                    3 -> startActivity(Intent(this, FavoritosActivity::class.java))
                    4 -> startActivity(Intent(this, HistorialCitasActivity::class.java))
                    5 -> { /* Ya estamos aquí */ }
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

        // CONFIGURACIÓN DE SELECTORES (SPINNERS)
        val servicios = arrayOf("Seleccionar servicio", "Tecnología", "Ropa", "SPA")
        val adapterServicios = ArrayAdapter(this, R.layout.item_spinner, servicios)
        adapterServicios.setDropDownViewResource(R.layout.item_spinner_dropdown)
        spinnerServicio.adapter = adapterServicios

        val horarios = arrayOf(
            "Seleccionar hora", "08:00 AM", "09:00 AM", "10:00 AM",
            "11:00 AM", "02:00 PM", "03:00 PM", "04:00 PM", "05:00 PM"
        )
        val adapterHora = ArrayAdapter(this, R.layout.item_spinner, horarios)
        adapterHora.setDropDownViewResource(R.layout.item_spinner_dropdown)
        spinnerHora.adapter = adapterHora

        txtFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val datePicker = DatePickerDialog(this, { _, y, m, d ->
                txtFecha.text = "$d/${m + 1}/$y"
            }, calendario.get(Calendar.YEAR), calendario.get(Calendar.MONTH), calendario.get(Calendar.DAY_OF_MONTH))
            datePicker.show()
        }

        btnAgendar.setOnClickListener {
            val servicioSeleccionado = spinnerServicio.selectedItem.toString()
            val horaSeleccionada = spinnerHora.selectedItem.toString()
            val fechaSeleccionada = txtFecha.text.toString().trim()
            val nombre = edtNombre.text.toString().trim()
            val correo = edtCorreo.text.toString().trim()
            val telefono = edtTelefono.text.toString().trim()

            if (spinnerServicio.selectedItemPosition == 0) {
                Toast.makeText(this, "Seleccione un servicio válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (fechaSeleccionada == "Seleccionar fecha" || fechaSeleccionada.isEmpty()) {
                Toast.makeText(this, "Seleccione una fecha para su cita", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (spinnerHora.selectedItemPosition == 0) {
                Toast.makeText(this, "Seleccione un horario disponible", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (nombre.isEmpty()) {
                edtNombre.error = "El nombre es obligatorio"; return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                edtCorreo.error = "Ingrese un correo electrónico válido"; return@setOnClickListener
            }
            if (telefono.length < 7 || telefono.length > 15) {
                edtTelefono.error = "Ingrese un teléfono válido (7-15 dígitos)"; return@setOnClickListener
            }

            Toast.makeText(this, "Guardando cita en el sistema...", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, CitaConfirmadaActivity::class.java).apply {
                putExtra(CitaConfirmadaActivity.EXTRA_NOMBRE, nombre)
                putExtra(CitaConfirmadaActivity.EXTRA_CORREO, correo)
                putExtra(CitaConfirmadaActivity.EXTRA_TELEFONO, telefono)
                putExtra(CitaConfirmadaActivity.EXTRA_SERVICIO, servicioSeleccionado)
                putExtra(CitaConfirmadaActivity.EXTRA_FECHA, fechaSeleccionada)
                putExtra(CitaConfirmadaActivity.EXTRA_HORA, horaSeleccionada)
            }
            startActivity(intent)
            finish()
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
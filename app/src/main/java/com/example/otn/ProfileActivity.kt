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
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class ProfileActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    // MENU LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView

    // OPCIONES
    private lateinit var layoutFavoritos: LinearLayout
    private lateinit var layoutCitas: LinearLayout
    private lateinit var layoutPublicaciones: LinearLayout

    // BOTONES
    private lateinit var btnEditarPerfil: Button
    private lateinit var btnCerrarSesion: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        // REFERENCIAS
        drawerLayout = findViewById(R.id.drawerLayout)

        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        // MENU LATERAL
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)

        // OPCIONES
        layoutFavoritos = findViewById(R.id.layoutFavoritos)
        layoutCitas = findViewById(R.id.layoutCitas)
        layoutPublicaciones = findViewById(R.id.layoutPublicaciones)

        // BOTONES
        btnEditarPerfil = findViewById(R.id.btnEditarPerfil)
        btnCerrarSesion = findViewById(R.id.btnCerrarSesion)

        // MENU LATERAL
        btnMenu.setOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)

        }

        // PERFIL MENU
        imgPerfil.setOnClickListener {

            val popupMenu = PopupMenu(
                this,
                imgPerfil,
                0,
                0,
                R.style.PopupMenuStyle
            )

            popupMenu.menu.add("👤 Sebastian")
            popupMenu.menu.add("✏️ Editar perfil")
            popupMenu.menu.add("🏢 Vincular negocio")
            popupMenu.menu.add("❤️ Favoritos")
            popupMenu.menu.add("📅 Agendar citas")
            popupMenu.menu.add("🚪 Cerrar sesión")

            for (i in 0 until popupMenu.menu.size()) {

                val menuItem = popupMenu.menu.getItem(i)

                val spanString = SpannableString(menuItem.title)

                spanString.setSpan(
                    ForegroundColorSpan(Color.WHITE),
                    0,
                    spanString.length,
                    0
                )

                menuItem.title = spanString

            }

            popupMenu.setOnMenuItemClickListener {

                when (it.title.toString()) {

                    "👤 Sebastian" -> {

                        Toast.makeText(
                            this,
                            "Ya estás en tu perfil",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    "✏️ Editar perfil" -> {

                        Toast.makeText(
                            this,
                            "Editar perfil próximamente",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    "🏢 Vincular negocio" -> {

                        val intent = Intent(
                            this,
                            PublicarActivity::class.java
                        )

                        startActivity(intent)

                    }

                    "❤️ Favoritos" -> {

                        Toast.makeText(
                            this,
                            "Favoritos próximamente",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    "📅 Agendar citas" -> {

                        Toast.makeText(
                            this,
                            "Agendar citas próximamente",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    "🚪 Cerrar sesión" -> {

                        val intent = Intent(
                            this,
                            MainActivity::class.java
                        )

                        startActivity(intent)

                        finish()

                    }

                }

                true

            }

            popupMenu.show()

        }

        // MENU LATERAL INICIO
        txtInicio.setOnClickListener {

            val intent = Intent(
                this,
                HomeActivity::class.java
            )

            startActivity(intent)

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        // MENU LATERAL MARKETPLACE
        txtMarketplace.setOnClickListener {

            val intent = Intent(
                this,
                MarketplaceActivity::class.java
            )

            startActivity(intent)

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        // MENU LATERAL PRODUCTOS
        txtProductos.setOnClickListener {

            val intent = Intent(
                this,
                PublicarActivity::class.java
            )

            startActivity(intent)

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        // EDITAR PERFIL
        btnEditarPerfil.setOnClickListener {

            Toast.makeText(
                this,
                "Editar perfil próximamente",
                Toast.LENGTH_SHORT
            ).show()

        }

        // FAVORITOS
        layoutFavoritos.setOnClickListener {

            Toast.makeText(
                this,
                "Favoritos próximamente",
                Toast.LENGTH_SHORT
            ).show()

        }

        // CITAS
        layoutCitas.setOnClickListener {

            Toast.makeText(
                this,
                "Mis citas próximamente",
                Toast.LENGTH_SHORT
            ).show()

        }

        // PUBLICACIONES
        layoutPublicaciones.setOnClickListener {

            val intent = Intent(
                this,
                MarketplaceActivity::class.java
            )

            startActivity(intent)

        }

        // CERRAR SESION
        btnCerrarSesion.setOnClickListener {

            val intent = Intent(
                this,
                MainActivity::class.java
            )

            startActivity(intent)

            finish()

        }

    }

    // BOTON ATRAS
    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START)

        } else {

            super.onBackPressed()

        }

    }

}
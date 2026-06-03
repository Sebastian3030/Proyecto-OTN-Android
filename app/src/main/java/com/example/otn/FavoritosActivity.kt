package com.example.otn

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class FavoritosActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    // MENU
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favoritos)

        // REFERENCIAS
        drawerLayout = findViewById(R.id.drawerLayout)

        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        // ABRIR MENU
        btnMenu.setOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)

        }

        // MENU PERFIL
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

            // TEXTO BLANCO
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

                        val intent = Intent(
                            this,
                            ProfileActivity::class.java
                        )

                        startActivity(intent)

                    }

                    "✏️ Editar perfil" -> {

                        val intent = Intent(
                            this,
                            EditarPerfilActivity::class.java
                        )

                        startActivity(intent)

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
                            "Ya estas en Tus favoritos",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    "📅 Agendar citas" -> {
                        val intent = Intent(
                            this,
                            AgendarCitaActivity::class.java
                        )
                        startActivity(intent)

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

        // INICIO
        txtInicio.setOnClickListener {

            val intent = Intent(
                this,
                HomeActivity::class.java
            )

            startActivity(intent)

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        // MARKETPLACE
        txtMarketplace.setOnClickListener {

            val intent = Intent(
                this,
                MarketplaceActivity::class.java
            )

            startActivity(intent)

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        // PUBLICAR
        txtProductos.setOnClickListener {

            val intent = Intent(
                this,
                PublicarActivity::class.java
            )

            startActivity(intent)

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        // CERRAR SESION
        txtCerrarSesion.setOnClickListener {

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
package com.example.otn

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class DetalleProductoActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    // TOPBAR
    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    // BOTONES
    private lateinit var btnContactar: Button
    private lateinit var btnComprar: Button

    // MENU LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtPublicar: TextView
    private lateinit var txtCerrarSesion: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle_producto)

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)

        // TOPBAR
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        // BOTONES
        btnContactar = findViewById(R.id.btnContactar)
        btnComprar = findViewById(R.id.btnComprar)

        // MENU LATERAL
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtPublicar = findViewById(R.id.txtPublicar)
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

                    // PERFIL
                    "👤 Sebastian" -> {

                        val intent = Intent(
                            this,
                            ProfileActivity::class.java
                        )

                        startActivity(intent)

                    }

                    // EDITAR PERFIL
                    "✏️ Editar perfil" -> {

                        val intent = Intent(
                            this,
                            EditarPerfilActivity::class.java
                        )

                        startActivity(intent)

                    }

                    // PUBLICAR
                    "🏢 Vincular negocio" -> {

                        val intent = Intent(
                            this,
                            PublicarActivity::class.java
                        )

                        startActivity(intent)

                    }

                    // FAVORITOS
                    "❤️ Favoritos" -> {

                        val intent = Intent(
                            this,
                            FavoritosActivity::class.java
                        )

                        startActivity(intent)

                    }

                    // CITAS
                    "📅 Agendar citas" -> {

                        Toast.makeText(
                            this,
                            "Próximamente",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    // CERRAR SESION
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

        // MENU LATERAL

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
        txtPublicar.setOnClickListener {

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

        // BOTON CONTACTAR
        btnContactar.setOnClickListener {

            Toast.makeText(
                this,
                "Chat próximamente",
                Toast.LENGTH_SHORT
            ).show()

        }

        // BOTON COMPRAR
        btnComprar.setOnClickListener {

            Toast.makeText(
                this,
                "Compras próximamente",
                Toast.LENGTH_SHORT
            ).show()

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
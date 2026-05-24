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

class PublicarActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    private lateinit var btnPublicar: Button

    // MENU LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publicar)

        // REFERENCIAS
        drawerLayout = findViewById(R.id.drawerLayout)

        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        btnPublicar = findViewById(R.id.btnPublicar)

        // MENU LATERAL
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)

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

            // COLOR BLANCO
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

                        Toast.makeText(
                            this,
                            "Editar perfil próximamente",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    // VINCULAR NEGOCIO
                    "🏢 Vincular negocio" -> {

                        Toast.makeText(
                            this,
                            "Ya estás en Publicar",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    // FAVORITOS
                    "❤️ Favoritos" -> {

                        Toast.makeText(
                            this,
                            "Favoritos próximamente",
                            Toast.LENGTH_SHORT
                        ).show()

                    }

                    // CITAS
                    "📅 Agendar citas" -> {

                        Toast.makeText(
                            this,
                            "Agendar citas próximamente",
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

        // BOTON PUBLICAR
        btnPublicar.setOnClickListener {

            Toast.makeText(
                this,
                "Producto publicado correctamente",
                Toast.LENGTH_SHORT
            ).show()

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

            Toast.makeText(
                this,
                "Ya estás en Publicar",
                Toast.LENGTH_SHORT
            ).show()

            drawerLayout.closeDrawer(GravityCompat.START)

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
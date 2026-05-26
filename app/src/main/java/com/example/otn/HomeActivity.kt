package com.example.otn

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    // MENU LATERAL
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

    // CATEGORIAS
    private lateinit var cardTecnologia: LinearLayout
    private lateinit var cardRopa: LinearLayout
    private lateinit var cardSpa: LinearLayout

    // PRODUCTO
    private lateinit var cardIphone: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)

        // TOPBAR
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        // MENU LATERAL
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        // CATEGORIAS
        cardTecnologia = findViewById(R.id.cardTecnologia)
        cardRopa = findViewById(R.id.cardRopa)
        cardSpa = findViewById(R.id.cardSpa)

        // PRODUCTO
        cardIphone = findViewById(R.id.cardIphone)

        // =========================
        // MENU LATERAL
        // =========================

        btnMenu.setOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)

        }

        // =========================
        // MENU PERFIL
        // =========================

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

            // COLOR TEXTO
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
                            "Próximamente disponible",
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

        // =========================
        // CATEGORIAS
        // =========================

        // TECNOLOGIA
        cardTecnologia.setOnClickListener {

            val intent = Intent(
                this,
                MarketplaceActivity::class.java
            )

            intent.putExtra(
                "categoria",
                "tecnologia"
            )

            startActivity(intent)

        }

        // ROPA
        cardRopa.setOnClickListener {

            val intent = Intent(
                this,
                MarketplaceActivity::class.java
            )

            intent.putExtra(
                "categoria",
                "ropa"
            )

            startActivity(intent)

        }

        // SPA
        cardSpa.setOnClickListener {

            val intent = Intent(
                this,
                MarketplaceActivity::class.java
            )

            intent.putExtra(
                "categoria",
                "spa"
            )

            startActivity(intent)

        }

        // =========================
        // PRODUCTO IPHONE
        // =========================

        cardIphone.setOnClickListener {

            val intent = Intent(
                this,
                DetalleProductoActivity::class.java
            )

            startActivity(intent)

        }

        // =========================
        // MENU LATERAL
        // =========================

        // INICIO
        txtInicio.setOnClickListener {

            Toast.makeText(
                this,
                "Ya estás en Inicio",
                Toast.LENGTH_SHORT
            ).show()

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

        // PRODUCTOS
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

    // =========================
    // BOTON ATRAS
    // =========================

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START)

        } else {

            super.onBackPressed()

        }

    }

}
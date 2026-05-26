package com.example.otn

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class MarketplaceActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var btnMenu: ImageView
    private lateinit var imgPerfil: ImageView

    // MENU
    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtPublicar: TextView
    private lateinit var txtCerrarSesion: TextView

    // BOTONES
    private lateinit var btnTodos: Button
    private lateinit var btnTecnologia: Button
    private lateinit var btnRopa: Button
    private lateinit var btnSpa: Button

    // CARDS
    private lateinit var cardTec1: View
    private lateinit var cardTec2: View

    private lateinit var cardRopa1: View
    private lateinit var cardRopa2: View

    private lateinit var cardSpa1: View
    private lateinit var cardSpa2: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_marketplace)

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)

        // TOPBAR
        btnMenu = findViewById(R.id.btnMenu)
        imgPerfil = findViewById(R.id.imgPerfil)

        // MENU
        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtPublicar = findViewById(R.id.txtPublicar)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        // BOTONES
        btnTodos = findViewById(R.id.btnTodos)
        btnTecnologia = findViewById(R.id.btnTecnologia)
        btnRopa = findViewById(R.id.btnRopa)
        btnSpa = findViewById(R.id.btnSpa)

        // CARDS
        cardTec1 = findViewById(R.id.cardTec1)
        cardTec2 = findViewById(R.id.cardTec2)

        cardRopa1 = findViewById(R.id.cardRopa1)
        cardRopa2 = findViewById(R.id.cardRopa2)

        cardSpa1 = findViewById(R.id.cardSpa1)
        cardSpa2 = findViewById(R.id.cardSpa2)

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

                        startActivity(
                            Intent(this, ProfileActivity::class.java)
                        )

                    }

                    // EDITAR PERFIL
                    "✏️ Editar perfil" -> {

                        startActivity(
                            Intent(this, EditarPerfilActivity::class.java)
                        )

                    }

                    // PUBLICAR
                    "🏢 Vincular negocio" -> {

                        startActivity(
                            Intent(this, PublicarActivity::class.java)
                        )

                    }

                    // FAVORITOS
                    "❤️ Favoritos" -> {

                        startActivity(
                            Intent(this, FavoritosActivity::class.java)
                        )

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

                        startActivity(
                            Intent(this, MainActivity::class.java)
                        )

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

            startActivity(
                Intent(this, HomeActivity::class.java)
            )

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        // MARKETPLACE
        txtMarketplace.setOnClickListener {

            Toast.makeText(
                this,
                "Ya estás en Marketplace",
                Toast.LENGTH_SHORT
            ).show()

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        // PUBLICAR
        txtPublicar.setOnClickListener {

            startActivity(
                Intent(this, PublicarActivity::class.java)
            )

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        // CERRAR SESION
        txtCerrarSesion.setOnClickListener {

            startActivity(
                Intent(this, MainActivity::class.java)
            )

            finish()

        }

        // =========================
        // RECIBIR CATEGORIA HOME
        // =========================

        val categoria = intent.getStringExtra("categoria")

        when (categoria) {

            "tecnologia" -> {

                ocultarTodo()

                cardTec1.visibility = View.VISIBLE
                cardTec2.visibility = View.VISIBLE

                actualizarBotones(btnTecnologia)

            }

            "ropa" -> {

                ocultarTodo()

                cardRopa1.visibility = View.VISIBLE
                cardRopa2.visibility = View.VISIBLE

                actualizarBotones(btnRopa)

            }

            "spa" -> {

                ocultarTodo()

                cardSpa1.visibility = View.VISIBLE
                cardSpa2.visibility = View.VISIBLE

                actualizarBotones(btnSpa)

            }

            else -> {

                mostrarTodos()

                actualizarBotones(btnTodos)

            }

        }

        // TODOS
        btnTodos.setOnClickListener {

            mostrarTodos()

            actualizarBotones(btnTodos)

        }

        // TECNOLOGIA
        btnTecnologia.setOnClickListener {

            ocultarTodo()

            cardTec1.visibility = View.VISIBLE
            cardTec2.visibility = View.VISIBLE

            actualizarBotones(btnTecnologia)

        }

        // ROPA
        btnRopa.setOnClickListener {

            ocultarTodo()

            cardRopa1.visibility = View.VISIBLE
            cardRopa2.visibility = View.VISIBLE

            actualizarBotones(btnRopa)

        }

        // SPA
        btnSpa.setOnClickListener {

            ocultarTodo()

            cardSpa1.visibility = View.VISIBLE
            cardSpa2.visibility = View.VISIBLE

            actualizarBotones(btnSpa)

        }

    }

    // MOSTRAR TODO
    private fun mostrarTodos() {

        cardTec1.visibility = View.VISIBLE
        cardTec2.visibility = View.VISIBLE

        cardRopa1.visibility = View.VISIBLE
        cardRopa2.visibility = View.VISIBLE

        cardSpa1.visibility = View.VISIBLE
        cardSpa2.visibility = View.VISIBLE

    }

    // OCULTAR TODO
    private fun ocultarTodo() {

        cardTec1.visibility = View.GONE
        cardTec2.visibility = View.GONE

        cardRopa1.visibility = View.GONE
        cardRopa2.visibility = View.GONE

        cardSpa1.visibility = View.GONE
        cardSpa2.visibility = View.GONE

    }

    // BOTON ACTIVO
    private fun actualizarBotones(botonActivo: Button) {

        btnTodos.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor("#1AFFFFFF"))

        btnTecnologia.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor("#1AFFFFFF"))

        btnRopa.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor("#1AFFFFFF"))

        btnSpa.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor("#1AFFFFFF"))

        botonActivo.backgroundTintList =
            ColorStateList.valueOf(Color.parseColor("#00BFFF"))

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
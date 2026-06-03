package com.example.otn

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout

class CompraActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var btnMenu: ImageView

    private lateinit var txtInicio: TextView
    private lateinit var txtMarketplace: TextView
    private lateinit var txtProductos: TextView
    private lateinit var txtCerrarSesion: TextView

    private lateinit var btnMas: Button
    private lateinit var btnMenos: Button
    private lateinit var btnContinuar: Button

    private lateinit var txtCantidad: TextView
    private lateinit var txtSubtotal: TextView
    private lateinit var txtTotal: TextView

    private lateinit var spinnerPago: Spinner

    private var cantidad = 1

    private val precioUnitario = 4500000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compra)

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)

        btnMenu = findViewById(R.id.btnMenu)

        txtInicio = findViewById(R.id.txtInicio)
        txtMarketplace = findViewById(R.id.txtMarketplace)
        txtProductos = findViewById(R.id.txtProductos)
        txtCerrarSesion = findViewById(R.id.txtCerrarSesion)

        // CONTROLES
        btnMas = findViewById(R.id.btnMas)
        btnMenos = findViewById(R.id.btnMenos)
        btnContinuar = findViewById(R.id.btnContinuar)

        txtCantidad = findViewById(R.id.txtCantidad)
        txtSubtotal = findViewById(R.id.txtSubtotal)
        txtTotal = findViewById(R.id.txtTotal)

        spinnerPago = findViewById(R.id.spinnerPago)

        // MENU HAMBURGUESA
        btnMenu.setOnClickListener {

            drawerLayout.openDrawer(GravityCompat.START)

        }

        // MENU LATERAL

        txtInicio.setOnClickListener {

            finish()

        }

        txtMarketplace.setOnClickListener {

            Toast.makeText(
                this,
                "Marketplace",
                Toast.LENGTH_SHORT
            ).show()

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        txtProductos.setOnClickListener {

            Toast.makeText(
                this,
                "Productos",
                Toast.LENGTH_SHORT
            ).show()

            drawerLayout.closeDrawer(GravityCompat.START)

        }

        txtCerrarSesion.setOnClickListener {

            Toast.makeText(
                this,
                "Sesión cerrada",
                Toast.LENGTH_SHORT
            ).show()

            finish()

        }

        // SPINNER
        val metodosPago = arrayOf(
            "Seleccionar método de pago",
            "Nequi",
            "Bancolombia",
            "Davivienda",
            "BBVA",
            "Banco de Bogotá",
            "Tarjeta Débito",
            "Tarjeta Crédito"
        )

        val adapter = ArrayAdapter(
            this,
            R.layout.item_spinner,
            metodosPago
        )

        adapter.setDropDownViewResource(
            R.layout.item_spinner_dropdown
        )

        spinnerPago.adapter = adapter

        actualizarTotal()

        // BOTON +
        btnMas.setOnClickListener {

            cantidad++

            actualizarTotal()

        }

        // BOTON -
        btnMenos.setOnClickListener {

            if (cantidad > 1) {

                cantidad--

                actualizarTotal()

            }

        }

        // CONTINUAR
        btnContinuar.setOnClickListener {

            if (spinnerPago.selectedItemPosition == 0) {

                Toast.makeText(
                    this,
                    "Seleccione un método de pago",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener

            }

            val metodoSeleccionado =
                spinnerPago.selectedItem.toString()

            val intent = Intent(
                this,
                PagoActivity::class.java
            )

            intent.putExtra(
                "metodoPago",
                metodoSeleccionado
            )

            intent.putExtra(
                "cantidad",
                cantidad
            )

            intent.putExtra(
                "total",
                cantidad * precioUnitario
            )

            startActivity(intent)

        }

    }

    private fun actualizarTotal() {

        val total = cantidad * precioUnitario

        txtCantidad.text = cantidad.toString()

        txtSubtotal.text =
            "Subtotal: $" +
                    String.format("%,d", total)
                        .replace(',', '.')

        txtTotal.text =
            "Total: $" +
                    String.format("%,d", total)
                        .replace(',', '.')

    }

    override fun onBackPressed() {

        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {

            drawerLayout.closeDrawer(GravityCompat.START)

        } else {

            super.onBackPressed()

        }

    }
}
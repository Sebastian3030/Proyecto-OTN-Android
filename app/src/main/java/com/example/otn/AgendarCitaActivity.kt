package com.example.otn

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import java.util.Calendar

class AgendarCitaActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    private lateinit var btnMenu: ImageView

    private lateinit var spinnerServicio: Spinner
    private lateinit var spinnerHora: Spinner

    private lateinit var txtFecha: TextView

    private lateinit var btnSeleccionarFecha: Button
    private lateinit var btnAgendar: Button

    private lateinit var edtNombre: EditText
    private lateinit var edtCorreo: EditText
    private lateinit var edtTelefono: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agendar_cita)

        // DRAWER
        drawerLayout = findViewById(R.id.drawerLayout)

        btnMenu = findViewById(R.id.btnMenu)

        // REFERENCIAS
        spinnerServicio = findViewById(R.id.spinnerServicio)
        spinnerHora = findViewById(R.id.spinnerHora)

        txtFecha = findViewById(R.id.txtFecha)

        btnSeleccionarFecha =
            findViewById(R.id.btnSeleccionarFecha)

        btnAgendar =
            findViewById(R.id.btnAgendar)

        edtNombre =
            findViewById(R.id.edtNombre)

        edtCorreo =
            findViewById(R.id.edtCorreo)

        edtTelefono =
            findViewById(R.id.edtTelefono)

        // MENU
        btnMenu.setOnClickListener {

            drawerLayout.openDrawer(
                GravityCompat.START
            )

        }

        // SERVICIOS
        val servicios = arrayOf(
            "Seleccionar servicio",
            "Tecnología",
            "Ropa",
            "SPA"
        )

        val adapterServicios = ArrayAdapter(
            this,
            R.layout.item_spinner,
            servicios
        )

        adapterServicios.setDropDownViewResource(
            R.layout.item_spinner_dropdown
        )

        spinnerServicio.adapter =
            adapterServicios

        // HORARIOS
        val horarios = arrayOf(
            "Seleccionar hora",
            "08:00 AM",
            "09:00 AM",
            "10:00 AM",
            "11:00 AM",
            "02:00 PM",
            "03:00 PM",
            "04:00 PM",
            "05:00 PM"
        )

        val adapterHora = ArrayAdapter(
            this,
            R.layout.item_spinner,
            horarios
        )

        adapterHora.setDropDownViewResource(
            R.layout.item_spinner_dropdown
        )

        spinnerHora.adapter =
            adapterHora

        // FECHA
        btnSeleccionarFecha.setOnClickListener {

            val calendario =
                Calendar.getInstance()

            val year =
                calendario.get(Calendar.YEAR)

            val month =
                calendario.get(Calendar.MONTH)

            val day =
                calendario.get(Calendar.DAY_OF_MONTH)

            val datePicker =
                DatePickerDialog(
                    this,
                    { _, y, m, d ->

                        txtFecha.text =
                            "$d/${m + 1}/$y"

                    },
                    year,
                    month,
                    day
                )

            datePicker.show()

        }

        // AGENDAR
        btnAgendar.setOnClickListener {

            val nombre =
                edtNombre.text.toString().trim()

            val correo =
                edtCorreo.text.toString().trim()

            val telefono =
                edtTelefono.text.toString().trim()

            if (spinnerServicio.selectedItemPosition == 0) {

                Toast.makeText(
                    this,
                    "Seleccione un servicio",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (txtFecha.text.toString()
                == "Seleccionar fecha"
            ) {

                Toast.makeText(
                    this,
                    "Seleccione una fecha",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (spinnerHora.selectedItemPosition == 0) {

                Toast.makeText(
                    this,
                    "Seleccione una hora",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            if (nombre.isEmpty() ||
                correo.isEmpty() ||
                telefono.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Complete todos los campos",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val intent = Intent(
                this,
                CitaConfirmadaActivity::class.java
            )

            intent.putExtra(
                "nombre",
                nombre
            )

            intent.putExtra(
                "correo",
                correo
            )

            intent.putExtra(
                "telefono",
                telefono
            )

            intent.putExtra(
                "servicio",
                spinnerServicio.selectedItem.toString()
            )

            intent.putExtra(
                "fecha",
                txtFecha.text.toString()
            )

            intent.putExtra(
                "hora",
                spinnerHora.selectedItem.toString()
            )

            startActivity(intent)

        }

    }

    override fun onBackPressed() {

        if (
            drawerLayout.isDrawerOpen(
                GravityCompat.START
            )
        ) {

            drawerLayout.closeDrawer(
                GravityCompat.START
            )

        } else {

            super.onBackPressed()

        }

    }

}
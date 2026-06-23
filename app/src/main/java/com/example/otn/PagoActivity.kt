package com.example.otn

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class PagoActivity : AppCompatActivity() {

    private lateinit var btnVolver: ImageView
    private lateinit var txtMetodoPago: TextView
    private lateinit var txtTotalPagar: TextView
    private lateinit var layoutNequi: LinearLayout
    private lateinit var layoutBanco: LinearLayout

    // NEQUI
    private lateinit var edtCelular: EditText
    private lateinit var edtClaveDinamica: EditText

    // BANCOS
    private lateinit var edtTitular: EditText
    private lateinit var edtDocumento: EditText
    private lateinit var edtCuenta: EditText
    private lateinit var edtClave: EditText

    private lateinit var btnConfirmarPago: Button

    private var metodoPago = ""
    private var total = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pago)

        // 1. ASIGNACIÓN DE VISTAS CON LOS IDS EXACTOS DE TU XML
        btnVolver = findViewById(R.id.btnVolver)
        txtMetodoPago = findViewById(R.id.txtMetodoPago)
        txtTotalPagar = findViewById(R.id.txtTotalPagar)
        layoutNequi = findViewById(R.id.layoutNequi)
        layoutBanco = findViewById(R.id.layoutBanco)

        edtCelular = findViewById(R.id.edtCelular)
        edtClaveDinamica = findViewById(R.id.edtClaveDinamica)

        edtTitular = findViewById(R.id.edtTitular)
        edtDocumento = findViewById(R.id.edtDocumento)
        edtCuenta = findViewById(R.id.edtCuenta)
        edtClave = findViewById(R.id.edtClave)

        btnConfirmarPago = findViewById(R.id.btnConfirmarPago)

        // 2. RECUPERAR LOS DATOS ENVIADOS DESDE COMPRAACTIVITY
        metodoPago = intent.getStringExtra("metodoPago") ?: ""
        total = intent.getIntExtra("total", 0)

        // Asignar los valores a los textos de la interfaz
        txtMetodoPago.text = "Método de pago: $metodoPago"
        txtTotalPagar.text = "Total: $" + String.format("%,d", total).replace(',', '.')

        // 3. CONTROL VISUAL DE FORMULARIOS SEGÚN EL MÉTODO SELECCIONADO
        layoutNequi.visibility = View.GONE
        layoutBanco.visibility = View.GONE

        if (metodoPago.lowercase() == "nequi") {
            layoutNequi.visibility = View.VISIBLE
        } else {
            layoutBanco.visibility = View.VISIBLE
        }

        // 4. ACCIONES DE LOS BOTONES
        btnVolver.setOnClickListener { mostrarDialogoCancelar() }

        btnConfirmarPago.setOnClickListener {
            if (metodoPago.lowercase() == "nequi") {
                val celular = edtCelular.text.toString().trim()
                val claveDinamica = edtClaveDinamica.text.toString().trim()

                if (celular.isEmpty() || claveDinamica.isEmpty()) {
                    Toast.makeText(this, "Complete todos los campos de Nequi", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            } else {
                val titular = edtTitular.text.toString().trim()
                val documento = edtDocumento.text.toString().trim()
                val cuenta = edtCuenta.text.toString().trim()
                val clave = edtClave.text.toString().trim()

                if (titular.isEmpty() || documento.isEmpty() || cuenta.isEmpty() || clave.isEmpty()) {
                    Toast.makeText(this, "Complete todos los campos bancarios", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Simulación de transacción exitosa
            Toast.makeText(this, "Pago procesado correctamente", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    private fun mostrarDialogoCancelar() {
        AlertDialog.Builder(this)
            .setTitle("Cancelar transacción")
            .setMessage("Si sales ahora perderás los datos ingresados. ¿Deseas cancelar la compra?")
            .setPositiveButton("Sí") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onBackPressed() {
        mostrarDialogoCancelar()
    }
}
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

        // REFERENCIAS
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

        // DATOS RECIBIDOS
        metodoPago = intent.getStringExtra("metodoPago") ?: ""
        total = intent.getIntExtra("total", 0)

        txtMetodoPago.text = "Método de pago: $metodoPago"

        txtTotalPagar.text =
            "Total: $" +
                    String.format("%,d", total)
                        .replace(',', '.')

        // MOSTRAR FORMULARIO CORRECTO
        layoutNequi.visibility = View.GONE
        layoutBanco.visibility = View.GONE

        if (metodoPago == "Nequi") {

            layoutNequi.visibility = View.VISIBLE

        } else {

            layoutBanco.visibility = View.VISIBLE

        }

        // BOTON VOLVER
        btnVolver.setOnClickListener {

            mostrarDialogoCancelar()

        }

        // CONFIRMAR PAGO
        btnConfirmarPago.setOnClickListener {

            if (metodoPago == "Nequi") {

                if (
                    edtCelular.text.toString().trim().isEmpty() ||
                    edtClaveDinamica.text.toString().trim().isEmpty()
                ) {

                    Toast.makeText(
                        this,
                        "Complete todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }

            } else {

                if (
                    edtTitular.text.toString().trim().isEmpty() ||
                    edtDocumento.text.toString().trim().isEmpty() ||
                    edtCuenta.text.toString().trim().isEmpty() ||
                    edtClave.text.toString().trim().isEmpty()
                ) {

                    Toast.makeText(
                        this,
                        "Complete todos los campos",
                        Toast.LENGTH_SHORT
                    ).show()

                    return@setOnClickListener
                }

            }




            Toast.makeText(
                this,
                "Pago procesado correctamente",
                Toast.LENGTH_LONG
            ).show()

        }

    }

    private fun mostrarDialogoCancelar() {

        AlertDialog.Builder(this)
            .setTitle("Cancelar transacción")
            .setMessage(
                "Si sales ahora perderás los datos ingresados. ¿Deseas cancelar la compra?"
            )
            .setPositiveButton("Sí") { _, _ ->

                finish()

            }
            .setNegativeButton("No", null)
            .show()

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {

        mostrarDialogoCancelar()

    }
}
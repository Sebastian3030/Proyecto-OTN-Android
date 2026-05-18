package com.example.otn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var txtRegistro: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias
        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtRegistro = findViewById(R.id.txtRegistro)

        // LOGIN
        btnLogin.setOnClickListener {

            val correo = etCorreo.text.toString()
            val password = etPassword.text.toString()

            // Usuario temporal
            val correoCorrecto = "admin"
            val passwordCorrecta = "1234"

            if (correo == correoCorrecto && password == passwordCorrecta) {

                Toast.makeText(
                    this,
                    "Bienvenido a OTN",
                    Toast.LENGTH_SHORT
                ).show()

                // Ir al Home
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)

            } else {

                Toast.makeText(
                    this,
                    "Correo o contraseña incorrectos",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }

        // Ir a registro
        txtRegistro.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)

        }
    }
}
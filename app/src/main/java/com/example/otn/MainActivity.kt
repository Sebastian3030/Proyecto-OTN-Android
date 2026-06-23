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

        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        txtRegistro = findViewById(R.id.txtRegistro)

        btnLogin.setOnClickListener {
            val correo = etCorreo.text.toString().trim()
            val password = etPassword.text.toString().trim()

            // Validaciones locales antes de enviar al servidor
            if (correo.isEmpty()) {
                etCorreo.error = "El correo es obligatorio"
                etCorreo.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "La contraseña es obligatoria"
                etPassword.requestFocus()
                return@setOnClickListener
            }

            // --- ESPACIO PARA CONEXIÓN BACKEND (Firebase / API Rest) ---
            // Aquí reemplazarás este bloque por tu llamada de autenticación real
            val correoCorrecto = "admin"
            val passwordCorrecta = "1234"

            if (correo == correoCorrecto && password == passwordCorrecta) {
                Toast.makeText(this, "Bienvenido a OTN", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, HomeActivity::class.java))
                finish() // Eliminamos login de la pila para que no regresen con botón atrás
            } else {
                Toast.makeText(this, "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }

        txtRegistro.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
package com.example.otn

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etPassword: EditText
    private lateinit var etConfirmPassword: EditText

    private lateinit var btnRegistrar: Button
    private lateinit var txtLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Referencias
        etNombre = findViewById(R.id.etNombre)
        etCorreo = findViewById(R.id.etCorreo)
        etPassword = findViewById(R.id.etPassword)
        etConfirmPassword = findViewById(R.id.etConfirmPassword)

        btnRegistrar = findViewById(R.id.btnRegistrar)
        txtLogin = findViewById(R.id.txtLogin)

        // ==========================================
        // ACCIÓN BOTÓN REGISTRAR (BACKEND-READY)
        // ==========================================
        btnRegistrar.setOnClickListener {

            // 1. CAPTURA DE DATOS LIMPIOS (Sin espacios invisibles en los extremos)
            val nombre = etNombre.text.toString().trim()
            val correo = etCorreo.text.toString().trim()
            val password = etPassword.text.toString().trim()
            val confirmPassword = etConfirmPassword.text.toString().trim()

            // 2. VALIDACIONES DE SEGURIDAD EXIGIDAS EN PRODUCCIÓN
            if (nombre.isEmpty()) {
                etNombre.error = "El nombre es obligatorio"
                return@setOnClickListener
            }

            if (correo.isEmpty()) {
                etCorreo.error = "El correo es obligatorio"
                return@setOnClickListener
            }

            // Valida que sea un formato de correo real (ejemplo@dominio.com)
            if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                etCorreo.error = "Ingrese un formato de correo electrónico válido"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPassword.error = "La contraseña es obligatoria"
                return@setOnClickListener
            }

            // Firebase exige mínimo 6 caracteres por seguridad
            if (password.length < 6) {
                etPassword.error = "La contraseña debe tener mínimo 6 caracteres"
                return@setOnClickListener
            }

            if (confirmPassword.isEmpty()) {
                etConfirmPassword.error = "Por favor, confirme su contraseña"
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                etConfirmPassword.error = "Las contraseñas no coinciden"
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // ====================================================================
            // ZONA BACKEND: Aquí crearás el usuario en tu servicio de Auth o BD.
            // Ejemplo futuro con Firebase:
            // auth.createUserWithEmailAndPassword(correo, password).addOnCompleteListener { ... }
            // ====================================================================

            Toast.makeText(this, "Cuenta creada correctamente", Toast.LENGTH_SHORT).show()

            // Volver al login de forma limpia
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish() // Cerramos el registro para que no quede en el historial si da atrás
        }

        // VOLVER AL LOGIN
        txtLogin.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
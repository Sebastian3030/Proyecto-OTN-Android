package com.example.otn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val crearCuenta = findViewById<TextView>(R.id.txtCrearCuenta)

        crearCuenta.setOnClickListener {

            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent)

            val btnLogin = findViewById<Button>(R.id.btnLogin)

            btnLogin.setOnClickListener {

                val intent = Intent(this, MarketplaceActivity::class.java)
                startActivity(intent)

            }

        }
    }
}
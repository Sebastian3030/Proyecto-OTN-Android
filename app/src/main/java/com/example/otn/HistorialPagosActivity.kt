package com.example.otn

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class HistorialPagosActivity : AppCompatActivity() {

    private lateinit var btnVolverPagos: ImageView
    private lateinit var topBarPagos: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_pagos)

        // 1. ENLAZAR LA BARRA SUPERIOR
        topBarPagos = findViewById(R.id.topBarPagos)
        btnVolverPagos = findViewById(R.id.btnVolverPagos)

        // 2. PARCHE PUNCH HOLE (EVITAR QUE LA CÁMARA TAPE TU TOPBAR)
        ViewCompat.setOnApplyWindowInsetsListener(topBarPagos) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(
                view.paddingLeft,
                statusBarInsets.top, // Inserta dinámicamente la altura de la muesca/cámara
                view.paddingRight,
                view.paddingBottom
            )
            insets
        }

        // 3. ACCIÓN PARA VOLVER AL HOME
        btnVolverPagos.setOnClickListener {
            onBackPressed()
        }
    }
}
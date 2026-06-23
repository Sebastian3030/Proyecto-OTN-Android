package com.example.otn

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HistorialPagosActivity : AppCompatActivity() {

    private lateinit var topBarPagos: LinearLayout
    private lateinit var btnVolverPagos: ImageView
    private lateinit var recyclerPagos: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_pagos)

        // 1. VINCULAR EXACTAMENTE LOS IDS DE TU XML
        topBarPagos = findViewById(R.id.topBarPagos)
        btnVolverPagos = findViewById(R.id.btnVolverPagos)
        recyclerPagos = findViewById(R.id.recyclerPagos)

        // 2. CONFIGURAR EL RECYCLERVIEW (LayoutManager obligatorio para que pinte los items)
        recyclerPagos.layoutManager = LinearLayoutManager(this)

        // 3. PARCHE PUNCH HOLE (Aplica el padding exacto de la barra de estado a tu topBar)
        ViewCompat.setOnApplyWindowInsetsListener(topBarPagos) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // 4. ACCIÓN DEL BOTÓN VOLVER (Cierra la actividad actual de forma segura)
        btnVolverPagos.setOnClickListener {
            onBackPressed()
        }
    }
}
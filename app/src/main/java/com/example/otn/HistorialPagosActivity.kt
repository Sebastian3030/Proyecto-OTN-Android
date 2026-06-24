package com.example.otn

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// Clase modelo para estructurar cada transacción de pago
data class Pago(val id: String, val concepto: String, val monto: String, val infoTransaccion: String)

class HistorialPagosActivity : AppCompatActivity() {

    private lateinit var topBarPagos: LinearLayout
    private lateinit var btnVolverPagos: ImageView
    private lateinit var recyclerPagos: RecyclerView

    // Arreglo para almacenar el listado de tus pagos
    private var listaDePagos = ArrayList<Pago>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial_pagos)

        // 1. VINCULAR EXACTAMENTE LOS IDS DE TU XML
        topBarPagos = findViewById(R.id.topBarPagos)
        btnVolverPagos = findViewById(R.id.btnVolverPagos)
        recyclerPagos = findViewById(R.id.recyclerPagos)

        // 2. CONFIGURAR EL RECYCLERVIEW
        recyclerPagos.layoutManager = LinearLayoutManager(this)

        // 3. CARGAR DATOS Y ASIGNAR EL ADAPTADOR EXCLUSIVO
        cargarHistorialPagos()

        // 4. PARCHE PUNCH HOLE (Barra de estado / Muesca)
        ViewCompat.setOnApplyWindowInsetsListener(topBarPagos) { view, insets ->
            val statusBarInsets = insets.getInsets(WindowInsetsCompat.Type.statusBars())
            view.setPadding(view.paddingLeft, statusBarInsets.top, view.paddingRight, view.paddingBottom)
            insets
        }

        // 5. ACCIÓN DEL BOTÓN VOLVER
        btnVolverPagos.setOnClickListener {
            onBackPressedDispatcher.onBackPressed() // Forma moderna y recomendada en Android
        }
    }

    private fun cargarHistorialPagos() {
        listaDePagos.clear()

        // --- SIMULACIÓN DE DATOS (MOCK) ---
        // Aquí agregas los pagos de prueba para verificar el diseño. Luego lo conectas a tu API o BD.
        listaDePagos.add(Pago("1", "Compra de iPhone 17 Pro Max", "💰 Total: $4.500.000", "Ref: #85940 • 22 de Junio, 2026"))
        listaDePagos.add(Pago("2", "Reserva Masaje SPA Relajante", "💰 Total: $120.000", "Ref: #85941 • 15 de Junio, 2026"))
        listaDePagos.add(Pago("3", "Suscripción Mensual Premium", "💰 Total: $45.000", "Ref: #85942 • 01 de Junio, 2026"))

        // 💡 Inyectamos el nuevo adaptador que sigue la frecuencia de tu archivo xml
        recyclerPagos.adapter = HistorialPagosAdapter(listaDePagos)
    }
}
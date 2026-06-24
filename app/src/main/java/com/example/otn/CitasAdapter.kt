package com.example.otn

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CitasAdapter(private val listaCitas: List<Cita>) : RecyclerView.Adapter<CitasAdapter.CitaViewHolder>() {

    // Clase interna que enlaza los componentes visuales del item_cita.xml
    class CitaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtServicio: TextView = view.findViewById(R.id.itemServicio)
        val txtFechaHora: TextView = view.findViewById(R.id.itemFechaHora)
        val txtEstado: TextView = view.findViewById(R.id.itemEstado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cita, parent, false)
        return CitaViewHolder(view)
    }

    override fun onBindViewHolder(holder: CitaViewHolder, position: Int) {
        val cita = listaCitas[position]

        // Asignamos los textos dinámicos correspondientes
        holder.txtServicio.text = cita.servicio
        holder.txtFechaHora.text = cita.fechaHora
        holder.txtEstado.text = cita.estado

        // Cambiamos el color del estado de forma inteligente
        if (cita.estado.contains("Confirmada", ignoreCase = true)) {
            holder.txtEstado.setTextColor(Color.parseColor("#4CAF50")) // Verde esmeralda elegante
        } else if (cita.estado.contains("Pendiente", ignoreCase = true)) {
            holder.txtEstado.setTextColor(Color.parseColor("#FFEB3B")) // Amarillo Material
        } else {
            holder.txtEstado.setTextColor(Color.parseColor("#FF5252")) // Rojo por si acaso (Cancelada/Rechazada)
        }
    }

    override fun getItemCount(): Int = listaCitas.size
}
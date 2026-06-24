package com.example.otn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistorialPagosAdapter(private val listaPagos: List<Pago>) : RecyclerView.Adapter<HistorialPagosAdapter.HistorialPagoViewHolder>() {

    class HistorialPagoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtConcepto: TextView = view.findViewById(R.id.itemConcepto)
        val txtMonto: TextView = view.findViewById(R.id.itemMonto)
        val txtInfo: TextView = view.findViewById(R.id.itemFechaPago)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistorialPagoViewHolder {
        // 💡 Siguiendo la frecuencia exacta con tu archivo: item_historial_pago.xml
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_historial_pago, parent, false)
        return HistorialPagoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistorialPagoViewHolder, position: Int) {
        val pago = listaPagos[position]
        holder.txtConcepto.text = pago.concepto
        holder.txtMonto.text = pago.monto
        holder.txtInfo.text = pago.infoTransaccion
    }

    override fun getItemCount(): Int = listaPagos.size
}
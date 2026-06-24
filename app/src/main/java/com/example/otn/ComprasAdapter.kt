package com.example.otn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ComprasAdapter(private val listaCompras: List<Compra>) : RecyclerView.Adapter<ComprasAdapter.CompraViewHolder>() {

    class CompraViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtProducto: TextView = view.findViewById(R.id.itemProducto)
        val txtPrecio: TextView = view.findViewById(R.id.itemPrecio)
        val txtFecha: TextView = view.findViewById(R.id.itemFecha)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompraViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_compra, parent, false)
        return CompraViewHolder(view)
    }

    override fun onBindViewHolder(holder: CompraViewHolder, position: Int) {
        val compra = listaCompras[position]
        holder.txtProducto.text = compra.producto
        holder.txtPrecio.text = compra.precio
        holder.txtFecha.text = compra.fecha
    }

    override fun getItemCount(): Int = listaCompras.size
}
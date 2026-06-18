package com.example.otn

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class ProductosHomeAdapter(private val listaProductos: List<Producto>) :
    RecyclerView.Adapter<ProductosHomeAdapter.ProductoViewHolder>() {

    // Aquí solo necesitamos el ImageView porque tu diseño es una sola imagen grande
    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProducto: ImageView = view.findViewById(R.id.imgProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        // Enlaza con tu item_producto.xml (el que acabamos de dejar sin fondo blanco)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]

        // Pinta la imagen del producto (ej: el iPhone o el Vestido)
        holder.imgProducto.setImageResource(producto.imagen)

        // ========================================================
        // ¡NUEVA FUNCIONALIDAD: ACCIÓN CLIC EN CADA PRODUCTO DEL HOME!
        // ========================================================
        holder.imgProducto.setOnClickListener { view ->
            // Obtenemos el contexto desde la vista presionada
            val context = view.context

            // Creamos el Intent hacia la pantalla de detalle
            val intent = Intent(context, DetalleProductoActivity::class.java)

            // Le pasamos los datos del producto actual de forma dinámica
            intent.putExtra("nombre", producto.nombre)
            intent.putExtra("precio", producto.precio)

            // Iniciamos la nueva actividad
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listaProductos.size
}
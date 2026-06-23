package com.example.otn

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ProductosHomeAdapter(private val listaProductos: List<Producto>) :
    RecyclerView.Adapter<ProductosHomeAdapter.ProductoViewHolder>() {

    // 1. Vinculamos todos los componentes reales que encontramos en tu XML
    class ProductoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgProducto: ImageView = view.findViewById(R.id.imgProducto)
        val txtNombre: TextView = view.findViewById(R.id.txtNombreProducto)
        val txtPrecio: TextView = view.findViewById(R.id.txtPrecioProducto)
        val txtCategoria: TextView = view.findViewById(R.id.txtCategoriaProducto)
        val txtUbicacion: TextView = view.findViewById(R.id.txtUbicacionProducto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return ProductoViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductoViewHolder, position: Int) {
        val producto = listaProductos[position]

        // 2. Pintamos los datos dinámicos que vienen de tu "API" (HomeActivity)
        holder.imgProducto.setImageResource(producto.imagen)
        holder.txtNombre.text = producto.nombre
        holder.txtPrecio.text = producto.precio
        holder.txtCategoria.text = producto.categoria.replaceFirstChar { it.uppercase() }

        // Colocamos una ubicación por defecto en lo que integras el campo en el modelo Producto
        holder.txtUbicacion.text = "Ubicación Disponible"

        // ====================================================================
        // OPTIMIZACIÓN DEL CLIC: Ahora se aplica a TODA la tarjeta (itemView)
        // ====================================================================
        holder.itemView.setOnClickListener { view ->
            val context = view.context

            // Creamos el Intent hacia la pantalla de detalle
            val intent = Intent(context, DetalleProductoActivity::class.java).apply {
                // Pasamos los datos dinámicos del producto seleccionado
                putExtra("nombre", producto.nombre)
                putExtra("precio", producto.precio)
                putExtra("imagen", producto.imagen)
            }

            // Lanzamos la actividad
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = listaProductos.size
}
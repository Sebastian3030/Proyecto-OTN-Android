package com.example.otn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CarruselAdapter(
    private val listaBanners: List<Any>, // Usamos 'Any' para que soporte tanto URLs (String) como Drawables (Int)
    private val onBannerClick: (Int) -> Unit
) : RecyclerView.Adapter<CarruselAdapter.CarruselViewHolder>() {

    class CarruselViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imgBanner: ImageView = view.findViewById(R.id.imgBanner) // Asegúrate que tu item_carrusel.xml tenga este ID
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarruselViewHolder {
        val vista = LayoutInflater.from(parent.context).inflate(R.layout.item_carrusel, parent, false)
        return CarruselViewHolder(vista)
    }

    override fun onBindViewHolder(holder: CarruselViewHolder, position: Int) {
        val banner = listaBanners[position]

        // ======================================================================
        // CONTROL DE CARGA INTELIGENTE (BACKEND-READY)
        // ======================================================================
        if (banner is String) {
            // Si el backend te manda una URL de internet (Firebase Storage, Cloudinary, etc.)
            Glide.with(holder.itemView.context)
                .load(banner)
                .placeholder(R.drawable.placeholder_loading) // Imagen temporal mientras descarga
                .error(R.drawable.placeholder_error) // Imagen por si falla el internet
                .into(holder.imgBanner)
        } else if (banner is Int) {
            // Si es un archivo local de la carpeta drawable (Tu maqueta actual)
            holder.imgBanner.setImageResource(banner)
        }

        // Evento de clic en el banner
        holder.itemView.setOnClickListener {
            onBannerClick(position)
        }
    }

    override fun getItemCount(): Int = listaBanners.size
}
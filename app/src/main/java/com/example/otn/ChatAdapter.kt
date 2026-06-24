package com.example.otn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ChatAdapter(private val listaMensajes: List<MensajeChat>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_USUARIO = 1
    private val TYPE_VENDEDOR = 2

    override fun getItemViewType(position: Int): Int {
        // Retorna el tipo de vista dependiendo de quién envió el mensaje
        return if (listaMensajes[position].esUsuarioActual) TYPE_USUARIO else TYPE_VENDEDOR
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == TYPE_USUARIO) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensaje_usuario, parent, false)
            UsuarioViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mensaje_vendedor, parent, false)
            VendedorViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val mensaje = listaMensajes[position]

        if (holder is UsuarioViewHolder) {
            holder.txtMensaje.text = mensaje.texto
        } else if (holder is VendedorViewHolder) {
            holder.txtMensaje.text = mensaje.texto
        }
    }

    override fun getItemCount(): Int = listaMensajes.size

    // Holders exclusivos para cada tipo de burbuja
    class UsuarioViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtMensaje: TextView = view.findViewById(R.id.txtMensajeUsuario)
    }

    class VendedorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtMensaje: TextView = view.findViewById(R.id.txtMensajeVendedor)
    }
}
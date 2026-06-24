package com.example.otn

data class MensajeChat(
    val id: String,
    val texto: String,
    val esUsuarioActual: Boolean,
    val hora: String
)
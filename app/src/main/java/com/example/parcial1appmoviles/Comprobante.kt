package com.example.parcial1appmoviles

import java.time.LocalDateTime

data class Comprobante(
    val id: Int,
    val monto: Int,
    val fecha: LocalDateTime
)
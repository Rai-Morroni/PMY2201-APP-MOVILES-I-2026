package main.kotlin.cl.duoc.pizzeria.model

data class Cliente(
    override val nombre: String,
    override var telefono: String,
    override val email: String,
    override val rut: String,
    val direccion: String
) : Usuario(nombre, telefono, email, rut)
package main.kotlin.cl.duoc.pizzeria.model

class Cliente(
    nombre: String,
    telefono: String,
    email: String,
    rut: String,
    val direccion: String
) : Usuario(nombre, telefono, email, rut) {}
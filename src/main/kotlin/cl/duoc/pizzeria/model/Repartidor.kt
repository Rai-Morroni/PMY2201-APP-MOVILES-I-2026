package main.kotlin.cl.duoc.pizzeria.model

class Repartidor(
    nombre: String,
    telefono: String,
    email: String,
    rut: String,
    val disponibilidad: String      // "Disponible" o "No disponible"
) : Usuario(nombre, telefono, email, rut) {

    fun entregarPedido(pedido: Pedido) {
        println("El repartidor $nombre repartirá el pedido: ${pedido.idPedido}")
    }
}
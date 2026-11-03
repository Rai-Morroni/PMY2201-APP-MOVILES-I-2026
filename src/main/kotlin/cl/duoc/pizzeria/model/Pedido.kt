package main.kotlin.cl.duoc.pizzeria.model

class Pedido(
    val idPedido: String,       // Identificador único del pedido
    val cantidadPizzas: Int,    // Cantidad de pizzas en el pedido
    val totalNeto: Int,         // Precio total ante de aplicar descuentos
    val totalPedido: Double,    // Total calculado del pedido después de aplicar descuentos
    val estadoPedido: String    // “Preparación”, “En Camino”, “Entregado”
)





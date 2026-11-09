package main.kotlin.cl.duoc.pizzeria.model
import kotlin.math.ceil

// 1. Declaración Enum
enum class EstadoPedido {
    EN_PREPARACION,
    LISTO,
    EN_CAMINO
}

// 2. Refactorizacion de clase Pedido
class Pedido(
    val idPedido: String,       // Identificador único del pedido
    val cliente: Cliente,       // Integra el objeto Cliente completo, no solo datos sueltos
    val tipoPizza: Pizza,       // Integra el objeto Pizza para luego entregarlo a Lista de Productos
    val cantidadPizzas: Int

) {
    // El estado ya no entra por el constructor, se crea por defecto  "EN_PREPARACION". Se asigna como 'var' para poder mutar
    var estado: EstadoPedido = EstadoPedido.EN_PREPARACION

    // 3. Metodo para modificar el comportamiento
    fun cambiarEstado(nuevoEstado: EstadoPedido) {
        estado = nuevoEstado
        println("El pedido $idPedido ahora está: $estado")
    }

    // 4. Metodo para los calculos matematicos
    fun calcularTotal(): Double {
        val subtotal = cantidadPizzas * tipoPizza.precioUnitario

        // Regla negocio: 10% de descuento si son más de 3 pizzas
        val descuento = if (cantidadPizzas > 3) 0.90 else 1.0
        val totalConDescuento = subtotal * descuento

        // Regla negocio: Agregar impuesto IVA (19%)
        val totalConIva = totalConDescuento * 1.19

        // Regla negocio: Redondear cifra hacia arriba (CLP)
        return ceil(totalConIva)
    }

    // Funciones manuales de desestructuración
    // Al desestructurar Pedido, el orden de los elementos será el siguiente: Cliente, Pizza y calcularTotal.
    operator fun component1(): Cliente = this.cliente
    operator fun component2(): Pizza = this.tipoPizza
    operator fun component3(): Double = this.calcularTotal()

    // Sobrecarga del operador '+' para combinar pedidos
    operator fun plus(otroPedido: Pedido): Pedido {
        // Sumamos las cantidades de pizzas
        val cantidadCombinada = this.cantidadPizzas + otroPedido.cantidadPizzas

        // Regresa un nuevo objeto Pedido que contiene ambos pedidos
        return Pedido(
            idPedido = "${this.idPedido}-${otroPedido.idPedido}", // Fusionamos los IDs (Solo para testing, el ID aun es estatico).
            cliente = this.cliente,
            tipoPizza = this.tipoPizza,
            cantidadPizzas = cantidadCombinada
        )
    }
}
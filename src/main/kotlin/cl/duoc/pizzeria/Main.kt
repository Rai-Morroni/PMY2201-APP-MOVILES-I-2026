package main.kotlin.cl.duoc.pizzeria

import main.kotlin.cl.duoc.pizzeria.model.Cliente
import main.kotlin.cl.duoc.pizzeria.model.Pedido
import main.kotlin.cl.duoc.pizzeria.model.Pizza
import main.kotlin.cl.duoc.pizzeria.model.Repartidor

fun main() {

    println("=== KOTLIN PIZZA ===")

    // ===== Tipos de Datos =====
    val idPedido: Int = 1001

    // 1. Registro de datos del cliente

    //a. Nombre
    print("Ingrese Nombre del Cliente: ")
    val nombre = readln()?.ifBlank { "Cliente Vacio" } ?: "Cliente Vacio"

    //b. Dirección
    print("Ingrese Dirección de Entrega: ")
    val direccion = readln()?.ifBlank { "Sin Dirección" } ?: "Sin Dirección"

    var telefono: String
    var telefonoValido = false

    //c. Teléfono
    do{
        print("Ingresa Número de Teléfono: ")
        telefono = readln().ifBlank {""}

        val longitudTelefono = telefono.length in 9..11
        val soloNumeros = telefono.all { it.isDigit() } && telefono.isNotEmpty()

        if (longitudTelefono && soloNumeros) {
            telefonoValido = true
        } else {
            println("Formato de teléfono inválido. Intentalo nuevamente.")
        }
    } while (!telefonoValido)


    println("Cliente Registrado: $idPedido, $nombre, $direccion, $telefono")

    // 2. Selección del pedido

    // a. Tipo de Pizza
    println("Selecciona el tipo de Pizza:")
    println("1. Peperonni")
    println("2. Cuatro Quesos")
    print("Opción: ")
    val opcionPizza = readln().toIntOrNull() ?: 1

    val tipoPizza = when (opcionPizza) {
        1 -> Pizza("Peperonni", 8990, 5)
        2 -> Pizza("Cuatro Quesos", 7990, 10)
        else -> Pizza("Peperonni", 8990, 5) // Opción por defecto para cualquier otro número
    }
    // b. Cantidad de Pizzas
    var cantidadPizzas: Int
    do {
        print("Ingresa la cantidad de Pizzas deseadas: ")
        cantidadPizzas = readln().toIntOrNull() ?: 0

        if (cantidadPizzas <= 0) {
            println("Debes ingresar una cantidad mayor a cero.")
        }
    } while (cantidadPizzas <= 0)

    println("Has seleccionado: ${cantidadPizzas} pizzas de ${tipoPizza.nombrePizza} con un valor de $${tipoPizza.precioUnitario} cada una.")

    // 3. Calculo del precio total
    val subTotalPedido = cantidadPizzas * tipoPizza.precioUnitario

    // Calculo de descuento para pedidos mayores a 3 pizzas.
    fun calcularDescuento(cantidadPizzas: Int, subTotalPedido: Double): Double {
        val descuentoCantidad: Double = if (cantidadPizzas > 3) 0.90 else 1.0
        return subTotalPedido * descuentoCantidad
    }

    val totalAPagar = calcularDescuento(cantidadPizzas, subTotalPedido.toDouble())
    println("El Subtotal del pedido es $${subTotalPedido}")
    println("El Total a Pagar es $${totalAPagar}")

    // 4. Manejo stock ingredientes
    if (cantidadPizzas > 0 && cantidadPizzas <= tipoPizza.ingredientesDisponibles) {
        tipoPizza.ingredientesDisponibles -= cantidadPizzas
        println("Pedido confirmado")
        println("Stock restante : ${tipoPizza.ingredientesDisponibles}")


        val clienteActual = Cliente(nombre, direccion, telefono)
        val repartidorAsignado = Repartidor("Carlos", "Disponible")
        val pedidoActual = Pedido(idPedido.toString(), cantidadPizzas, subTotalPedido, totalAPagar, "En Preparación")

        // 5. Resumen del Pedido
        println("\n==================================")
        println("      RESUMEN DEL PEDIDO          ")
        println("==================================")
        println("Cliente: ${clienteActual.nombre}")
        println("Dirección: ${clienteActual.direccion}")
        println("Teléfono: ${clienteActual.telefono}")
        println("----------------------------------")
        println("ID Pedido: ${pedidoActual.idPedido}")
        println("Tipo Pizza: ${tipoPizza.nombrePizza}")
        println("Precio Unitario: $${tipoPizza.precioUnitario}")
        println("Cantidad: ${cantidadPizzas}")
        println("SubTotal: $${subTotalPedido}")
        if (cantidadPizzas > 3) println("Descuento: 10% Aplicado")
        println("Total a Pagar: $${totalAPagar}")
        println("Estado Pedido: En Preparación")
        println("Repartidor: ${repartidorAsignado.nombre} está ${repartidorAsignado.disponibilidad}")
        println("==================================")
    } else {
        println("===================================")
        println("Stock insuficiente de ingredientes para la Pizza de ${tipoPizza.nombrePizza} (Pedido máximo: ${tipoPizza.ingredientesDisponibles} unidades).")
        println("Inténtalo nuevamente")
        println("===================================")
    }
}
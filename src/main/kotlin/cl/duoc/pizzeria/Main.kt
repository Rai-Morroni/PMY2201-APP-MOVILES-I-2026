package main.kotlin.cl.duoc.pizzeria

import main.kotlin.cl.duoc.pizzeria.model.Cliente
import main.kotlin.cl.duoc.pizzeria.model.Pedido
import main.kotlin.cl.duoc.pizzeria.model.Pizza
import main.kotlin.cl.duoc.pizzeria.model.Repartidor
import main.kotlin.cl.duoc.pizzeria.model.prepararPizza

// Ingredientes para 20 Pizzas Pepperoni y 10 Cuatro Quesos.
val inventarioIngredientes = mutableMapOf(
    "Masa" to 30,
    "Queso Mozzarella" to 30,
    "Queso Azul" to 10,
    "Queso Parmesano" to 10,
    "Queso Emmental" to 10,
    "Salsa Tomates" to 30,
    "Pepperoni" to 20
)

fun main() {

    println("=== KOTLIN PIZZA ===")

    // ===== Tipos de Datos =====
    val idPedido: Int = 1001

    // 1. Registro de datos del cliente

    //a. Nombre
    print("Ingrese Nombre del Cliente: ")
    val nombre = readln()?.ifBlank { "Cliente Vacio" } ?: "Cliente Vacio"

    //b. Teléfono
    var telefono: String
    var telefonoValido = false

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

    //c. Email
    print("Ingrese Email del Cliente: ")
    val email = readln()?.ifBlank { "Email Vacio" } ?: "Email Vacio"

    //d. RUT
    print("Ingrese RUT del Cliente: ")
    val rut = readln()?.ifBlank { "Rut Vacio" } ?: "Rut Vacio"

    //e. Dirección
    print("Ingrese Dirección de Entrega: ")
    val direccion = readln()?.ifBlank { "Sin Dirección" } ?: "Sin Dirección"


    println("Cliente Registrado: $idPedido, $nombre, $telefono, $email, $rut y $direccion")

    // 2. Selección del pedido

    // a. Tipo de Pizza
    println("Selecciona el tipo de Pizza:")
    println("1. Pepperonni")
    println("2. Cuatro Quesos")
    print("Opción: ")

    val opcionPizza = readln().toIntOrNull() ?: 1

    val tipoPizza = when (opcionPizza) {
        1 -> Pizza("Pepperoni", 8990, mapOf(
            "Masa" to 1,
            "Queso Mozzarella" to 1,
            "Salsa Tomates" to 1,
            "Pepperoni" to 1
        ))
        2 -> Pizza("Cuatro Quesos", 10990, mapOf(
            "Masa" to 1,
            "Queso Mozzarella" to 1,
            "Queso Azul" to 1,
            "Queso Parmesano" to 1,
            "Queso Emmental" to 1,
            "Salsa Tomates" to 1
        ))
        // Opción por defecto para cualquier otro número
        else -> Pizza("Pepperoni", 8990, mapOf(
            "Masa" to 1, "Queso Mozzarella" to 1, "Salsa Tomates" to 1, "Pepperoni" to 1
        ))
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
//  if (cantidadPizzas > 0 && cantidadPizzas <= tipoPizza.ingredientesDisponibles) {
    val stockSuficiente = prepararPizza(tipoPizza.recetaPizza, cantidadPizzas, inventarioIngredientes)

    if (cantidadPizzas > 0 && stockSuficiente) {
        //tipoPizza.ingredientesDisponibles -= cantidadPizzas
        println("Pedido confirmado")
        //println("Stock restante : ${tipoPizza.ingredientesDisponibles}")


        val clienteActual = Cliente(nombre = nombre, telefono = telefono, email = email, rut = rut, direccion = direccion)

        // Declaramos la lista de empleados disponibles
        val listaRepartidores = listOf(
            Repartidor("Juan", "987654321", "juan@mail.com", "11.111.111-1", "No disponible"),
            Repartidor("Carlos", "123456789", "carlos@mail.com", "22.222.222-2", "Disponible"),
            Repartidor("Ana", "555555555", "ana@mail.com", "33.333.333-3", "Disponible")
        )

        // Busca el primer repartidor disponible. Si find retorna null, el operador ?: crea un repartidor temporal por defecto.
        val repartidorAsignado = listaRepartidores.find { it.disponibilidad == "Disponible" }
            ?: Repartidor("Sin Asignar", "N/A", "N/A", "N/A", "No disponible")

        val pedidoActual = Pedido(idPedido.toString(), cantidadPizzas, subTotalPedido, totalAPagar, "En Preparación")

        // 5. Resumen del Pedido
        println("\n==================================")
        println("      RESUMEN DEL PEDIDO          ")
        println("==================================")
        println("ID Pedido: ${pedidoActual.idPedido}")
        println("Cliente: ${clienteActual.nombre}")
        println("RUT: ${clienteActual.rut}")
        println("Email: ${clienteActual.email}")
        println("Dirección: ${clienteActual.direccion}")
        println("Teléfono: ${clienteActual.telefono}")
        println("----------------------------------")
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
        println("Stock insuficiente de ingredientes para la Pizza de ${tipoPizza.nombrePizza}")
        println("Inténtalo nuevamente")
        println("===================================")
    }
}
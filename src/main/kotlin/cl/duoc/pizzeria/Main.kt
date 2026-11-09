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
    // Lista para guardar los pedidos:
    val historialPedidos = mutableListOf<Pedido>()

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
    var email = ""
    var emailValido = false
    var intentosEmail = 0
    val patronEmail = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")

    do {
        print("Ingrese Email del Cliente: ")
        // 1. Captura la entrada evitando nulos
        val emailIngresado = readln().ifBlank { "" }

        // 2. Revisión: si hace match, conserva la entrada; si no, asigna el valor por defecto
        // matches valida que la entrada coincida con el patron Regex.
        if (emailIngresado.matches(patronEmail)) {
            email = emailIngresado
            emailValido = true // Salida exitosa del bucle
        } else {
            //Contador para sumar intentos
            intentosEmail++
            if (intentosEmail < 3) {
                println("El formato del email ingresado es incorrecto. Intentos restantes: ${3 - intentosEmail}")
            } else {
                println("Máximo de intentos alcanzado. Se asignará correo por defecto: correo@invalido.com.")
                email = "correo@invalido.com"
                emailValido = true // Salida del bucle tras 3 intentos
            }
        }
    } while (!emailValido)

    //d. RUT
    var rutCompleto = ""
    var rutValido = false

    // Regex: Entre 7 y 8 números de largo + guion + terminacion en número o letra K/k
    val patronRut = Regex("^[0-9]{7,8}-[0-9Kk]$")

    do {
        print("Ingrese RUT del Cliente (SIN puntos, CON guión y CON dígito verificador): ")
        // Trim elimina espacios en blanco al inicio o final de la entrada
        val entradaRut = readln().trim()

        if (entradaRut.matches(patronRut)) {
            // Se normaliza la entrada de letra K a mayúsculas
            rutCompleto = entradaRut.uppercase()
            rutValido = true
        } else {
            println("Formato inválido. Ingrésalo sin puntos y con guion.")
        }
    } while (!rutValido)

    //e. Dirección
    print("Ingrese Dirección de Entrega: ")
    val direccion = readln()?.ifBlank { "Sin Dirección" } ?: "Sin Dirección"

    //f. Notas adicionales (Opcionales)
    print("Ingrese notas adicionales del pedido (presione Enter para omitir): ")
    val notas: String? = readlnOrNull()?.takeIf { it.isNotBlank() }

    println("\n==================================")
    println("Cliente Registrado: $idPedido, $nombre, $telefono, $email, $rutCompleto y $direccion")
    println("==================================")

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
    var cantidadPizzasValida = false
    var cantidadPizzasIngresada = 0

    do {
        print("Ingresa la cantidad de Pizzas deseadas: ")
        val entradaUsuario = readln()

        try {
            cantidadPizzasIngresada = entradaUsuario.toInt()

            if (cantidadPizzasIngresada >= 0) {
                cantidadPizzasValida = true
            } else {
                println("Debes ingresar una cantidad mayor a cero.")
            }
        } catch (e: NumberFormatException) {
            println("Error de formato: Ingresa únicamente numeros.")
        }
    } while (!cantidadPizzasValida)

    println("\n==================================")
    println("Has seleccionado: ${cantidadPizzasIngresada} pizzas de ${tipoPizza.nombrePizza} con un valor de $${tipoPizza.precioUnitario} cada una.")
    println("==================================")

    // 3. Manejo stock ingredientes
    val stockSuficiente = prepararPizza(tipoPizza.recetaPizza, cantidadPizzasIngresada, inventarioIngredientes)

    if (cantidadPizzasIngresada > 0 && stockSuficiente) {
        println("Pedido confirmado")

        val clienteActual = Cliente(nombre = nombre, telefono = telefono, email = email, rut = rutCompleto, direccion = direccion)

        // Desestructuración de Cliente
        val (nombreDesestructurado, telefonoDesestructurado, emailDesestructurado) = clienteActual

        println("\n=== DEMOSTRACIÓN SIMULADA DE DESESTRUCTURACIÓN ===")
        println("\n=== DATOS DESESTRUCTURADOS DEL CLIENTE ===")
        println("Nombre: $nombreDesestructurado | Teléfono: $telefonoDesestructurado | Email: $emailDesestructurado")

        // Declaramos la lista de empleados disponibles
        val listaRepartidores = listOf(
            Repartidor("Juan", "987654321", "juan@mail.com", "11.111.111-1", "No disponible", 10.5),
            Repartidor("Carlos", "123456789", "carlos@mail.com", "22.222.222-2", "Disponible", 8.3),
            Repartidor("Ana", "555555555", "ana@mail.com", "33.333.333-3", "Disponible", 1.5)
        )

        // Filtra a los repartidores disponibles y selecciona el que se encuentre mas cercano. Si retorna null, el operador ?: crea un repartidor temporal por defecto.
        val repartidorAsignado = listaRepartidores.filter { it.disponibilidad == "Disponible" }.minByOrNull { it.distanciaKm }
            ?: Repartidor("Sin Asignar", "N/A", "N/A", "N/A", "No disponible", 0.0)


        val pedidoActual = Pedido(idPedido.toString(), clienteActual, tipoPizza, cantidadPizzasIngresada)
        // Agrega el pedidoActual a la Lista de historial de pedidos.
        historialPedidos.add(pedidoActual)

        // Desestructuración de Pedido
        val (clientePedido, productoPedido, totalPedido) = pedidoActual

        println("\n=== DATOS DESESTRUCTURADOS DEL PEDIDO ===")
        println("Cliente: ${clientePedido.nombre} | Producto: ${productoPedido.nombrePizza} | Total Calculado: $$totalPedido")

        // 4. Resumen del Pedido
        println("\n==================================")
        println("      RESUMEN DEL PEDIDO          ")
        println("==================================")
        println("ID Pedido: ${pedidoActual.idPedido}")
        println("Cliente: ${nombreDesestructurado}")
        println("RUT: ${clienteActual.rut}")
        println("Email: ${emailDesestructurado}")
        println("Dirección: ${clienteActual.direccion}")
        println("Teléfono: ${telefonoDesestructurado}")
        println( notas?.let { "Notas: $it" } ?: "Sin notas" )
        println("----------------------------------")
        println("Tipo Pizza: ${tipoPizza.nombrePizza}")
        println("Precio Unitario: $${tipoPizza.precioUnitario}")
        println("Cantidad: ${cantidadPizzasIngresada}")
        println("SubTotal: $${(cantidadPizzasIngresada * tipoPizza.precioUnitario)}")
        if (cantidadPizzasIngresada > 3) println("Descuento: 10% Aplicado")
        println("Total a Pagar + IVA: $$totalPedido")
        println("Estado Pedido: ${pedidoActual.estado}")
        println("Repartidor: ${repartidorAsignado.nombre} está ${repartidorAsignado.disponibilidad}")
        println("==================================")

        // ===== DEMOSTRACIÓN DE SOBRECARGA =====
        println("\n=== DEMOSTRACIÓN SIMULADA DE SOBRECARGA Y METAPROGRAMACIÓN ===")

        // 1. Sobrecarga del operador '+': Combinación de pedidos
        val pedidoExtra = Pedido("2002", clienteActual, tipoPizza, 1) // Simulación: Cliente pide 1 pizza extra
        val pedidoFusionado = pedidoActual + pedidoExtra

        println("Pedidos combinados (ID original + ID extra): ${pedidoFusionado.idPedido}")
        println("Nueva cantidad total de pizzas: ${pedidoFusionado.cantidadPizzas}")
        println("Nuevo Total a Pagar: $${pedidoFusionado.calcularTotal()}")

        // 2. Sobrecarga del operador '==': Igualdad de objetos
        val pizzaComparacion = Pizza(tipoPizza.nombrePizza, tipoPizza.precioUnitario, tipoPizza.recetaPizza)
        println("¿El producto original y el de comparación son lógicamente iguales?: ${tipoPizza == pizzaComparacion}")

    } else {
        println("\n==================================")
        println("Stock insuficiente de ingredientes para la Pizza de ${tipoPizza.nombrePizza}")
        println("Inténtalo nuevamente")
        println("===================================")
    }

    // ===== CIERRE DE CAJA =====
    println("\n=== REPORTE DIARIO DE VENTAS ===")
    for (pedido in historialPedidos) {
        println("ID: ${pedido.idPedido} | Cliente: ${pedido.cliente.nombre} | Total: $${pedido.calcularTotal()} | Estado: ${pedido.estado}")
    }
}
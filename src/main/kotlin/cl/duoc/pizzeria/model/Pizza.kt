package main.kotlin.cl.duoc.pizzeria.model

class Pizza(
    val nombrePizza: String,                // “Pepperoni” o “Cuatro Quesos”
    val precioUnitario: Int,                // Precio de cada pizza
    val recetaPizza: Map<String, Int>,       // Map<String, Int> como receta de ingredientes
    //var ingredientesDisponibles: Int,     // Cantidad disponible de ingredientes principales
)

// Listado de ingredientes para cada Pizza
val pizzaPepperoni = Pizza("Pepperoni", 8990, mapOf(
    "Masa" to 1,
    "Queso Mozzarella" to 1,
    "Salsa Tomates" to 1,
    "Pepperoni" to 1
))

val pizzaCuatroQuesos = Pizza("Pepperoni", 10990, mapOf(
    "Masa" to 1,
    "Queso Mozzarella" to 1,
    "Queso Azul" to 1,
    "Queso Parmesano" to 1,
    "Queso Emmental" to 1,
    "Salsa Tomates" to 1,
))

// Control de Inventario
// Función para verificar ingredientes disponibles
fun prepararPizza(recetaPizza: Map<String, Int>, cantidadPizzas: Int, inventario: MutableMap<String, Int>): Boolean {

    // Verificación de ingredientes por pizza
    for ((ingrediente, cantidadUnitaria) in recetaPizza) {
        val totalNecesario = cantidadUnitaria * cantidadPizzas
        val stockActual = inventario[ingrediente] ?: 0

        if (stockActual < totalNecesario) {
            println("\n==================================")
            println("Error: Stock insuficiente de $ingrediente. Necesario: $totalNecesario, Disponible: $stockActual")

            // Pasos de reposicion de ingredientes
            when (ingrediente) {
                "Masa" -> println("Acción: Descongelar masas de reserva.")
                "Queso Mozzarella" -> println("Acción: Preparar nuevo lote de Mozzarella inmediatamente.")
                "Salsa Tomates" -> println("Acción: Reponer contenedores de Salsa Tomates inmediatamente.")
                else -> println("Acción: Comunicar quiebre de stock de $ingrediente a Encargado de Bodega inmediatamente.")
            }
            return false // Interrumpe la función retornando falso
        }
    }

    // Si el bucle anterior no retornó false, significa que hay stock suficiente. Procede a descontar.
    for ((ingrediente, cantidadUnitaria) in recetaPizza) {
        val totalNecesario = cantidadUnitaria * cantidadPizzas
        inventario[ingrediente] = inventario[ingrediente]!! - totalNecesario
    }
    return true // Proceso exitoso
}

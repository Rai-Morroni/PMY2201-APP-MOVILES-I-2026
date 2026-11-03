package main.kotlin.cl.duoc.pizzeria.model

class Pizza(
    val nombrePizza: String,                // “Pepperoni” o “Cuatro Quesos”
    val precioUnitario: Int,                // Precio de cada pizza
    var ingredientesDisponibles: Int,     // Cantidad disponible de ingredientes principales

)
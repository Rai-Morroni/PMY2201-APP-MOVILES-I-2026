package main.kotlin.cl.duoc.pizzeria.model

@Target(AnnotationTarget.CLASS) // Indica que esta etiqueta se pega en las Clases
@Retention(AnnotationRetention.RUNTIME) // Existe durante la ejecución de la aplicación.
annotation class Promocionable
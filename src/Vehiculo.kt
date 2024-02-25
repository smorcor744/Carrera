/**
 * La clase [Vehiculo] es una clase base que representa un vehículo en la carrera, ya sea automóvil o motocicleta.
 *
 * @property nombre El nombre único del vehículo.
 * @property marca La marca del vehículo.
 * @property modelo El modelo del vehículo.
 * @property capacidadCombustible La capacidad máxima del tanque de combustible del vehículo.
 * @property combustibleActual La cantidad actual de combustible en el tanque.
 * @property kilometrosActuales La distancia total recorrida por el vehículo en la carrera.
 */
open class Vehiculo(
    nombre: String,
    val marca: String,
    val modelo: String,
    capacidadCombustible: Float,
    combustibleActual: Float,
    var kilometrosActuales: Float
) {
    val capacidadCombustible = capacidadCombustible.redondear(2)
    var combustibleActual = combustibleActual.redondear(2)
    val nombre = nombre.trim().lowercase()

    init {
        require(!nombresEstaRepetido(this.nombre)) { "Ya existe este nombre ${this.nombre}" }
        require(capacidadCombustible > 0) { "La capacidad de combustible no puede ser menor que 0." }
        require(combustibleActual >= 0) { "El combustible actual no puede ser menor que 0." }
    }

    companion object {
        private val nombres: MutableSet<String> = mutableSetOf()
        private fun nombresEstaRepetido(nombre: String) = !nombres.add(nombre)
        const val KMPORLITRO = 10
        const val KMPORLITROHIBRIDO = 15
        const val KMPORLITROMOTO = 20
    }

    override fun toString(): String {
        return "marca = $marca, modelo = $modelo, capacidad de combustible = $capacidadCombustible, combustible actual $combustibleActual, kilometros realizados $kilometrosActuales"
    }

    open fun calcularAutonomia(): Float {
        return (combustibleActual * KMPORLITRO)
    }


    open fun realizarViaje(distancia: Float): Float {
        var posibles = calcularAutonomia()
        if (posibles >= distancia) {
            posibles -= distancia
            combustibleActual = (distancia / KMPORLITRO)
            kilometrosActuales += distancia.toInt()
            return 0.0f
        } else {
            combustibleActual = 0.0F
            kilometrosActuales += posibles.toInt()
            return (distancia - posibles).redondear(2)
        }
    }

    fun repostar(cantidad: Float = 0F): Float {
        val combustiblePrevio = combustibleActual
        if (cantidad <= 0) {
            combustibleActual = capacidadCombustible
            return cantidad
        } else combustibleActual = minOf(capacidadCombustible, combustibleActual + cantidad)
        return combustibleActual - combustiblePrevio
    }
}
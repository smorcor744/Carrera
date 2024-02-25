/**
 * Clase que representa un automóvil en la carrera.
 *
 * @property nombre El nombre único del vehículo.
 * @property marca La marca del vehículo.
 * @property modelo El modelo del vehículo.
 * @property capacidadCombustible La capacidad máxima del tanque de combustible del vehículo.
 * @property combustibleActual La cantidad actual de combustible en el tanque.
 * @property kilometrosActuales La distancia total recorrida por el vehículo en la carrera.
 * @property esHibrido Indica si el automóvil es híbrido.
 */
class Automovil(
    nombre: String,
    marca: String,
    modelo: String,
    capacidadCombustible: Float,
    combustibleActual: Float,
    kilometrosActuales: Float,
    var esHibrido: Boolean
) : Vehiculo(nombre, marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales) {
    var condicionBritania: Boolean = false

    override fun calcularAutonomia(): Float {
        return if (esHibrido) capacidadCombustible * KMPORLITROHIBRIDO else super.calcularAutonomia()
    }

    fun cambiarCondicionBritania(nuevaCondition: Boolean) {
        condicionBritania = nuevaCondition
    }

    fun realizarDerrape(): Float {
        if (esHibrido) {
            combustibleActual -= KMPORLITRO * 6.25F
        } else {
            combustibleActual -= KMPORLITRO * 7.5F
        }
        return combustibleActual.redondear(2)
    }
}
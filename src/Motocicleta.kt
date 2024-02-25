/**
 * Clase que representa una motocicleta en la carrera.
 *
 * @property nombre El nombre único del vehículo.
 * @property marca La marca del vehículo.
 * @property modelo El modelo del vehículo.
 * @property capacidadCombustible La capacidad máxima del tanque de combustible del vehículo.
 * @property combustibleActual La cantidad actual de combustible en el tanque.
 * @property kilometrosActuales La distancia total recorrida por el vehículo en la carrera.
 * @property cilindrada La cilindrada de la motocicleta.
 */
class Motocicleta(
    nombre: String,
    marca: String,
    modelo: String,
    capacidadCombustible: Float,
    combustibleActual: Float,
    kilometrosActuales: Float,
    val cilindrada: Int
) : Vehiculo(nombre, marca, modelo, capacidadCombustible, combustibleActual, kilometrosActuales) {

    init {
        require(cilindrada >= 125) { "La cilindrade debe de ser mayor a 125 y menor que 1000" }
        require(cilindrada < 1000) { "La cilindrade debe de ser mayor a 125 y menor que 1000" }
    }

    override fun calcularAutonomia(): Float {
        return if (cilindrada != 1000) {
            combustibleActual * (KMPORLITROMOTO - (cilindrada / 1000))
        } else {
            combustibleActual * KMPORLITROMOTO
        }
    }


    fun realizarCaballito(): Float {
        combustibleActual -= KMPORLITROMOTO * 6.5f
        return combustibleActual.redondear(2)
    }
}
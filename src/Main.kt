import kotlin.math.roundToInt
import kotlin.random.Random

fun main(){
    val aurora = Automovil("Aurora", "Seat", "Panda", 50f, 50f * 0.1f, 0f, true) // Coche eléctrico con capacidad de 50 litros, inicia con el 10%
    val boreal = Automovil("Boreal", "BMW", "M8", 80f, 80f * 0.1f, 0f, false) // SUV híbrido con capacidad de 80 litros, inicia con el 10%
    val cefiro = Motocicleta("Céfiro", "Derbi", "Motoreta", 15f, 15f * 0.1f, 0f, 500) // Motocicleta de gran cilindrada con capacidad de 15 litros, inicia con el 10%
    val dinamo = Automovil("Dinamo", "Cintroen", "Sor", 70f, 70f * 0.1f, 0f, true) // Camioneta eléctrica con capacidad de 70 litros, inicia con el 10%
    val eclipse = Automovil("Eclipse", "Renault", "Espacio", 60f, 60f * 0.1f, 0f, false) // Coupé deportivo con capacidad de 60 litros, inicia con el 10%
    val fenix = Motocicleta("Fénix", "Honda", "Vital", 20f, 20f * 0.1f, 0f, 250) // Motocicleta eléctrica con capacidad de 20 litros, inicia con el 10%

    val vehiculo = listOf(aurora,boreal,cefiro,dinamo,eclipse,fenix)
    val nurburgrill = Carrera("Monaco",1000f,vehiculo)
    nurburgrill.iniciarCarrera()

}

/**
 * Extensión de la clase Float para redondear el número con una cantidad específica de decimales.
 */
fun Float.redondear(posicionDecimales: Int): Float {
    if (posicionDecimales < 0) {
        return this
    }
    val factor = 10 * posicionDecimales
    val res = (this * factor).roundToInt()
    return (res / factor).toFloat()
}


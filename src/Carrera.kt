import kotlin.math.roundToInt
import kotlin.random.Random

/**
 * La clase [Carrera] representa una competencia de vehículos donde compiten en rondas hasta que uno de ellos alcanza
 * la distancia total establecida. Durante la carrera, los vehículos avanzan, realizan filigranas y pueden repostar combustible.
 *
 * @property nombreCarrera El nombre de la carrera.
 * @property distanciaTotal La distancia total de la carrera que debe alcanzar un vehículo para ganar.
 * @property participantes La lista de vehículos que participan en la carrera.
 */
class Carrera(
    private val nombreCarrera: String,
    private val distanciaTotal: Float,
    private var participantes: List<Vehiculo>
) {
    private var estadoCarrera: Boolean = false
    private var historialAcciones: MutableMap<String, MutableList<String>> = mutableMapOf()
    private var posiciones: MutableList<Pair<String, Int>> = mutableListOf()
    private lateinit var ganador: Vehiculo
    private var contRondas = 1

    init {
        require(participantes.isNotEmpty()) { "No puede haber una carrera sin participantes." }
    }

    // Método para iniciar la carrera
    fun iniciarCarrera() {
        estadoCarrera = true
        println("Comienza $nombreCarrera.\n")
        while (estadoCarrera) {
            println("Ronda $contRondas\n")
            for (vehiculo in participantes) {
                avanzarVehiculo(vehiculo)
            }
            actualizarPosiciones()
            determinarGanador()
            obtenerResultados()
            contRondas++
            if (!estadoCarrera) {
                mostrarPosiciones()
                println("\nEl ganador es: ${ganador.nombre}")
            }
        }
    }

    // Método para avanzar un vehículo en la carrera
    private fun avanzarVehiculo(vehiculo: Vehiculo) {
        val distancia: Float = Random.nextDouble(10.0, 200.0).toFloat()
        val filigranas = (distancia / 20).roundToInt()

        if (vehiculo.calcularAutonomia() < distancia) {
            val cantidad = distancia - vehiculo.calcularAutonomia()
            repostarVehiculo(vehiculo, cantidad)
            vehiculo.realizarViaje(distancia)
        } else {
            vehiculo.realizarViaje(distancia)
        }

        realizarFiligranas(vehiculo, filigranas)
    }

    // Método para repostar combustible en un vehículo
    private fun repostarVehiculo(vehiculo: Vehiculo, cantidad: Float) {
        val combustibleAntes = vehiculo.combustibleActual
        vehiculo.repostar(cantidad)

        if (vehiculo.combustibleActual < 0) {
            vehiculo.combustibleActual = 0f
            vehiculo.kilometrosActuales += (combustibleAntes / Vehiculo.KMPORLITRO).toInt()
        }

        historialAcciones.getOrPut(vehiculo.nombre) { mutableListOf() }
            .add("${vehiculo.nombre} ha repostado.")
    }

    // Método para realizar filigranas con un vehículo
    private fun realizarFiligranas(vehiculo: Vehiculo, filigranas: Int) {
        for (i in 1..filigranas) {
            when (vehiculo) {
                is Automovil -> {
                    vehiculo.realizarDerrape()
                }
                is Motocicleta -> {
                    vehiculo.realizarCaballito()
                }
            }
            historialAcciones.getOrPut(vehiculo.nombre) { mutableListOf() }
                .add("${vehiculo.nombre} ha realizado una filigrana.")
        }
    }

    // Método para actualizar las posiciones de los vehículos
    private fun actualizarPosiciones() {
        posiciones.clear()
        val distancias = participantes.map { Pair(it.nombre, it.kilometrosActuales.roundToInt()) }
        val distanciasOrdenadas = distancias.sortedByDescending { it.second }
        posiciones.addAll(distanciasOrdenadas)
    }

    // Método para determinar al ganador de la carrera
    private fun determinarGanador() {
        for (participante in participantes) {
            if (participante.kilometrosActuales >= distanciaTotal) {
                estadoCarrera = false
                ganador = participante
            }
        }
    }

    // Método para obtener y mostrar los resultados de la carrera
    private fun obtenerResultados() {
        val resultados: List<ResultadoCarrera> = posiciones.mapIndexed { index, (nombre, _) ->
            val vehiculo = participantes.find { it.nombre == nombre }
            val acciones = historialAcciones[nombre] ?: emptyList()

            if (vehiculo != null) {
                ResultadoCarrera(
                    vehiculo = vehiculo,
                    posicion = index + 1, // Sumar 1 para que las posiciones inicien desde 1 en lugar de 0
                    kilometraje = vehiculo.kilometrosActuales,
                    paradasRepostaje = acciones.count { it.contains("repostado") }, // Contar las acciones de repostaje
                    historialAcciones = acciones
                )
            } else {
                println("No se encontró el vehículo con nombre $nombre.")
                null
            }
        }.filterNotNull()

        // Imprimir los resultados
        resultados.forEach {
            println(it)
        }
    }


    //Método para mostrar la clasificación final
    private fun mostrarPosiciones() {
        println("Posiciones finales:")
        posiciones.forEachIndexed { index, (nombre, distancia) ->
            val vehiculo = participantes.find { it.nombre == nombre }
            println("${index + 1}. ${vehiculo?.nombre} - $distancia km")
        }
    }

}

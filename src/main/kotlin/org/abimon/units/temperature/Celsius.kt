package org.abimon.units.temperature

/** Temperature */

data class Celsius(val degrees: Double) {
    constructor(degrees: Long): this(degrees.toDouble())

    fun toFahrenheit(): Fahrenheit = Fahrenheit((degrees * (9.0 / 5.0)) + 32)
    fun toKelvin(): Kelvin = Kelvin(degrees + 273.15)

    override fun toString(): String = "$degrees °C"

    init {
        if(degrees < -273.15)
            throw IllegalArgumentException("$this is an invalid temperature (lower than Absolute Zero at -273.15 °C)")
    }
}
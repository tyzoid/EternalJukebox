package org.abimon.units.temperature

data class Kelvin(val degrees: Double) {
    constructor(degrees: Long): this(degrees.toDouble())

    fun toCelsius(): Celsius = Celsius(degrees - 273.15)
    fun toFahrenheit(): Fahrenheit = Fahrenheit((degrees * (9.0 / 5.0)) - 459.67)

    override fun toString(): String = "$degrees K"
    init {
        if(degrees < 0)
            throw IllegalArgumentException("$this is an invalid temperature (lower than Absolute Zero at 0 K)")
    }
}
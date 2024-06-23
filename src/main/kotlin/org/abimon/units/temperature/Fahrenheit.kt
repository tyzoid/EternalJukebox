package org.abimon.units.temperature

data class Fahrenheit(val degrees: Double) {
    constructor(degrees: Long): this(degrees.toDouble())

    fun toCelsius(): Celsius = Celsius((degrees - 32) * (5.0 / 9.0))
    fun toKelvin(): Kelvin = Kelvin((degrees + 459.67) * (5.0 / 9.0))

    override fun toString(): String = "$degrees °F"

    init {
        if(degrees < -459.67)
            throw IllegalArgumentException("$this is an invalid temperature (lower than Absolute Zero at -459.67 °F)")
    }
}
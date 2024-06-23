package org.abimon.units.mass

import org.abimon.units.energy.Joule

data class Pound(val lb: Double) {
    constructor(lb: Long): this(lb.toDouble())

    fun toJoules(): Joule = toKilograms().toJoules()

    fun toOunces(): Ounce = Ounce(lb * 16)
    fun toKilograms(): Kilogram = Kilogram(lb * 0.45359237)

    override fun toString(): String = "$lb lb"
}
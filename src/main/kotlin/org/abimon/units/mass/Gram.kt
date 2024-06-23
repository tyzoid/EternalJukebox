package org.abimon.units.mass

import org.abimon.units.energy.Joule

/** Mass */

data class Gram(val g: Double) {
    constructor(g: Long): this(g.toDouble())

    fun toJoules(): Joule = toKilograms().toJoules()

    fun toKilograms(): Kilogram = Kilogram(g / 1000.0)
    fun toOunces(): Ounce = Ounce(g * 0.0353)
    fun toPounds(): Pound = Pound(g * 0.0022)

    override fun toString(): String = "$g g"
}
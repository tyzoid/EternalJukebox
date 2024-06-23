package org.abimon.units.energy

import org.abimon.units.UnitConstants
import org.abimon.units.mass.Kilogram
import org.abimon.units.mass.Pound

/** Energy */

data class Joule(val joules: Double) {
    constructor(joules: Long): this(joules.toDouble())

    fun toKilograms(): Kilogram = Kilogram(joules / UnitConstants.SPEED_OF_LIGHT_SQUARED)
    fun toPounds(): Pound = toKilograms().toPounds()

    override fun toString(): String = "$joules J"
}
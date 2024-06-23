package org.abimon.units.mass

import org.abimon.units.UnitConstants
import org.abimon.units.energy.Joule

data class Kilogram(val kg: Double) {
    constructor(kg: Long): this(kg.toDouble())

    fun toJoules(): Joule = Joule(kg * UnitConstants.SPEED_OF_LIGHT_SQUARED)

    fun toOunces(): Ounce = toPounds().toOunces()
    fun toPounds(): Pound = Pound(kg / 0.45359237)

    override fun toString(): String = "$kg kg"
}
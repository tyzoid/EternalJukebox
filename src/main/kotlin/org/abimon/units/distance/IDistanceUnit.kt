package org.abimon.units.distance

import java.text.NumberFormat

interface IDistanceUnit {
    fun toMetre(): Metre
    fun toFeet(): Foot

    override fun toString(): String
    fun format(format: NumberFormat): String
}
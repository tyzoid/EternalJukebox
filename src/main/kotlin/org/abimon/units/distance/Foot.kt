package org.abimon.units.distance

import java.text.NumberFormat

data class Foot(val feet: Double): IDistanceUnit {
    override fun toMetre(): Metre = Metre(feet / 0.3048)
    override fun toFeet(): Foot = this

    override fun format(format: NumberFormat): String = "${format.format(feet)} ft"
    override fun toString(): String = "$feet ft"
}
package org.abimon.units.distance

import java.text.NumberFormat

data class Metre(val metres: Double): IDistanceUnit {
    override fun toMetre(): Metre = this
    override fun toFeet(): Foot = Foot(metres * 0.3048)

    override fun toString(): String = "$metres m"
    override fun format(format: NumberFormat): String = "${format.format(metres)} m"
}
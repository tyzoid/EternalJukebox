package org.abimon.units.distance

/** Distance */

data class Millimetre(val mm: Double) {
    constructor(mm: Long): this(mm.toDouble())

    fun toCentimetres(): Centimetre = Centimetre(mm / 1000)

    override fun toString(): String = "$mm mm"
}
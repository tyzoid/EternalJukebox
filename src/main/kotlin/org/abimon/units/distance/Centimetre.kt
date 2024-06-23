package org.abimon.units.distance

data class Centimetre(val cm: Double) {
    constructor(cm: Long): this(cm.toDouble())

    override fun toString(): String = "$cm cm"
}
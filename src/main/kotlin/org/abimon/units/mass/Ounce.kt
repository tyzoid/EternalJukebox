package org.abimon.units.mass

data class Ounce(val oz: Double) {
    constructor(oz: Long): this(oz.toDouble())

    fun toGrams(): Gram = Gram(oz / 0.0353)

    override fun toString(): String = "$oz oz"
}
package org.abimon.units.data

import java.text.NumberFormat

data class Petabyte(val petabytes: Double): IDataUnit {
    constructor(petabytes: Long): this(petabytes.toDouble())

    override fun toBytes(): ByteUnit = ByteUnit(petabytes * 1000 * 1000 * 1000 * 1000 * 1000)
    override fun toKilobytes(): Kilobyte = Kilobyte(petabytes * 1000 * 1000 * 1000 * 1000)
    override fun toMegabytes(): Megabyte = Megabyte(petabytes * 1000 * 1000 * 1000)
    override fun toGigabytes(): Gigabyte = Gigabyte(petabytes * 1000 * 1000)
    override fun toTerabytes(): Terabyte = Terabyte(petabytes * 1000)
    override fun toPetabytes(): Petabyte = this
    override fun toExabytes(): Exabyte = Exabyte(petabytes / 1000.0)

    override fun toString(): String = "$petabytes PB"
    override fun format(format: NumberFormat): String = "${format.format(petabytes)} PB"
}
package org.abimon.units.data

import java.text.NumberFormat

data class Exabyte(val exabytes: Double): IDataUnit {
    constructor(exabytes: Long): this(exabytes.toDouble())

    override fun toBytes(): ByteUnit = ByteUnit(exabytes * 1000 * 1000 * 1000 * 1000 * 1000 * 1000)
    override fun toKilobytes(): Kilobyte = Kilobyte(exabytes * 1000 * 1000 * 1000 * 1000 * 1000)
    override fun toMegabytes(): Megabyte = Megabyte(exabytes * 1000 * 1000 * 1000 * 1000)
    override fun toGigabytes(): Gigabyte = Gigabyte(exabytes * 1000 * 1000 * 1000)
    override fun toTerabytes(): Terabyte = Terabyte(exabytes * 1000 * 1000)
    override fun toPetabytes(): Petabyte = Petabyte(exabytes * 1000)
    override fun toExabytes(): Exabyte = this

    override fun toString(): String = "$exabytes EB"
    override fun format(format: NumberFormat): String = "${format.format(exabytes)} EB"
}
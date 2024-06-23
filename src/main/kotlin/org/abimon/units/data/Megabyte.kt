package org.abimon.units.data

import java.text.NumberFormat

data class Megabyte(val megabytes: Double): IDataUnit {
    constructor(megabytes: Long): this(megabytes.toDouble())

    override fun toBytes(): ByteUnit = ByteUnit(megabytes * 1000 * 1000)
    override fun toKilobytes(): Kilobyte = Kilobyte(megabytes * 1000)
    override fun toMegabytes(): Megabyte = this
    override fun toGigabytes(): Gigabyte = Gigabyte(megabytes / 1000.0)
    override fun toTerabytes(): Terabyte = Terabyte(megabytes / 1000.0 / 1000.0)
    override fun toPetabytes(): Petabyte = Petabyte(megabytes / 1000.0 / 1000.0 / 1000.0)
    override fun toExabytes(): Exabyte = Exabyte(megabytes / 1000.0 / 1000.0 / 1000.0 / 1000.0)

    override fun toString(): String = "$megabytes MB"
    override fun format(format: NumberFormat): String = "${format.format(megabytes)} MB"
}
package org.abimon.units.data

import java.text.NumberFormat

data class Gigabyte(val gigabytes: Double): IDataUnit {
    constructor(gigabytes: Long): this(gigabytes.toDouble())

    override fun toBytes(): ByteUnit = ByteUnit(gigabytes * 1000 * 1000 * 1000)
    override fun toKilobytes(): Kilobyte = Kilobyte(gigabytes * 1000 * 1000)
    override fun toMegabytes(): Megabyte = Megabyte(gigabytes * 1000)
    override fun toGigabytes(): Gigabyte = this
    override fun toTerabytes(): Terabyte = Terabyte(gigabytes / 1000.0)
    override fun toPetabytes(): Petabyte = Petabyte(gigabytes / 1000.0 / 1000.0)
    override fun toExabytes(): Exabyte = Exabyte(gigabytes / 1000.0 / 1000.0 / 1000.0)

    override fun toString(): String = "$gigabytes GB"
    override fun format(format: NumberFormat): String = "${format.format(gigabytes)} GB"
}
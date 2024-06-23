package org.abimon.units.data

import java.text.NumberFormat

data class ByteUnit(val bytes: Double): IDataUnit {
    constructor(bytes: Long): this(bytes.toDouble())

    override fun toBytes(): ByteUnit = this
    override fun toKilobytes(): Kilobyte = Kilobyte(bytes / 1000.0)
    override fun toMegabytes(): Megabyte = Megabyte(bytes / 1000.0 / 1000.0)
    override fun toGigabytes(): Gigabyte = Gigabyte(bytes / 1000.0 / 1000.0 / 1000.0)
    override fun toTerabytes(): Terabyte = Terabyte(bytes / 1000.0 / 1000.0 / 1000.0 / 1000.0)
    override fun toPetabytes(): Petabyte = Petabyte(bytes / 1000.0 / 1000.0 / 1000.0 / 1000.0 / 1000.0)
    override fun toExabytes(): Exabyte = Exabyte(bytes / 1000.0 /1000.0 /1000.0 /1000.0 /1000.0 /1000.0)

    override fun toString(): String = "$bytes B"
    override fun format(format: NumberFormat): String = "${format.format(bytes)} B"
}
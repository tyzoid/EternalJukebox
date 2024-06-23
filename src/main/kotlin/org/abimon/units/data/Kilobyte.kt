package org.abimon.units.data

import java.text.NumberFormat

data class Kilobyte(val kilobytes: Double): IDataUnit {
    constructor(kilobytes: Long): this(kilobytes.toDouble())

    override fun toBytes(): ByteUnit = ByteUnit(kilobytes * 1000)
    override fun toKilobytes(): Kilobyte = this
    override fun toMegabytes(): Megabyte = Megabyte(kilobytes / 1000.0)
    override fun toGigabytes(): Gigabyte = Gigabyte(kilobytes / 1000.0 / 1000.0)
    override fun toTerabytes(): Terabyte = Terabyte(kilobytes / 1000.0 / 1000.0 / 1000.0)
    override fun toPetabytes(): Petabyte = Petabyte(kilobytes / 1000.0 / 1000.0 / 1000.0 / 1000.0)
    override fun toExabytes(): Exabyte = Exabyte(kilobytes / 1000.0 / 1000.0 / 1000.0 / 1000.0 / 1000.0)

    override fun toString(): String = "$kilobytes KB"
    override fun format(format: NumberFormat): String = "${format.format(kilobytes)} KB"
}
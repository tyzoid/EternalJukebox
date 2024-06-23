package org.abimon.units.data

import java.text.NumberFormat

data class Terabyte(val terabytes: Double): IDataUnit {
    constructor(terabytes: Long): this(terabytes.toDouble())

    override fun toBytes(): ByteUnit = ByteUnit(terabytes * 1000 * 1000 * 1000 * 1000)
    override fun toKilobytes(): Kilobyte = Kilobyte(terabytes * 1000 * 1000 * 1000)
    override fun toMegabytes(): Megabyte = Megabyte(terabytes * 1000 * 1000)
    override fun toGigabytes(): Gigabyte = Gigabyte(terabytes * 1000)
    override fun toTerabytes(): Terabyte = this
    override fun toPetabytes(): Petabyte = Petabyte(terabytes / 1000.0)
    override fun toExabytes(): Exabyte = Exabyte(terabytes / 1000.0 / 1000.0)

    override fun toString(): String = "$terabytes TB"
    override fun format(format: NumberFormat): String = "${format.format(terabytes)} TB"
}
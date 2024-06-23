package org.abimon.units.data

import java.text.NumberFormat

interface IDataUnit {
    fun toBytes(): ByteUnit
    fun toKilobytes(): Kilobyte
    fun toMegabytes(): Megabyte
    fun toGigabytes(): Gigabyte
    fun toTerabytes(): Terabyte
    fun toPetabytes(): Petabyte
    fun toExabytes(): Exabyte

    override fun toString(): String
    fun format(format: NumberFormat): String
}
package org.abimon.visi.lang

fun Runtime.usedMemory(): Long = (totalMemory() - freeMemory())

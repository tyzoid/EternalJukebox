package org.abimon.visi.io

import java.io.*

fun InputStream.readChunked(processChunk: (ByteArray) -> Unit): Int {
    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
    var total = 0
    var emptyReadCounter = 0

    while (true) {
        val bytesRead = read(buffer)
        if (bytesRead == -1) break

        if (bytesRead == 0) {
            emptyReadCounter++
            if (emptyReadCounter > 3) break
        }

        processChunk(buffer.copyOfRange(0, bytesRead))
        total += bytesRead
    }

    close()
    return total
}

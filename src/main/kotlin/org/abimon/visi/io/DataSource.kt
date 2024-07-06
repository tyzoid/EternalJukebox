package org.abimon.visi.io

import java.io.*
import java.net.HttpURLConnection
import java.net.URL

interface DataSource {
    /**
     * Get an input stream associated with this data source.
     */
    val inputStream: InputStream
    val data: ByteArray
    val size: Long
    
    fun <T> use(action: (InputStream) -> T): T = inputStream.use(action)
}

class FileDataSource(val file: File) : DataSource {

    override val data: ByteArray
        get() = file.readBytes()

    override val inputStream: InputStream
        get() = FileInputStream(file)

    override val size: Long
        get() = file.length()
}

class HTTPDataSource(val url: URL, val userAgent: String) : DataSource {
    constructor(url: URL) : this(url, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.11; rv:44.0) Gecko/20100101 Firefox/44.0")

    override val data: ByteArray
        get() = use { stream -> stream.readBytes() }

    override val inputStream: InputStream
        get() {
            val http = url.openConnection() as HttpURLConnection
            http.requestMethod = "GET"
            http.setRequestProperty("User-Agent", userAgent)
            return if (http.responseCode < 400) http.inputStream else http.errorStream
        }

    override val size: Long
        get() = use { it.available().toLong() }
}

class ByteArrayDataSource(override val data: ByteArray): DataSource {
    override val inputStream: InputStream
        get() = ByteArrayInputStream(data)
    override val size: Long = data.size.toLong()
}

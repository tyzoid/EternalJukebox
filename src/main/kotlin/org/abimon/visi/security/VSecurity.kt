package org.abimon.visi.security

import org.abimon.visi.io.readChunked
import java.io.InputStream
import java.math.BigInteger
import java.security.MessageDigest

/** ***Do not use for things like passwords*** */
fun ByteArray.md5Hash(): String {
    val md = MessageDigest.getInstance("MD5")
    val hashBytes = md.digest(this)
    return String.format("%032x", BigInteger(1, hashBytes))
}

/** **Do not use for things like passwords, or situations where the data needs to be blanked out** */
fun String.md5Hash(): String = toByteArray(Charsets.UTF_8).md5Hash()

/** ***Do not use for things like passwords*** */
fun InputStream.sha512Hash(): String {
    val md = MessageDigest.getInstance("SHA-512")
    readChunked { md.update(it) }
    val hashBytes = md.digest()
    return String.format("%032x", BigInteger(1, hashBytes))
}

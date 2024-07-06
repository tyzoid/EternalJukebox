package org.abimon.eternalJukebox

import com.github.kittinunf.fuel.core.Request

fun Request.bearer(token: String): Request = header("Authorization" to "Bearer $token")

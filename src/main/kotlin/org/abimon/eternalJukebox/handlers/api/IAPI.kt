package org.abimon.eternalJukebox.handlers.api

import io.vertx.ext.web.Router

interface IAPI {
    /**
     * Path to mount this API on. Must start with `/`
     */
    val mountPath: String

    fun setup(router: Router)
}

package org.abimon.eternalJukebox.handlers

import io.vertx.ext.web.Router
import io.vertx.ext.web.handler.StaticHandler
import org.abimon.eternalJukebox.EternalJukebox

object StaticResources {
    fun setup(router: Router) {
        router.get().handler(StaticHandler.create(EternalJukebox.config.webRoot))
    }
}

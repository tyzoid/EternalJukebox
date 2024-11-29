package org.abimon.eternalJukebox.handlers.api

import io.vertx.core.json.JsonArray
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import org.abimon.eternalJukebox.*
import org.abimon.eternalJukebox.objects.EnumStorageType
import org.abimon.eternalJukebox.objects.JukeboxInfo
import org.slf4j.Logger
import org.slf4j.LoggerFactory

object AnalysisAPI : IAPI {
    override val mountPath: String = "/analysis"
    private val logger: Logger = LoggerFactory.getLogger("AnalysisApi")

    override fun setup(router: Router) {
        router.get("/analyse/:id").suspendingHandler(this::analyseSpotify)
        router.get("/search").suspendingHandler(AnalysisAPI::searchSpotify)
    }

    private suspend fun analyseSpotify(context: RoutingContext) {
        if (EternalJukebox.storage.shouldStore(EnumStorageType.ANALYSIS)) {
            val id = context.pathParam("id")
            if (EternalJukebox.storage.isStored("$id.json", EnumStorageType.ANALYSIS)) {
                if (EternalJukebox.storage.provide("$id.json", EnumStorageType.ANALYSIS, context, context.clientInfo))
                    return

                val data = EternalJukebox.storage.provide("$id.json", EnumStorageType.ANALYSIS, context.clientInfo)
                if (data != null)
                    return context.response().putHeader("X-Client-UID", context.clientInfo.userUID).end(data, "application/json")
            }

            context.response().putHeader("X-Client-UID", context.clientInfo.userUID).setStatusCode(400).end(jsonObjectOf(
                    "error" to "It is not possible to get new analysis data from Spotify. Please check the subreddit linked under 'Social' in the navigation bar for more information.",
                    "client_uid" to context.clientInfo.userUID
            ))
        } else {
            context.response().putHeader("X-Client-UID", context.clientInfo.userUID).setStatusCode(501).end(jsonObjectOf(
                    "error" to "Configured storage method does not support storing ANALYSIS",
                    "client_uid" to context.clientInfo.userUID
            ))
        }
    }

    private suspend fun searchSpotify(context: RoutingContext) {
        val query = context.request().getParam("query") ?: "Never Gonna Give You Up"
        val results = EternalJukebox.spotify.search(query, context.clientInfo)

        context.response().end(JsonArray(results.map(JukeboxInfo::toJsonObject)))
    }

    init {
        logger.info("Initialised Analysis Api")
    }
}

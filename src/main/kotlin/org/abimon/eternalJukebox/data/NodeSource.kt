package org.abimon.eternalJukebox.data

import com.github.kittinunf.fuel.Fuel
import org.abimon.visi.io.DataSource
import org.abimon.visi.io.HTTPDataSource
import java.net.URL
import java.util.*

abstract class NodeSource {
    abstract val nodeHosts: Array<String>

    private val rng: Random = Random()

    fun provide(path: String): DataSource? {
        val starting = rng.nextInt(nodeHosts.size)

        for (i in nodeHosts.indices) {
            val host = nodeHosts[(starting + i) % nodeHosts.size]
            val (_, healthy) = Fuel.get("$host/api/node/healthy").timeout(5 * 1000).response()
            if (healthy.statusCode == 200)
                return HTTPDataSource(URL("$host/api/node/$path"))
        }

        return null
    }
}

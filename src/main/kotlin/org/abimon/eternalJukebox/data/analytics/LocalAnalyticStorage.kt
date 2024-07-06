package org.abimon.eternalJukebox.data.analytics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.abimon.eternalJukebox.EternalJukebox
import org.abimon.eternalJukebox.guaranteeDelete
import org.abimon.eternalJukebox.objects.EnumAnalyticType
import org.abimon.eternalJukebox.objects.EnumStorageType
import org.abimon.eternalJukebox.simpleClassName
import org.abimon.eternalJukebox.useThenDelete
import org.abimon.visi.io.FileDataSource
import java.io.File
import java.io.FileOutputStream
import java.io.PrintStream
import java.util.*

object LocalAnalyticStorage : IAnalyticsStorage {
    private val storageLocations: Map<EnumAnalyticType<*>, File> = EnumAnalyticType.VALUES.associateWith { type ->
        File(
            EternalJukebox.config.analyticsStorageOptions["${type::class.simpleClassName.uppercase(Locale.getDefault())}_FILE"] as? String
                ?: "analytics-${type::class.simpleClassName.lowercase(Locale.getDefault())}.log"
        )
    }
    private val storageStreams: MutableMap<EnumAnalyticType<*>, PrintStream> = HashMap()

    override fun <T : Any> store(now: Long, data: T, type: EnumAnalyticType<T>): Boolean {
        if(!storageStreams.containsKey(type))
            storageStreams[type] = PrintStream(FileOutputStream(storageLocations[type] ?: return false), true)
        storageStreams[type]?.println("$now|$data") ?: return false
        return true
    }

    init {
        GlobalScope.launch {
            storageLocations.forEach { (type, log) ->
                if (log.exists()) {
                    if (EternalJukebox.storage.shouldStore(EnumStorageType.LOG)) {
                        log.useThenDelete { file ->
                            EternalJukebox.storage.store(
                                "Analysis-${type::class.simpleClassName}-${UUID.randomUUID()}.log",
                                EnumStorageType.LOG,
                                FileDataSource(file),
                                "text/plain",
                                null
                            )
                        }
                    } else {
                        log.guaranteeDelete()
                    }
                }

                log.createNewFile()
            }
        }
    }
}

package org.abimon.eternalJukebox.data.analytics

import org.abimon.eternalJukebox.objects.EnumAnalyticType

interface IAnalyticsStorage {

    /**
     * Store [data] as type [type]
     * Returns true if successfully stored; false otherwise
     */
    fun <T: Any> store(now: Long, data: T, type: EnumAnalyticType<T>): Boolean

    @Suppress("UNCHECKED_CAST")
    fun storeMultiple(now: Long, data: List<Pair<EnumAnalyticType<*>, Any>>) = data.forEach { (type, data) -> store(now, data, type as EnumAnalyticType<Any>) }
}

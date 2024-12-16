package org.abimon.eternalJukebox.data.analytics

import io.vertx.ext.web.Router
import org.abimon.eternalJukebox.objects.EnumAnalyticType
import org.abimon.eternalJukebox.schedule
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

object HTTPAnalyticsProvider: IAnalyticsProvider {
    private val scheduler: ScheduledExecutorService = Executors.newSingleThreadScheduledExecutor()

    private val requests: AtomicInteger = AtomicInteger(0)
    private val hourlyRequests: AtomicInteger = AtomicInteger(0)

    private val PROVIDING = arrayOf(
            EnumAnalyticType.SESSION_REQUESTS, EnumAnalyticType.HOURLY_REQUESTS
    )

    override fun shouldProvide(type: EnumAnalyticType<*>): Boolean = type in PROVIDING

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> provide(now: Long, type: EnumAnalyticType<T>): T? {
        return when (type) {
            is EnumAnalyticType.SESSION_REQUESTS -> requests.get()
            is EnumAnalyticType.HOURLY_REQUESTS -> hourlyRequests.get()

            else -> return null
        } as? T
    }

    override fun setupWebAnalytics(router: Router) {
        router.route().handler { context ->
            requests.incrementAndGet()
            hourlyRequests.incrementAndGet()

            scheduler.schedule(1, TimeUnit.HOURS) { hourlyRequests.decrementAndGet() }

            context.next()
        }
    }
}

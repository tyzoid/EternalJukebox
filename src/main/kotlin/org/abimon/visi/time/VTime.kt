package org.abimon.visi.time

import java.time.*

fun LocalDateTime.timeDifference(): String {
    val period = Period.between(this.toLocalDate(), LocalDate.now())
    val duration = Duration.between(this.toLocalTime(), LocalTime.now())

    val components = listOf(
        period.years.toLong() to "year",
        period.months.toLong() to "month",
        period.days.toLong() to "day",
        duration.toHours() % 24 to "hour",
        duration.toMinutes() % 60 to "minute",
        duration.seconds % 60 to "second"
    ).mapNotNull { (value, singular) ->
        value.takeIf { it > 0 }?.let { if (it == 1L) "$it $singular" else "$it ${singular}s" }
    }

    return when (components.size) {
        0 -> ""
        1 -> components[0]
        else -> components.dropLast(1).joinToString(", ") + " and ${components.last()}"
    }
}

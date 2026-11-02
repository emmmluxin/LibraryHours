package com.anyuan.library.data

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class LibraryStatus(
    val closedAllDay: Boolean,
    val open: Boolean,
    val openTime: LocalTime,
    val closeTime: LocalTime,
    val nextChange: LocalDateTime,
    val nextChangeLabel: String,
    val statusLine: String,
)

object LibraryRepository {

    fun openTime(month: Int): LocalTime = LocalTime.of(8, 30)

    fun closeTime(month: Int): LocalTime =
        if (month in 5..9) LocalTime.of(20, 30) else LocalTime.of(19, 30)

    fun isSummerSeason(month: Int): Boolean = month in 5..9

    fun nextOpenDay(from: LocalDate): LocalDate {
        var d = from.plusDays(1)
        while (d.dayOfWeek == DayOfWeek.MONDAY) d = d.plusDays(1)
        return d
    }

    fun statusAt(now: LocalDateTime): LibraryStatus {
        val open = openTime(now.monthValue)
        val close = closeTime(now.monthValue)
        val isMonday = now.dayOfWeek == DayOfWeek.MONDAY

        return when {
            isMonday -> LibraryStatus(
                closedAllDay = true,
                open = false,
                openTime = open,
                closeTime = close,
                nextChange = nextOpenDay(now.toLocalDate()).atTime(open),
                nextChangeLabel = "距开馆",
                statusLine = "今日闭馆 · 周一",
            )
            now.toLocalTime() < open -> LibraryStatus(
                closedAllDay = false,
                open = false,
                openTime = open,
                closeTime = close,
                nextChange = now.toLocalDate().atTime(open),
                nextChangeLabel = "距开馆",
                statusLine = "未开馆",
            )
            now.toLocalTime() >= close -> LibraryStatus(
                closedAllDay = false,
                open = false,
                openTime = open,
                closeTime = close,
                nextChange = nextOpenDay(now.toLocalDate()).atTime(open),
                nextChangeLabel = "距开馆",
                statusLine = "已闭馆",
            )
            else -> LibraryStatus(
                closedAllDay = false,
                open = true,
                openTime = open,
                closeTime = close,
                nextChange = now.toLocalDate().atTime(close),
                nextChangeLabel = "距闭馆",
                statusLine = "开馆中",
            )
        }
    }
}

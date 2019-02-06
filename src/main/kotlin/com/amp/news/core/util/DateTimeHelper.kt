@file:JvmName("DateTimeHelper")

package com.amp.news.core.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Date
import java.util.concurrent.CopyOnWriteArrayList

private val DATE_FORMATTERS = CopyOnWriteArrayList<DateTimeFormatter>().apply {
  add(DateTimeFormatter.ISO_INSTANT.withZone(ZoneOffset.UTC))
  add(DateTimeFormatter.ISO_LOCAL_DATE_TIME.withZone(ZoneOffset.UTC))
  add(DateTimeFormatter.ISO_LOCAL_DATE.withZone(ZoneOffset.UTC))
}

fun LocalDateTime.toISOString() = DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(this)!!

fun Date.toISOString() = this.toLocalDateTime().toISOString()

fun Date.toLocalDateTime() = this.toInstant().atZone(ZoneOffset.UTC).toLocalDateTime()!!

fun LocalDateTime.toDate(): Date = Date.from(this.atZone(ZoneOffset.UTC).toInstant())

fun parseDate(date: String): LocalDateTime? {
  for (formatter in DATE_FORMATTERS) {
    try {
      // equals ISO_LOCAL_DATE
      return if (formatter == DATE_FORMATTERS[2]) {
        LocalDate.parse(date, formatter).atStartOfDay()
      } else {
        LocalDateTime.parse(date, formatter)
      }
    } catch (ignored: DateTimeParseException) {
    }
  }

  return null
}

fun now() = LocalDateTime.now().toDate()

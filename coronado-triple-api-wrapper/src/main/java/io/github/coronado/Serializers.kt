package io.github.coronado.baseobjects

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZonedDateTime

object BigDecimalAdapter {
    @FromJson
    fun fromJson(string: String) = BigDecimal(string)

    @ToJson
    fun toJson(value: BigDecimal) = value.toString()
}

object ZonedDateTimeAdapter {
    @FromJson
    fun fromJson(string: String) = ZonedDateTime.parse(string)

    @ToJson
    fun toJson(value: ZonedDateTime) = value.toString()
}

object LocalDateAdapter {
    @FromJson
    fun fromJson(string: String) = LocalDate.parse(string)

    @ToJson
    fun toJson(value: LocalDate) = value.toString()
}

object LocalTimeAdapter {
    @FromJson
    fun fromJson(string: String) = LocalTime.parse(string)

    @ToJson
    fun toJson(value: LocalTime) = value.toString()
}

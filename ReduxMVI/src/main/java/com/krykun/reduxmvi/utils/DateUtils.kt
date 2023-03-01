package com.krykun.reduxmvi.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object DateUtils {

    fun dateFromTimestamp(timestamp: Long, dateFormat: String): String {
        val formatter = SimpleDateFormat(dateFormat, Locale.getDefault())
        return formatter.format(Date(timestamp))
    }

    private fun utcServerToDate(utcServerTime: String, dateFormat: UtcDateFormat): Date? {
        val utcFormat: DateFormat = SimpleDateFormat(dateFormat.format)
        utcFormat.timeZone = TimeZone.getTimeZone("UTC")
        return utcFormat.parse(utcServerTime)
    }

    fun utcServerToTime(utcServerTime: String, dateFormat: UtcDateFormat = UtcDateFormat.UTC_FORMAT): Long? {
        return utcServerToDate(utcServerTime, dateFormat)?.time
    }

    fun utcServerToLocalUITime(utcServerTime: String, dateFormat: UtcDateFormat = UtcDateFormat.UTC_FORMAT): String {
        val date = utcServerToDate(utcServerTime, dateFormat)
        val localTime: DateFormat = SimpleDateFormat("MM/dd/yyyy hh:mm a", Locale.getDefault())
        return if (date != null) {
            localTime.format(date)
        } else {
            ""
        }
    }

    enum class UtcDateFormat(val format: String) {
        UTC_FORMAT("yyyy-MM-dd'T'HH:mm'Z'"),
        UTC_SERVER_FORMAT("yyyy-MM-dd'T'HH:mm:ss'Z'")
    }
}

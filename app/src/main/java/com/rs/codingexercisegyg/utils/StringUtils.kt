package com.rs.codingexercisegyg.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

@Throws(ParseException::class)
fun convertStringDateFormat(date: String): String {
    val dateToFormat = date.split('+')[0] + "Z"
    val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US)
    format.timeZone = TimeZone.getTimeZone("UTC")
    return format.parse(dateToFormat)?.toString() ?: ""
}
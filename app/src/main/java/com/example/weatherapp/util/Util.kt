package com.example.weatherapp.util

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


fun milliToDate(value: Int): String? {
    val formatter: DateFormat = SimpleDateFormat("HH:mm")
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = value.toLong()
    return formatter.format(calendar.time)
}
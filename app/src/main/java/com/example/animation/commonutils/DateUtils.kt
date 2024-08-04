package com.example.animation.commonutils

import android.util.Log
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


fun getDateAndTime(dateInput: String?): String {
    try {
        val localDate = getUtcTime(dateInput)
        val date: Date? =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(localDate)
        date?.let {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            return SimpleDateFormat(
                "dd-MMM | hh:mm a",
                Locale.getDefault()
            ).format(calendar.time)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

fun getDateOnly(dateInput: String?): String {
    try {
        val localDate = getUtcTime(dateInput)
        val date: Date? =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(localDate)
        date?.let {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            return SimpleDateFormat(
                "yyyy-MM-dd", Locale.getDefault()
            ).format(calendar.time)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

fun getYearOnly(dateInput: String?): String {
    try {
        val localDate = getUtcTime(dateInput)
        val date: Date? =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(localDate)
        date?.let {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            return SimpleDateFormat(
                "yyyy", Locale.getDefault()
            ).format(calendar.time)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

fun getMonthAndYear(dateInput: String?): String {
    try {
        val localDate = getUtcTime(dateInput)
        val date: Date? =
            SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).parse(localDate)
        date?.let {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            return SimpleDateFormat(
                "yyyy-MMM", Locale.getDefault()
            ).format(calendar.time)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

fun getMonthOnly(dateInput: String?): String {
    try {
        val date: Date? =
            dateInput?.let { SimpleDateFormat("yyyy-MMM", Locale.getDefault()).parse(it) }
        date?.let {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = date
            return SimpleDateFormat(
                "MMM", Locale.getDefault()
            ).format(calendar.time)
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

fun getUtcTime(date: String?): String {
    try {
        date?.let {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH)
            dateFormat.timeZone = TimeZone.getTimeZone("UTC")
            val utcDate = dateFormat.parse(date)
            utcDate?.let {
                dateFormat.timeZone = TimeZone.getDefault()
                return dateFormat.format(utcDate)
            }
        }
    } catch (exp: java.lang.Exception) {
        Log.e("TAG", "getLocalTime: ", exp)
    }
    return ""
}
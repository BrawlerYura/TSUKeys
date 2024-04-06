package com.example.teamdevelopment.domain.useCases

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class FormatDateUseCase {

    fun formatDateFromApi(date: String): String {
        val parts = date.split("-")
        val year = parts[0]
        val month = parts[1]
        val day = parts[2]
        return "${day.substring(startIndex = 0, endIndex = 2)}.$month.$year"
    }

    @SuppressLint("SimpleDateFormat")
    fun formatDateToTextField(selectedDateMillis: Long?): String {
        if (selectedDateMillis == null) {
            return ""
        }

        val dateFormat = SimpleDateFormat("dd.MM.yyyy")
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")

        val date = Date(selectedDateMillis)
        return dateFormat.format(date)
    }

    fun formatDateToApi(date: String): String {
        val parts = date.split(".")
        val day = parts[0]
        val month = parts[1]
        val year = parts[2]
        return "$year-$month-${day}T08:12:28.534Z"
    }
}
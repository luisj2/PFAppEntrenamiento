package com.example.fithealth.Model.Utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


object DateUtils {
    fun dateToHourAndMinutes(date: LocalDateTime): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        return date.format(formatter)
    }
}
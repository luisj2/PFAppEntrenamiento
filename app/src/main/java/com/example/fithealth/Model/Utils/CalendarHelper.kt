package com.example.fithealth.Model.Utils

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object CalendarHelper {

    private var selectedDate: LocalDate = LocalDate.now()

    fun getDaysInMonthArray(): List<String> {
        val yearMonth = YearMonth.from(selectedDate)
        val monthLength = yearMonth.lengthOfMonth()
        val monthStartWeekday = yearMonth.atDay(1).dayOfWeek.value

        // Tamaño total calendario(6 semanas * 7 días)
        val totalListLength = 6 * 7

        return List<String>(totalListLength) { i ->
            val dayOfMonth = (i + 1) - monthStartWeekday
            if (dayOfMonth in 0..<monthLength) (dayOfMonth + 1).toString() else ""
        }
    }

    fun monthYearFromSelectedDate(): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return formatter.format(selectedDate).uppercase()
    }

    fun plusMonthSelectedDate() {
        selectedDate = selectedDate.plusMonths(1)
    }

    fun minusMonthSelectedDate() {
        selectedDate = selectedDate.minusMonths(1)
    }

    fun isCurrentMonth(): Boolean {
        val currentDate = LocalDate.now()
        return currentDate.month == selectedDate.month && currentDate.year == selectedDate.year
    }

    fun getTodayDay() : Int = LocalDate.now().dayOfMonth
}
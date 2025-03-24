package com.example.fithealth.Model.Utils

import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

object CalendarHelper {

    var selectedDate: LocalDate = LocalDate.now()

    fun getDaysInMonthArray(): List<String> {
        val yearMonth = YearMonth.from(selectedDate)
        val monthLength = yearMonth.lengthOfMonth()
        val monthStartWeekday = yearMonth.atDay(1).dayOfWeek.value

        val calendarMonthWeeksRows = 6
        val weekDays = 7

        val totalListLength = calendarMonthWeeksRows * weekDays

        return List<String>(totalListLength) { i ->
            val dayOfMonth = (i + 1) - monthStartWeekday
            if (dayOfMonth in 0..<monthLength) (dayOfMonth + 1).toString() else ""
        }
    }

    fun monthYearFromSelectedDate(): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy")
        return formatter.format(selectedDate).uppercase()
    }

    fun getSelectedDateToDayMonthYear(): String {
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return selectedDate.format(formatter)
    }

    fun getSelectedDateInDayMonthYear(): LocalDate = LocalDate.of(
        selectedDate.year, selectedDate.month,
        selectedDate.dayOfMonth
    )

    fun plusDaySelectedDate() {
        selectedDate = selectedDate.plusDays(1)
    }

    fun minusDaySelectedDate() {
        selectedDate = selectedDate.minusDays(1)
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

    fun selectedDateToCurrentDate() {
        selectedDate = LocalDate.now()
    }

    //fun getSelectedDateMonth() :

    fun getTodayDay(): Int = LocalDate.now().dayOfMonth


}
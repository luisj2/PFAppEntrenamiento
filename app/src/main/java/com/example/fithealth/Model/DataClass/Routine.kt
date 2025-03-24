package com.example.fithealth.Model.DataClass

data class Routine(
    val routineName: String,
    val routineCategory: String,
    val routineId: String = generateId(routineName, routineCategory),
    val minutesDuration: Int,
    val exerciseList: List<Exercise?>
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherRoutine = other as? Routine ?: return false
        return otherRoutine.routineName == routineName && otherRoutine.routineCategory == routineCategory
    }
}

private fun generateId(routineName: String, routineCategory: String): String {
    val sanitizedRoutineName = routineName.replace(" ", "_")
    val sanitizedCategory = routineCategory.replace(" ", "_")
    val timeSuffix = System.currentTimeMillis().toString().takeLast(4)
    return "${sanitizedRoutineName}_${sanitizedCategory}_$timeSuffix"
}

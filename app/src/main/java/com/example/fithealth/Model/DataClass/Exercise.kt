package com.example.fithealth.Model.DataClass

data class Exercise(
    var exerciseId: String = "",
    var exerciseName: String = "",
    var exerciseType: String = ""
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        val otherExercise = other as? Exercise ?: return false
        return otherExercise.exerciseName == exerciseName && otherExercise.exerciseType == exerciseType
    }


}


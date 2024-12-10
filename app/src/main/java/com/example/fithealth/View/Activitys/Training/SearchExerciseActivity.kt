package com.example.fithealth.View.Activitys.Training

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fithealth.databinding.ActivitySearchExerciseBinding

class SearchExerciseActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchExerciseBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchExerciseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        TODO("Not yet implemented")
    }


}
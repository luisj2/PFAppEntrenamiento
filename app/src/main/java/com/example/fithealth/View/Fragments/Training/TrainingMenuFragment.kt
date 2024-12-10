package com.example.fithealth.View.Fragments.Training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fithealth.View.OtherAdapters.ViewPagerAdapter
import com.example.fithealth.databinding.FragmentTrainingMenuBinding
import com.google.android.material.tabs.TabLayoutMediator

class TrainingMenuFragment : Fragment() {

    private var _binding: FragmentTrainingMenuBinding? = null
    private val binding: FragmentTrainingMenuBinding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTrainingMenuBinding.inflate(inflater)

        setupUI()

        return binding.root
    }

    private fun setupUI() {
        setupNavegation()
    }

    private fun setupNavegation() {
        setupViewPager()
        setupTabLayout()
    }

    private fun setupViewPager() {
        val fragmentList = listOf(TrainingFragment(), CreateExerciseFragment())
        binding.apply {
            viewPagerTraining.adapter = ViewPagerAdapter(requireActivity(), fragmentList)
        }
    }

    private fun setupTabLayout() {
        val tabTitles = listOf("Entrenamiento", "Crear Ejercicio")
        binding.apply {
            TabLayoutMediator(tabLayoutTraining, viewPagerTraining) { tab, position ->
                tab.text = tabTitles.getOrNull(position) ?: "Tab ${position + 1}"
            }.attach()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

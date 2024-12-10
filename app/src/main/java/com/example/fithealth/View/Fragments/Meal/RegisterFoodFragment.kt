package com.example.fithealth.View.Fragments.Meal

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.fithealth.databinding.FragmentRegisterFoodBinding

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFoodFragment : Fragment() {


    private var _binding: FragmentRegisterFoodBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterFoodBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
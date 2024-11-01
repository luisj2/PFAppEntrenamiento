package com.example.fithealth.View.Fragments.Social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.databinding.FragmentSocialMenuBinding

class SocialMenuFragment : Fragment() {

    private var _binding: FragmentSocialMenuBinding? = null
    private val binding: FragmentSocialMenuBinding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSocialMenuBinding.inflate(inflater, container, false)

        fragmentNavegation()
        return binding.root
    }

    private fun fragmentNavegation() {
        val navHostFragment = getFragmentContainer() as? NavHostFragment
        navHostFragment?.let {
            binding.navegationView.setupWithNavController(it.navController)
        } ?: toast("Ha habido un error en la navegacion")
    }

    private fun getFragmentContainer(): Fragment? =
        requireActivity().supportFragmentManager.findFragmentById(binding.containerViewSocial.id)

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
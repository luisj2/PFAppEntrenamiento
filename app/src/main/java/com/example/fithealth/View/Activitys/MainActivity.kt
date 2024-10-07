package com.example.fithealth.View.Activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.R
import com.example.fithealth.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fragmentNavegation()
    }

    private fun fragmentNavegation() {
        val navHostFragment =  getFragmentContainer() as? NavHostFragment
        navHostFragment?.let {
            binding.bottomNavigationView.setupWithNavController(it.navController)
        } ?: toast("Ha habido un error en la navegacion")
    }

    private fun getFragmentContainer(): Fragment? =
        supportFragmentManager.findFragmentById(binding.ContainerView.id)

}
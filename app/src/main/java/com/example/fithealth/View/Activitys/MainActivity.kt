package com.example.fithealth.View.Activitys

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.fithealth.Model.Utils.ExtensionUtils.toast
import com.example.fithealth.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val START_FRAGMENT = "startFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val startFragment = intent.getStringExtra(START_FRAGMENT)

        fragmentNavegation(startFragment)
    }

    private fun fragmentNavegation(startFragment: String?) {
        val navHostFragment = getFragmentContainer()
        val navControler = navHostFragment?.navController

        if (navControler == null) {
            toast("Ha habido un error en la navegacion")
            return
        }
        binding.bottomNavigationView.setupWithNavController(navControler)
        //if (startFragment != null) navigatetoStartFragment(startFragment, navControler)
    }

    private fun getFragmentContainer(): NavHostFragment? =
        supportFragmentManager.findFragmentById(binding.ContainerView.id) as? NavHostFragment

}
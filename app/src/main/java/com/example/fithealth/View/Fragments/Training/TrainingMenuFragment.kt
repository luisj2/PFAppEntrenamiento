package com.example.fithealth.View.Fragments.Training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.fithealth.R
import com.example.fithealth.View.OtherAdapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class TrainingMenuFragment : Fragment() {
    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var viewPagerAdapter: ViewPagerAdapter? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /*
        viewPagerAdapter = ViewPagerAdapter(childFragmentManager)
        viewPagerAdapter!!.addFragment(CreateExerciseFragment(), "Crear Ejercicio")
        //viewPagerAdapter.addFragment(new DialogFragment(),"Comidas");
        viewPager!!.adapter = viewPagerAdapter
        tabLayout!!.setupWithViewPager(viewPager)

         */
        return inflater.inflate(R.layout.fragment_menu_entrenamiento, container, false)
    }
}

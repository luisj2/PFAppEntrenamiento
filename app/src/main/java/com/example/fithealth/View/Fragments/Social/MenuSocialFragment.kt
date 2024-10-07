package com.example.fithealth.View.Fragments.Social

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.example.fithealth.R
import com.example.fithealth.View.OtherAdapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout

class MenuSocialFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_menu_social, container, false)
        /*
        val viewPagerAdapter = ViewPagerAdapter(activity!!.supportFragmentManager)
        viewPagerAdapter.addFragment(MensajeriaFragment(), "Mensajes")
        viewPagerAdapter.addFragment(SocialFragment(), "Buscar\nAmigos")
        viewPagerAdapter.addFragment(SolicitudesAmistadFragment(), "Solicitudes\nAmistad")
        viewPager!!.adapter = viewPagerAdapter
        tabLayout!!.setupWithViewPager(viewPager)
         */
        return view
    }


}
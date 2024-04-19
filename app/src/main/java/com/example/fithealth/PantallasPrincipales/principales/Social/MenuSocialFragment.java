package com.example.fithealth.PantallasPrincipales.principales.Social;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.fithealth.ViewPagerAdapter;
import com.example.fithealth.PantallasPrincipales.principales.Social.BusquedaSocial.MensajeriaFragment;
import com.example.fithealth.R;
import com.google.android.material.tabs.TabLayout;


public class MenuSocialFragment extends Fragment {


    private TabLayout tabLayout;
    private ViewPager viewPager;
    public MenuSocialFragment() {}




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_social, container, false);

        enlazarComponentes(view);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        viewPagerAdapter.addFragment(new MensajeriaFragment(),"Mensajes");
        viewPagerAdapter.addFragment(new SocialFragment(),"Buscar\nAmigos");
        viewPagerAdapter.addFragment(new SolicitudesAmistadFragment(),"Solicitudes\nAmistad");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private void enlazarComponentes(View view) {
        tabLayout = view.findViewById(R.id.tabLayoutSocial);
        viewPager = view.findViewById(R.id.viewPagerSocial); 
    }
}
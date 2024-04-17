package com.example.fithealth.PantallasPrincipales.principales.Entrenamiento;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import com.example.fithealth.PantallasPrincipales.principales.Social.SocialFragment;
import com.example.fithealth.R;
import com.example.fithealth.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MenuEntrenamientoFragment extends Fragment {

    TabLayout tabLayout;
    ViewPager viewPager;
    public MenuEntrenamientoFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_menu_entrenamiento,container,false);

        enlazarComponentes(view);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());

        viewPagerAdapter.addFragment(new SocialFragment(),"Social");
        viewPagerAdapter.addFragment(new DialogFragment(),"Comidas");

        viewPager.setAdapter(viewPagerAdapter);

        tabLayout.setupWithViewPager(viewPager);




        return view;
    }

    private void enlazarComponentes(View view) {

        tabLayout = view.findViewById(R.id.tablelayoutEntrenamiento);
        viewPager = view.findViewById(R.id.viewPagerEntrenamiento);

    }
}

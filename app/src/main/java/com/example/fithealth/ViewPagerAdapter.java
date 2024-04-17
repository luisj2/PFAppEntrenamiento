package com.example.fithealth;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    List<Fragment> fragments;
    List <String> titulos;
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<Fragment>();
        titulos = new ArrayList<String>();

    }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    public void addFragment(Fragment fragment,String titulo){
        fragments.add(fragment);
        titulos.add(titulo);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titulos.get(position);
    }
}

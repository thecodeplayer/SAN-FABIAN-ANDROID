package com.example.sanfabian;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import Categories.FragmentCategories;
import WhatToDo.FragmentWhatToDo;

public class FragmentHome extends Fragment implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bottomNavigationView = (BottomNavigationView) getActivity().findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        getFragmentManager().beginTransaction().replace(R.id.container, new FragmentWhatToDo()).commit();
        bottomNavigationView.setSelectedItemId(R.id.nav_what_to_do);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectFragment = null;
        switch (item.getItemId()){
            case R.id.nav_what_to_do:
                selectFragment = new FragmentWhatToDo();
                break;
            case R.id.nav_categories:
                selectFragment = new FragmentCategories();
                break;
            case R.id.nav_dictionary:
                selectFragment = new FragmentDictionary();
                break;
        }
        getFragmentManager().beginTransaction().replace(R.id.container, selectFragment).addToBackStack(null).commit();
        return true;
    }

}

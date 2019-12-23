package com.peargrammers.flashcards.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.viewmodels.management.HomeViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    HomeViewModel homeViewModel;
    private TextView tv_username;

    public HomeFragment() {
        this.homeViewModel = HomeViewModel.getInstance();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tv_username = view.findViewById(R.id.tv_username);


        homeViewModel.getUserEmail().observe(HomeFragment.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_username.setText(s);
            }
        });

        homeViewModel.getCurrentUserEmail();
    }
}

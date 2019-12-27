package com.peargrammers.flashcards.fragments;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.viewmodels.management.HomeViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    HomeViewModel homeViewModel;
    GridLayout mGridLayout;

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
        mGridLayout = view.findViewById(R.id.grid_layout);

        setSingleEvent(mGridLayout);

    }

    private void setSingleEvent(GridLayout gridLayout) {

        System.out.println(gridLayout.getChildCount());
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    Log.i("CardView", "clicked");
                    switch (finalI){
                        case 0:
                            cardView.setBackgroundColor(R.color.red);
                        case 1:
                            cardView.setBackgroundColor(R.color.leadningColor);
                        case 2:
                            cardView.setBackgroundColor(R.color.white);
                        case 3:
                            cardView.setBackgroundColor(R.color.red);

                    }

                }
            });
//        }
        }
    }
}

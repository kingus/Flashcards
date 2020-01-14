package com.peargrammers.flashcards.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.FragmentManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.fragments.game_modes.ChooseCatalogsQTFramgent;
import com.peargrammers.flashcards.fragments.game_modes.QuizFragment;
import com.peargrammers.flashcards.fragments.game_modes.ViewCatalogsFragment;
import com.peargrammers.flashcards.fragments.game_modes.ViewFlashcards;
import com.peargrammers.flashcards.viewmodels.management.HomeViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    HomeViewModel homeViewModel;
    GridLayout mGridLayout;
    ViewFlashcards viewFlashcards;
    QuizFragment quizFragment;
    ChooseCatalogsQTFramgent chooseCatalogsQTFramgent;
    CatalogFragment catalogFragment;
    ViewCatalogsFragment viewCatalogsFragment;

    public HomeFragment() {
        this.homeViewModel = HomeViewModel.getInstance();
        this.viewFlashcards = new ViewFlashcards();
        this.catalogFragment = new CatalogFragment();
        this.quizFragment = new QuizFragment();
        this.chooseCatalogsQTFramgent = new ChooseCatalogsQTFramgent();
        this.viewCatalogsFragment = new ViewCatalogsFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mGridLayout = view.findViewById(R.id.grid_layout);
        setSingleEvent(mGridLayout);

    }

    private void setSingleEvent(GridLayout gridLayout) {

        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    Log.i("CardView", "clicked");
                    homeViewModel.setGameMode(finalI);
                    switch (finalI){
                        case 0:
                            changeFragment(viewCatalogsFragment);
                            break;

                        case 1:
                            changeFragment(chooseCatalogsQTFramgent);
                            System.out.println(1);
                            break;


                        case 2:
                            changeFragment(viewCatalogsFragment);
                            System.out.println(2);
                            break;


                        case 3:
                            changeFragment(viewCatalogsFragment);
                            System.out.println(3);
                            break;

                    }

                }
            });
//        }
        }
    }
    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager2 = getFragmentManager();
        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
        fragmentTransaction2.addToBackStack(fragment.toString());
//        fragmentTransaction2.hide(HomeFragment.this);
        fragmentTransaction2.replace(R.id.main_frame, fragment);
        fragmentTransaction2.commit();
        Log.i("FRAGMENT", "changed");
    }
}

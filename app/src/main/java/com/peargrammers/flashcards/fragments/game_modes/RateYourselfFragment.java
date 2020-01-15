package com.peargrammers.flashcards.fragments.game_modes;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.ViewFlashcardsAdapter;
import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateYourselfFragment extends Fragment {
    private TextView cardText;
    ImageButton btnRight, btnWrong;
    private boolean side = true;

    ViewFlashcardsAdapter viewFlashcardsAdapter;
    ArrayList<Flashcard> flashcardsList = new ArrayList<>();

    private FlashcardsViewModel flashcardsViewModel = FlashcardsViewModel.getInstance();

    public RateYourselfFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_rate_yourself, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cardText = view.findViewById(R.id.tv_card);
        btnRight = view.findViewById(R.id.btn_right);
        btnWrong = view.findViewById(R.id.btn_wrong);
        btnRight.setVisibility(View.INVISIBLE);
        btnWrong.setVisibility(View.INVISIBLE);

        flashcardsViewModel.getFlashcardsList().observe(RateYourselfFragment.this, new Observer<ArrayList<Flashcard>>() {
            @Override
            public void onChanged(ArrayList<Flashcard> flashcards) {
                flashcardsList.clear();
                flashcardsList.addAll(flashcards);
                viewFlashcardsAdapter = new ViewFlashcardsAdapter(flashcardsList, getActivity());
                flashcardsViewModel.getFlashcardsListDB(flashcardsViewModel.getCurrentCatalog().getCID());
                if(side)
                    cardText.setText(flashcardsList.get(flashcardsViewModel.getCurrentFlashcardIndex()).getFrontside());
                else
                    cardText.setText(flashcardsList.get(flashcardsViewModel.getCurrentFlashcardIndex()).getBackside());
            }
        });

        flashcardsViewModel.getFlashcardsListDB(flashcardsViewModel.getCurrentCatalog().getCID());


        cardText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(cardText, "scaleY", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(cardText, "scaleY", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        if(side){
                            side = false;
                        }
                        else{
                            side = true;
                        }
                        btnRight.setVisibility(View.VISIBLE);
                        btnWrong.setVisibility(View.VISIBLE);
                        oa2.start();
                    }
                });
                oa1.start();
            }
        });

//        btnNext.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                System.out.println("NEXT CLICKED");
//
//                if(flashcardsViewModel.getCurrentFlashcardIndex()<flashcardsList.size()-1) {
//                    flashcardsViewModel.setCurrentFlashcardIndex(flashcardsViewModel.getCurrentFlashcardIndex()+1);
//                    side = true;
//                    btnNext.setVisibility(View.INVISIBLE);
//
//                }
//                else
//                    System.out.println("END");
//            }
//
//        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("RIGHT");
                setFlags();

            }
        });

        btnWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("WRONG");
                setFlags();
            }
        });





    }

    public void setFlags(){
        btnRight.setVisibility(View.INVISIBLE);
        btnWrong.setVisibility(View.INVISIBLE);
        if(flashcardsViewModel.getCurrentFlashcardIndex()<flashcardsList.size()-1) {
            flashcardsViewModel.setCurrentFlashcardIndex(flashcardsViewModel.getCurrentFlashcardIndex()+1);
            side = true;

        }
        else
            System.out.println("END");
    }

}

package com.peargrammers.flashcards.fragments.game_modes;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.ViewFlashcardsAdapter;
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.activities.authentication.LoginActivity;
import com.peargrammers.flashcards.fragments.FragmentCoordinator;
import com.peargrammers.flashcards.fragments.HomeFragment;
import com.peargrammers.flashcards.fragments.SummarizeFragment;
import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.models.QuizDataSet;
import com.peargrammers.flashcards.viewmodels.game_modes.RateYourselfViewModel;

import java.util.ArrayList;

import static com.peargrammers.flashcards.activities.HomeActivity.dialog;

/**
 * A simple {@link Fragment} subclass.
 */
public class RateYourselfFragment extends Fragment {
    private TextView cardText;
    private ImageButton btnRight, btnWrong;
    private RateYourselfViewModel rateYourselfViewModel;
    private QuizDataSet currentDataSet;
    private SummarizeFragment summarizeFragment;

    public RateYourselfFragment() {
        rateYourselfViewModel = RateYourselfViewModel.getInstance();
        summarizeFragment = new SummarizeFragment();
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

        rateYourselfViewModel.getFlashcardsList().observe(RateYourselfFragment.this, new Observer<ArrayList<Flashcard>>() {
            @Override
            public void onChanged(ArrayList<Flashcard> flashcards) {
                rateYourselfViewModel.resetStatistics();
                rateYourselfViewModel.setCurrentFlashcardIndex(0);
                if(flashcards.size()>0) {
                    currentDataSet = rateYourselfViewModel.getSingleQuizDataSet();
                    cardText.setText(currentDataSet.getFlashcard().getFrontside().toUpperCase());
                }
                else{
//                    HomeActivity.dialog = ProgressDialog.show(getContext(), "", "Please Wait...");
//                    new CountDownTimer(500, 500) {
//
//                        public void onTick(long millisUntilFinished) {
//                        }
//
//                        public void onFinish() {
//                            HomeActivity.dialog.dismiss();
//
//                        }
//                    }.start();
                    FragmentCoordinator.changeFragment(HomeActivity.homeFragment, getFragmentManager());

                }

            }
        });
        rateYourselfViewModel.getFlashcardsListDB(rateYourselfViewModel.getCurrentCID());


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
                        if(rateYourselfViewModel.isSide()){
                            cardText.setText(currentDataSet.getFlashcard().getFrontside().toUpperCase());
                            rateYourselfViewModel.setSide(false);
                        }
                        else{
                            cardText.setText(currentDataSet.getFlashcard().getBackside().toUpperCase());
                            rateYourselfViewModel.setSide(true);
                        }
                        btnRight.setVisibility(View.VISIBLE);
                        btnWrong.setVisibility(View.VISIBLE);
                        oa2.start();
                    }
                });
                oa1.start();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("RIGHT");
                rateYourselfViewModel.processAnswer(true);
                setFlags();

            }
        });

        btnWrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("WRONG");
                rateYourselfViewModel.processAnswer(false);
                setFlags();

            }
        });
    }

    public void setFlags(){
        btnRight.setVisibility(View.INVISIBLE);
        btnWrong.setVisibility(View.INVISIBLE);
        System.out.println(rateYourselfViewModel.getCurrentFlashcardIndex());
        System.out.println();
        if(rateYourselfViewModel.getCurrentFlashcardIndex() != rateYourselfViewModel.getFlashcardsInput().size()) {
            currentDataSet =  rateYourselfViewModel.getSingleQuizDataSet();
            cardText.setText(currentDataSet.getFlashcard().getFrontside().toUpperCase());
            rateYourselfViewModel.setSide(false);
        }
        else{
            System.out.println("END");
            rateYourselfViewModel.removeLearnedFlashcards();
            rateYourselfViewModel.updateFlashcardsLevel();
            FragmentCoordinator.changeFragment(summarizeFragment, getFragmentManager());
            rateYourselfViewModel.resetStatistics();
        }

    }


}

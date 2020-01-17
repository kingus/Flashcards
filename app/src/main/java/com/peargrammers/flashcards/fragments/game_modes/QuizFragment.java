package com.peargrammers.flashcards.fragments.game_modes;


import android.annotation.SuppressLint;
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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.activities.authentication.LoginActivity;
import com.peargrammers.flashcards.fragments.FragmentCoordinator;
import com.peargrammers.flashcards.fragments.HomeFragment;
import com.peargrammers.flashcards.fragments.SummarizeFragment;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.models.QuizDataSet;
import com.peargrammers.flashcards.viewmodels.game_modes.QuizViewModel;
import com.peargrammers.flashcards.viewmodels.game_modes.SummarizeViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuizFragment extends Fragment {
    private QuizViewModel quizViewModel;
    private Button answerA;
    private Button answerB;
    private Button answerC;
    private Button answerD;
    private FloatingActionButton nextFloatingButton;
    private TextView tvQuestionText;
    private TextView tvScore;
    private TextView tvTotal;
//    public static ProgressDialog infoDialog = null;



    private SummarizeViewModel summarizeViewModel;
    private SummarizeFragment summarizeFragment;



    public QuizFragment() {
        quizViewModel = QuizViewModel.getInstance();
        summarizeViewModel = SummarizeViewModel.getInstance();
        summarizeFragment = new SummarizeFragment();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quiz, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {



        HomeActivity.dialog = ProgressDialog.show(getContext(), "", "Please Wait...");

        answerA = view.findViewById(R.id.answerA);
        answerB = view.findViewById(R.id.answerB);
        answerC = view.findViewById(R.id.answerC);
        answerD = view.findViewById(R.id.answerD);
        nextFloatingButton = view.findViewById(R.id.nextFloatingButton);
        tvQuestionText = view.findViewById(R.id.tv_question_text);
        tvScore = view.findViewById(R.id.tv_score);
        tvTotal = view.findViewById(R.id.tv_total);
        nextFloatingButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                if (quizViewModel.getCurrentFlashardIndex() != quizViewModel.getFlashcardsInput().size()) {
                    nextFloatingButton.setVisibility(View.INVISIBLE);
                    QuizDataSet currentDataSet =  quizViewModel.getSingleQuizDataSet();
                    answerA.setText((CharSequence) currentDataSet.getAnswers().get(0));
                    answerB.setText((CharSequence) currentDataSet.getAnswers().get(1));
                    answerC.setText((CharSequence) currentDataSet.getAnswers().get(2));
                    answerD.setText((CharSequence) currentDataSet.getAnswers().get(3));
                    tvQuestionText.setText((currentDataSet.getFlashcard().getFrontside()).toUpperCase());
                    tvScore.setText((CharSequence) Integer.toString(currentDataSet.getFlashcard().getSmallBox()));
                    tvTotal.setText(quizViewModel.getCurrentFlashardIndex() + "/" + quizViewModel.getFlashcardsInput().size());

                    answerA.setEnabled(true);
                    answerB.setEnabled(true);
                    answerC.setEnabled(true);
                    answerD.setEnabled(true);
                    answerA.setBackgroundColor(getResources().getColor(R.color.leadningColor));
                    answerB.setBackgroundColor(getResources().getColor(R.color.leadningColor));
                    answerC.setBackgroundColor(getResources().getColor(R.color.leadningColor));
                    answerD.setBackgroundColor(getResources().getColor(R.color.leadningColor));
                } else {
                    System.out.println("Koniec fiszek");
                    quizViewModel.removeLearnedFlashcards();
                    quizViewModel.updateFlashcardsLevel();
                    FragmentCoordinator.changeFragment(summarizeFragment, getFragmentManager());

                }


            }
        });

        View.OnClickListener onClickListener =  new View.OnClickListener() {

            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {

                nextFloatingButton.setVisibility(View.VISIBLE);
                Button clickedButton = v.findViewById(v.getId());
                if (quizViewModel.processAnswer(clickedButton.getText().toString()))
                {
                    clickedButton.setBackgroundColor(getResources().getColor(R.color.green));

                } else {
                    clickedButton.setBackgroundColor(getResources().getColor(R.color.red));
                    String goodAnswer = quizViewModel.getFlashcardsInput().get(quizViewModel.getCurrentFlashardIndex()-1).getBackside();
                    if (answerA.getText().toString().equals(goodAnswer)) {
                        answerA.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    if (answerB.getText().toString().equals(goodAnswer)) {
                        answerB.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    if (answerC.getText().toString().equals(goodAnswer)) {
                        answerC.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                    if (answerD.getText().toString().equals(goodAnswer)) {
                        answerD.setBackgroundColor(getResources().getColor(R.color.green));
                    }
                }
                answerA.setEnabled(false);
                answerB.setEnabled(false);
                answerC.setEnabled(false);
                answerD.setEnabled(false);

            }
        } ;
        answerA.setOnClickListener(onClickListener);
        answerB.setOnClickListener(onClickListener);
        answerC.setOnClickListener(onClickListener);
        answerD.setOnClickListener(onClickListener);


        quizViewModel.getFlashcardsList().observe(QuizFragment.this, new Observer<ArrayList<Flashcard>>() {
            @Override
            public void onChanged(ArrayList<Flashcard> flashcards) {

                System.out.println("SPRAWDZAM ROZMIAR: " + flashcards.size());
                if (flashcards.size() < 4) {
                    HomeActivity.dialog.dismiss();
                    FragmentCoordinator.changeFragment(new HomeFragment(), getFragmentManager());

//                    HomeActivity.dialog.dismiss();
//
//                   // FragmentCoordinator.changeFragment(new HomeFragment(), getFragmentManager());
//
//                    infoDialog =  ProgressDialog.show(getActivity(), "", "Not enough flashcards. You will be redirected to the start screen.");
//                    new CountDownTimer(1000, 1000) {
//
//                        public void onTick(long millisUntilFinished) {
//                        }
//
//                        public void onFinish() {
//                            infoDialog.dismiss();
//                            FragmentCoordinator.changeFragment(new HomeFragment(), getFragmentManager());
//                            infoDialog.dismiss();
//                        }
//                    }.start();
                } else {
                   
                quizViewModel.resetStatistics();
                quizViewModel.setCurrentFlashardIndex(0);
                QuizDataSet currentDataSet =  quizViewModel.getSingleQuizDataSet();
//                answerA.setText();
                    answerA.setText((CharSequence) currentDataSet.getAnswers().get(0));
                    answerB.setText((CharSequence) currentDataSet.getAnswers().get(1));
                    answerC.setText((CharSequence) currentDataSet.getAnswers().get(2));
                    answerD.setText((CharSequence) currentDataSet.getAnswers().get(3));
                    tvQuestionText.setText(currentDataSet.getFlashcard().getFrontside().toUpperCase());
                    tvScore.setText((CharSequence) Integer.toString(currentDataSet.getFlashcard().getSmallBox()));
                    tvTotal.setText(quizViewModel.getCurrentFlashardIndex() + "/" + quizViewModel.getFlashcardsInput().size());
                    HomeActivity.dialog.dismiss();
                }



            }
        });

        quizViewModel.getFlashcardsListDB(quizViewModel.getCurrentCID());

    }

}

package com.peargrammers.flashcards.fragments.game_modes;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.models.QuizDataSet;
import com.peargrammers.flashcards.viewmodels.game_modes.QuizViewModel;

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


    public QuizFragment() {
        quizViewModel = QuizViewModel.getInstance();
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
        super.onViewCreated(view, savedInstanceState);
        HomeActivity.dialog = ProgressDialog.show(getContext(), "", "Please Wait...");

        answerA = view.findViewById(R.id.answerA);
        answerB = view.findViewById(R.id.answerB);
        answerC = view.findViewById(R.id.answerC);
        answerD = view.findViewById(R.id.answerD);
        nextFloatingButton = view.findViewById(R.id.nextFloatingButton);
        tvQuestionText = view.findViewById(R.id.tv_question_text);
        tvScore = view.findViewById(R.id.tv_score);
        nextFloatingButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                nextFloatingButton.setVisibility(View.INVISIBLE);
                QuizDataSet currentDataSet =  quizViewModel.getSingleQuizDataSet();
                answerA.setText((CharSequence) currentDataSet.getAnswers().get(0));
                answerB.setText((CharSequence) currentDataSet.getAnswers().get(1));
                answerC.setText((CharSequence) currentDataSet.getAnswers().get(2));
                answerD.setText((CharSequence) currentDataSet.getAnswers().get(3));
                tvQuestionText.setText((currentDataSet.getFlashcard().getFrontside()).toLowerCase());
                tvScore.setText((CharSequence) Integer.toString(currentDataSet.getFlashcard().getSmallBox()));
                answerA.setEnabled(true);
                answerB.setEnabled(true);
                answerC.setEnabled(true);
                answerD.setEnabled(true);
                answerA.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                answerB.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                answerC.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                answerD.setBackgroundColor(getResources().getColor(R.color.colorAccent));

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
                    clickedButton.setBackgroundColor(getResources().getColor(R.color.leadningColor));

                } else {
                    clickedButton.setBackgroundColor(getResources().getColor(R.color.red));
                    System.out.println("A: " + answerA.getText().toString());
                    System.out.println("B: " + answerB.getText().toString());
                    System.out.println("C: " + answerC.getText().toString());
                    System.out.println("D: " + answerD.getText().toString());
                    System.out.println("Clicked: " + clickedButton.getText().toString());
                    String goodAnswer = quizViewModel.getFlashcardsInput().get(quizViewModel.getCurrentFlashardIndex()-1).getBackside();
                    if (answerA.getText().toString().equals(goodAnswer)) {
                        System.out.println("DOBRZE A");
                        answerA.setBackgroundColor(getResources().getColor(R.color.leadningColor));
                    }
                    if (answerB.getText().toString().equals(goodAnswer)) {
                        System.out.println("DOBRZE B");
                        answerB.setBackgroundColor(getResources().getColor(R.color.leadningColor));
                    }
                    if (answerC.getText().toString().equals(goodAnswer)) {
                        System.out.println("DOBRZE C");
                        answerC.setBackgroundColor(getResources().getColor(R.color.leadningColor));
                    }
                    if (answerD.getText().toString().equals(goodAnswer)) {
                        System.out.println("DOBRZE D");
                        answerD.setBackgroundColor(getResources().getColor(R.color.leadningColor));
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
                QuizDataSet currentDataSet =  quizViewModel.getSingleQuizDataSet();
//                answerA.setText();
                HomeActivity.dialog.dismiss();

                answerA.setText((CharSequence) currentDataSet.getAnswers().get(0));
                answerB.setText((CharSequence) currentDataSet.getAnswers().get(1));
                answerC.setText((CharSequence) currentDataSet.getAnswers().get(2));
                answerD.setText((CharSequence) currentDataSet.getAnswers().get(3));
                tvQuestionText.setText(currentDataSet.getFlashcard().getFrontside());
                tvScore.setText((CharSequence) Integer.toString(currentDataSet.getFlashcard().getSmallBox()));

            }
        });

        quizViewModel.getFlashcardsListDB(quizViewModel.getCurrentCID());

    }
}

package com.peargrammers.flashcards.fragments.game_modes;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.ViewFlashcardsAdapter;
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.fragments.FragmentCoordinator;
import com.peargrammers.flashcards.fragments.SummarizeFragment;
import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.models.QuizDataSet;
import com.peargrammers.flashcards.viewmodels.game_modes.QuickAnswerViewModel;
import com.peargrammers.flashcards.viewmodels.game_modes.SummarizeViewModel;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;

import java.util.ArrayList;

import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickAnswerFragment extends Fragment {
    private Button btnCheck;
    private TextView cardText;
    private EditText etAnswer;
    private ImageButton btnHint;
    ImageButton btnRight, btnWrong;
    private FlashcardsViewModel flashcardsViewModel = FlashcardsViewModel.getInstance();
    private QuickAnswerViewModel quickAnswerViewModel;
    private SummarizeViewModel summarizeViewModel;
    private SummarizeFragment summarizeFragment;
    private QuizDataSet currentDataSet;

    ViewFlashcardsAdapter viewFlashcardsAdapter;
    ArrayList<Flashcard> flashcardsList = new ArrayList<>();
    private boolean side = true;
    FloatingActionButton fBtnNext;


    public QuickAnswerFragment() {
        quickAnswerViewModel = QuickAnswerViewModel.getInstance();
        summarizeViewModel = SummarizeViewModel.getInstance();
        summarizeFragment = new SummarizeFragment();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_quick_answer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnCheck = view.findViewById(R.id.btn_check);
        cardText = view.findViewById(R.id.tv_card);
        fBtnNext = view.findViewById(R.id.fbtn_next);
        btnHint = view.findViewById(R.id.btn_hint);
        etAnswer = view.findViewById(R.id.et_answer);
        btnRight = view.findViewById(R.id.btn_right);
        btnWrong = view.findViewById(R.id.btn_wrong);

        /*
        flashcardsViewModel.getFlashcardsList().observe(QuickAnswerFragment.this, new Observer<ArrayList<Flashcard>>() {
            @Override
            public void onChanged(ArrayList<Flashcard> flashcards) {
                System.out.println("POBIERAM DANE z BAZY");
                flashcardsList.clear();
                flashcardsList.addAll(flashcards);
                //viewFlashcardsAdapter = new ViewFlashcardsAdapter(flashcardsList, getActivity());
                flashcardsViewModel.getFlashcardsListDB(flashcardsViewModel.getCurrentCatalog().getCID());
                if(side)
                    cardText.setText(flashcardsList.get(flashcardsViewModel.getCurrentFlashcardIndex()).getFrontside());
                else
                    cardText.setText(flashcardsList.get(flashcardsViewModel.getCurrentFlashcardIndex()).getBackside());
            }
        });

        flashcardsViewModel.getFlashcardsListDB(flashcardsViewModel.getCurrentCatalog().getCID());
        */

        quickAnswerViewModel.getFlashcardsList().observe(QuickAnswerFragment.this, new Observer<ArrayList<Flashcard>>() {
            @Override
            public void onChanged(ArrayList<Flashcard> flashcards) {
                quickAnswerViewModel.resetStatistics();
                quickAnswerViewModel.setCurrentFlashardIndex(0);

                if(flashcards.size()>0) {
                    currentDataSet =  quickAnswerViewModel.getSingleQuizDataSet();
                    cardText.setText(currentDataSet.getFlashcard().getFrontside());
                }
                else{
                    FragmentCoordinator.changeFragment(HomeActivity.homeFragment, getFragmentManager());
                }
            }
        });

        quickAnswerViewModel.getFlashcardsListDB(quickAnswerViewModel.getCurrentCID());


        btnCheck.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fBtnNext.setVisibility(VISIBLE);
                btnHint.setVisibility(View.INVISIBLE);
                btnCheck.setEnabled(false);

                cardText.setText(currentDataSet.getFlashcard().getBackside());
                if (quickAnswerViewModel.processAnswer(etAnswer.getText().toString())){
                    btnRight.setVisibility(VISIBLE);
                } else {
                    btnWrong.setVisibility(VISIBLE);
                }
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(cardText, "scaleY", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(cardText, "scaleY", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        side = false;
                        oa2.start();
                    }
                });
                oa1.start();

            }

        });

        fBtnNext.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            public void onClick(View v) {
                btnWrong.setVisibility(View.INVISIBLE);
                btnRight.setVisibility(View.INVISIBLE);
                btnHint.setVisibility(VISIBLE);

                etAnswer.setText("");
                if(quickAnswerViewModel.getCurrentFlashardIndex() != quickAnswerViewModel.getFlashcardsInput().size()) {
                    fBtnNext.setVisibility(View.INVISIBLE);
                    btnCheck.setEnabled(true);
//                    flashcardsViewModel.setCurrentFlashcardIndex(flashcardsViewModel.getCurrentFlashcardIndex() + 1);
//                    side = true;
                    currentDataSet =  quickAnswerViewModel.getSingleQuizDataSet();
                    cardText.setText(currentDataSet.getFlashcard().getFrontside());
                    quickAnswerViewModel.setHint(0);
                    etAnswer.setText("");

                }
                else{
                    System.out.println("END");
                    quickAnswerViewModel.removeLearnedFlashcards();
                    quickAnswerViewModel.updateFlashcardsLevel();
                    FragmentCoordinator.changeFragment(summarizeFragment, getFragmentManager());
                    etAnswer.setText("");

                }
            }
        });

        btnHint.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(quickAnswerViewModel.getHint()<3){
                    quickAnswerViewModel.setHint(quickAnswerViewModel.getHint()+1);
                    if(currentDataSet.getFlashcard().getBackside().length() >= quickAnswerViewModel.getHint()) {
                        etAnswer.setText(currentDataSet.getFlashcard().getBackside().substring(0, quickAnswerViewModel.getHint()));
                    }
                }
                else{
                    String text = "You've used all the hints.";
                    Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

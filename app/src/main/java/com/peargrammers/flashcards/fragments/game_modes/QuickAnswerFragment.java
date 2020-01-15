package com.peargrammers.flashcards.fragments.game_modes;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.peargrammers.flashcards.R;

import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuickAnswerFragment extends Fragment {
    private Button btnCheck;
    private TextView cardText;
    private EditText etAnswer;
    FloatingActionButton fBtnNext;


    public QuickAnswerFragment() {
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


        btnCheck.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                fBtnNext.setVisibility(VISIBLE);
                btnCheck.setEnabled(false);
                final ObjectAnimator oa1 = ObjectAnimator.ofFloat(cardText, "scaleY", 1f, 0f);
                final ObjectAnimator oa2 = ObjectAnimator.ofFloat(cardText, "scaleY", 0f, 1f);
                oa1.setInterpolator(new DecelerateInterpolator());
                oa2.setInterpolator(new AccelerateDecelerateInterpolator());
                oa1.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        cardText.setText("LALALA");
                        oa2.start();
                    }
                });
                oa1.start();

            }

        });

        fBtnNext.setOnClickListener(new View.OnClickListener() {

            @SuppressLint("RestrictedApi")
            public void onClick(View v) {
                fBtnNext.setVisibility(View.INVISIBLE);
                btnCheck.setEnabled(true);

            }

        });

    }
}

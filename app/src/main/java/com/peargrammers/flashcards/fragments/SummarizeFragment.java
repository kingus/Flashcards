package com.peargrammers.flashcards.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.viewmodels.game_modes.SummarizeViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummarizeFragment extends Fragment {
    private SummarizeViewModel summarizeViewModel;
    private TextView tvWellAnswered;
    private TextView tvWrongAnswered;
    private Button btnGoHome;

    public SummarizeFragment() {
        summarizeViewModel = SummarizeViewModel.getInstance();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_summarize, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvWellAnswered = view.findViewById(R.id.tv_well_answered);
        tvWrongAnswered = view.findViewById(R.id.tv_wrong_answered);
        btnGoHome = view.findViewById(R.id.btn_go_home);

        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentCoordinator.changeFragment(HomeActivity.homeFragment, getFragmentManager());
            }
        });

        tvWellAnswered.setText(Integer.toString(summarizeViewModel.getGoodAnsweredCounter()));
        tvWrongAnswered.setText(Integer.toString(summarizeViewModel.getWrongAnsweredCounter()));
    }
}

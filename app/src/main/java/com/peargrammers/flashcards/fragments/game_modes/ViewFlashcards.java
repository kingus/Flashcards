package com.peargrammers.flashcards.fragments.game_modes;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.ViewFlashcardsAdapter;
import com.peargrammers.flashcards.fragments.CatalogFragment;
import com.peargrammers.flashcards.models.Flashcard;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFlashcards extends Fragment {

    ViewPager viewPager;
    ViewFlashcardsAdapter viewFlashcardsAdapter;
    List<Flashcard> flashcardsList;

    public ViewFlashcards() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_flashcards, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        flashcardsList = new ArrayList<>();
        flashcardsList.add(new Flashcard("cat", "kot"));
        flashcardsList.add(new Flashcard("dog", "pies"));
        flashcardsList.add(new Flashcard("turtle", "zolw"));
        flashcardsList.add(new Flashcard("mouse", "mysz"));

        viewFlashcardsAdapter = new ViewFlashcardsAdapter(flashcardsList, getActivity());
        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(viewFlashcardsAdapter);
        viewPager.setPadding(130, 0, 130, 0);

    }



}

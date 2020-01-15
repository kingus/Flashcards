package com.peargrammers.flashcards.fragments.game_modes;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.peargrammers.flashcards.FlashcardAdapter;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.RecyclerViewClickInterface;
import com.peargrammers.flashcards.ViewFlashcardsAdapter;
import com.peargrammers.flashcards.fragments.CatalogFragment;
import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewFlashcards extends Fragment {

    ViewPager viewPager;
    ViewFlashcardsAdapter viewFlashcardsAdapter;
    FlashcardsViewModel flashcardsViewModel = FlashcardsViewModel.getInstance();
    ArrayList<Flashcard> flashcardsList;
    Button btnSearch;
    EditText searchCard;

    public ViewFlashcards() {
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
        viewPager = view.findViewById(R.id.viewPager);
        searchCard = view.findViewById(R.id.search_card);
        btnSearch = view.findViewById(R.id.btn_search);


        flashcardsViewModel.getFlashcardsList().observe(ViewFlashcards.this, new Observer<ArrayList<Flashcard>>() {
            @Override
            public void onChanged(ArrayList<Flashcard> flashcards) {
                flashcardsList.clear();
                flashcardsList.addAll(flashcards);
                viewFlashcardsAdapter = new ViewFlashcardsAdapter(flashcardsList, getActivity());
                viewPager.setAdapter(viewFlashcardsAdapter);
                viewPager.setPadding(130, 0, 130, 0);
            }
        });

        flashcardsViewModel.getFlashcardsListDB(flashcardsViewModel.getCurrentCatalog().getCID());


        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = flashcardsViewModel.findFlashcardPosition(searchCard.getText().toString(), flashcardsList);
                viewPager.setCurrentItem(pos);
            }
        });

    }
    //needs to be done in a view model!!!!!!!!!!!!!!!!!

}

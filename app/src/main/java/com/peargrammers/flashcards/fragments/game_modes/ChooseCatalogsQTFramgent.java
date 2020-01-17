package com.peargrammers.flashcards.fragments.game_modes;


import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peargrammers.flashcards.Adapters.CatalogPlayAdapter;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.Adapters.RecyclerViewClickInterface;
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.fragments.FragmentCoordinator;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.viewmodels.game_modes.QuizViewModel;
import com.peargrammers.flashcards.viewmodels.management.CatalogsViewModel;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChooseCatalogsQTFramgent extends Fragment implements RecyclerViewClickInterface{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Catalog> catalogsList = new ArrayList<>();
    private CatalogsViewModel catalogsViewModel;
    private QuizViewModel quizViewModel;
    private RecyclerView.LayoutManager mLayoutManager;
    private QuizFragment quizFragment;

    public ChooseCatalogsQTFramgent() {
        catalogsViewModel = CatalogsViewModel.getInstance();
        quizViewModel = QuizViewModel.getInstance();
        quizFragment = new QuizFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_play, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_categories);
        HomeActivity.dialog = ProgressDialog.show(getContext(), "", "Please Wait...");
        mRecyclerView.setHasFixedSize(true);
        final RecyclerViewClickInterface rvci = this;

        catalogsViewModel.getUsersCatalogsList().observe(ChooseCatalogsQTFramgent.this, new Observer<ArrayList<Catalog>>() {
            @Override
            public void onChanged(ArrayList<Catalog> catalogs) {
                catalogsList.clear();
                catalogsList.addAll(catalogs);
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
                System.out.println(glm.getSpanCount());
                mLayoutManager = glm;
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new CatalogPlayAdapter(catalogsList, rvci);
                mRecyclerView.setAdapter(mAdapter);
                HomeActivity.dialog.dismiss();
            }
        });


        catalogsViewModel.getUsetsCatalogListDB();

    }

    @Override
    public void onItemClick(int position) {
        FlashcardsViewModel.getInstance().setCurrentCatalog(catalogsList.get(position));
//        System.out.println(catalogsList.get(position).getName());
//        System.out.println(position);
//        System.out.println("CID: " + catalogsList.get(position).getCID());
        quizViewModel.setCurrentCID(catalogsList.get(position).getCID());
        quizViewModel.setGoodAnsweredCounter(0);
        quizViewModel.setWrongAnsweredCounter(0);
        FragmentCoordinator.changeFragment(quizFragment, getFragmentManager());
        mRecyclerView.findViewHolderForAdapterPosition(position).itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));

    }
}

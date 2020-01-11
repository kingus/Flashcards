package com.peargrammers.flashcards.fragments.game_modes;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.peargrammers.flashcards.CatalogAdapter;
import com.peargrammers.flashcards.CatalogPlayAdapter;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.RecyclerViewClickInterface;
import com.peargrammers.flashcards.activities.management.AddCatalogActivity;
import com.peargrammers.flashcards.activities.management.EditCatalogActivity;
import com.peargrammers.flashcards.activities.management.FlashcardsActivity;
import com.peargrammers.flashcards.fragments.CatalogFragment;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.viewmodels.management.CatalogsViewModel;
import com.peargrammers.flashcards.viewmodels.management.EditCatalogViewModel;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 */
public class PlayFramgent extends Fragment implements RecyclerViewClickInterface{

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Catalog> catalogsList = new ArrayList<>();
    private CatalogsViewModel catalogsViewModel;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressBar progressBar;

    public PlayFramgent() {
        catalogsViewModel = CatalogsViewModel.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_play, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar  = view.findViewById(R.id.progress_circular);
        progressBar.setVisibility(ProgressBar.VISIBLE);
        mRecyclerView = view.findViewById(R.id.recycler_categories);
        mRecyclerView.setHasFixedSize(true);

        final RecyclerViewClickInterface rvci = this;
        catalogsViewModel.getUsersCatalogsList().observe(PlayFramgent.this, new Observer<ArrayList<Catalog>>() {
            @Override
            public void onChanged(ArrayList<Catalog> catalogs) {
                catalogsList.clear();
                catalogsList.addAll(catalogs);
                progressBar.setVisibility(ProgressBar.INVISIBLE);
                mLayoutManager = new GridLayoutManager(getContext(), 2);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new CatalogPlayAdapter(catalogsList, rvci);
                mRecyclerView.setAdapter(mAdapter);
            }
        });


        catalogsViewModel.getUsetsCatalogListDB();

    }

    private void setSingleEvent(GridLayout gridLayout) {

        System.out.println(gridLayout.getChildCount());
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            final CardView cardView = (CardView) gridLayout.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @SuppressLint("ResourceAsColor")
                @Override
                public void onClick(View v) {
                    Log.i("CardView", "clicked");
                    switch (finalI){
                        case 0:
                            System.out.println(finalI);
                            break;

                        case 1:
                            System.out.println(finalI);
                            break;


                        case 2:
                            System.out.println(finalI);
                            break;


                        case 3:
                            System.out.println(finalI);
                            break;



                    }

                }
            });
//        }
        }
    }


    @Override
    public void onItemClick(int position) {
        FlashcardsViewModel.getInstance().setCurrentCatalog(catalogsList.get(position));
        System.out.println(catalogsList.get(position).getName());
        CardView cardView = (CardView) mRecyclerView.getChildAt(position);
//        cardView.setCardBackgroundColor(Color.RED);
        System.out.println(mRecyclerView.getScrollBarSize());
        cardView.setCardBackgroundColor(getResources().getColor(R.color.colorAccent));

    }
}

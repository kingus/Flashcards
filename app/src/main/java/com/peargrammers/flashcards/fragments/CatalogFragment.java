package com.peargrammers.flashcards.fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.AddCatalogActivity;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.viewmodels.management.CatalogsViewModel;

import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends Fragment {

    private FloatingActionButton floatingActionButton;
    private CatalogsViewModel catalogsViewModel;
    private TableLayout tl_catalogs_table;
    public CatalogFragment() {
        catalogsViewModel = CatalogsViewModel.getInstance();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_catalog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        floatingActionButton  = view.findViewById(R.id.floatingActionButton);
        tl_catalogs_table  = view.findViewById(R.id.tl_catalogs_table);


        catalogsViewModel.getUsersCatalogsList().observe(CatalogFragment.this, new Observer<ArrayList<Catalog>>() {
            @Override
            public void onChanged(ArrayList<Catalog> catalogs) {
                tl_catalogs_table.removeAllViews();
                for (Catalog tmp : catalogs) {
                    TextView textView = new TextView(getContext());
                    textView.setText(tmp.getName());
                    TableRow tableRow = new TableRow(getContext());
                    tableRow.addView(textView);
                    tl_catalogs_table.addView(tableRow);
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getActivity(), AddCatalogActivity.class);
                startActivity(myIntent);
            }
        });


        catalogsViewModel.getUsetsCatalogListDB();
    }
}

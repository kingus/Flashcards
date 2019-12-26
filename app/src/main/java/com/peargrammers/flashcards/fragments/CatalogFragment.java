package com.peargrammers.flashcards.fragments;


import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.peargrammers.flashcards.CatalogAdapter;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.AddCatalogActivity;
import com.peargrammers.flashcards.activities.EditCatalogActivity;
import com.peargrammers.flashcards.activities.authentication.LoginActivity;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.viewmodels.management.AddCatalogViewModel;
import com.peargrammers.flashcards.viewmodels.management.EditCatalogViewModel;
import com.peargrammers.flashcards.viewmodels.management.CatalogsViewModel;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CatalogsViewModel catalogsViewModel;
    private AddCatalogViewModel addCatalogViewModel;
    private FloatingActionButton floatingActionButton;

    private final FragmentActivity catalogActivity = getActivity();

    private ArrayList<Catalog> catalogsList = new ArrayList<>();

    private Catalog removedCatalog = null;


    public CatalogFragment() {
        catalogsViewModel = CatalogsViewModel.getInstance();
        addCatalogViewModel = AddCatalogViewModel.getInstance();
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_catalog, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        floatingActionButton  = view.findViewById(R.id.floatingActionButton);


        catalogsViewModel.getUsersCatalogsList().observe(CatalogFragment.this, new Observer<ArrayList<Catalog>>() {
            @Override
            public void onChanged(ArrayList<Catalog> catalogs) {
                catalogsList.clear();
                catalogsList.addAll(catalogs);
                mLayoutManager = new LinearLayoutManager(catalogActivity);
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new CatalogAdapter(catalogsList);

                mRecyclerView.setAdapter(mAdapter);
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
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);


        catalogsViewModel.getIfRemoveCatalogProperly().observe(getActivity(), new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Toast.makeText(getActivity(), "Catalog removed", Toast.LENGTH_SHORT).show();

            }
        });

    }


    ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

            final int position = viewHolder.getAdapterPosition();

            switch(direction){
                case ItemTouchHelper.LEFT:
                    Log.i("REMOVE", String.valueOf(position));
                    catalogsViewModel.removeCatalogFromList(catalogsList.get(position).getCID());

                    removedCatalog = catalogsList.remove(position);
                    //deletedCatalog = method removing the catalog and returning a catalog
                    //mAdapter.notifyItemRemoved(position);
                    Snackbar.make(mRecyclerView, "Undo", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    //method that'll add removed catalog to the list
                                    addCatalogViewModel.addNewCatalog(removedCatalog.getName(), removedCatalog.getCategory());
                                    catalogsList.add(position,removedCatalog);

                                    System.out.println("UNDO CLICKED");
                                    //NEEDED
                                    //mAdapter.notifyItemInserted();
                                }
                            }).show();
                    break;
                case ItemTouchHelper.RIGHT:
                    //EditCatalogActivity editCatalogActivity = EditCatalogActivity.get

                    EditCatalogViewModel.getInstance().setEditedCatalog(catalogsList.get(position));

                    Intent myIntent = new Intent(getActivity(), EditCatalogActivity.class);
                    startActivity(myIntent);
                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(getActivity(), R.color.leadningColor))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(getActivity(), R.color.leadningColor))
                    .addSwipeRightActionIcon(R.drawable.ic_edit)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };

}

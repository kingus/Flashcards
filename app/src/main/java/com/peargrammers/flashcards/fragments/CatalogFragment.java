package com.peargrammers.flashcards.fragments;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.peargrammers.flashcards.CatalogAdapter;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.RecyclerViewClickInterface;
import com.peargrammers.flashcards.activities.management.FlashcardsActivity;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.viewmodels.management.AddCatalogViewModel;
import com.peargrammers.flashcards.viewmodels.management.EditCatalogViewModel;
import com.peargrammers.flashcards.viewmodels.management.CatalogsViewModel;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 */
public class CatalogFragment extends Fragment implements RecyclerViewClickInterface {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private CatalogsViewModel catalogsViewModel;
    private AddCatalogViewModel addCatalogViewModel;
    private FloatingActionButton floatingActionButton;
    private EditCatalogViewModel editCatalogViewModel;


    private final FragmentActivity catalogActivity = getActivity();

    private ArrayList<Catalog> catalogsList = new ArrayList<>();

    private Catalog removedCatalog = null;


    public CatalogFragment() {
        catalogsViewModel = CatalogsViewModel.getInstance();
        addCatalogViewModel = AddCatalogViewModel.getInstance();
        editCatalogViewModel = EditCatalogViewModel.getInstance();
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
        floatingActionButton  = view.findViewById(R.id.nextFloatingButton);

        final RecyclerViewClickInterface rvci = this;
        catalogsViewModel.getUsersCatalogsList().observe(CatalogFragment.this, new Observer<ArrayList<Catalog>>() {
            @Override
            public void onChanged(ArrayList<Catalog> catalogs) {
                catalogsList.clear();
                catalogsList.addAll(catalogs);
                mLayoutManager = new LinearLayoutManager(catalogActivity);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mAdapter = new CatalogAdapter(catalogsList, rvci);
                mRecyclerView.setAdapter(mAdapter);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCatalogDialog();
            }
        });


        catalogsViewModel.getUsetsCatalogListDB();
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemTouchHelperCallback);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);

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
                    showRemoveDialog(position);
                    break;
                case ItemTouchHelper.RIGHT:
                    EditCatalogViewModel.getInstance().setEditedCatalog(catalogsList.get(position));
                    showEditDialog(position);
                    break;
            }
            mAdapter.notifyItemChanged(position);
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

    @Override
    public void onItemClick(int position) {
        FlashcardsViewModel.getInstance().setCurrentCatalog(catalogsList.get(position));
        Intent myIntent = new Intent(getActivity(), FlashcardsActivity.class);
        startActivity(myIntent);
    }

    public void showRemoveDialog(final int position){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.alert_remove_catalog, null);
        Button acceptBut = view.findViewById(R.id.accept);
        Button declineBut = view.findViewById(R.id.decline);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(view).create();
        alertDialog.show();
        acceptBut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i("REMOVE", String.valueOf(position));
                alertDialog.hide();
                catalogsViewModel.removeCatalogFromList(catalogsList.get(position).getCID());
                removedCatalog = catalogsList.remove(position);
                Snackbar.make(mRecyclerView, "Catalog has been removed.", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        addCatalogViewModel.addNewCatalog(removedCatalog.getName(), removedCatalog.getCategory());
                        catalogsList.add(position,removedCatalog);
                    }
                }).show();
            }
        });

        declineBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });
    }

    public void showAddCatalogDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.alert_catalog, null);
        Button cancelBut = view.findViewById(R.id.btn_cancel);
        Button saveBut = view.findViewById(R.id.btn_save_back);
        final TextView tvMainText = view.findViewById(R.id.tv_main_text);
        final EditText etCatalogName = view.findViewById(R.id.et_catalog_name);
        final EditText etCatalogCategory = view.findViewById(R.id.et_catalog_category);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(view).create();
        tvMainText.setText(R.string.add_catalog);
        alertDialog.show();
        saveBut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                alertDialog.hide();
                addCatalogViewModel.addNewCatalog(etCatalogName.getText().toString(), etCatalogCategory.getText().toString());
            }
        });

        cancelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });
    }



    public void showEditDialog(final int position){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.alert_catalog, null);
        Button cancelBut = view.findViewById(R.id.btn_cancel);
        Button saveBut = view.findViewById(R.id.btn_save_back);
        final EditText etCatalogName = view.findViewById(R.id.et_catalog_name);
        final EditText etCatalogCategory = view.findViewById(R.id.et_catalog_category);
        etCatalogCategory.setText(catalogsList.get(position).getCategory());
        etCatalogName.setText(catalogsList.get(position).getName());
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(view).create();
        alertDialog.show();
        saveBut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i("EDIT", String.valueOf(position));
                alertDialog.hide();
                editCatalogViewModel.editCatalog(editCatalogViewModel.getEditedCatalog().getCID() ,etCatalogName.getText().toString(), etCatalogCategory.getText().toString());
            }
        });

        cancelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });
    }
}

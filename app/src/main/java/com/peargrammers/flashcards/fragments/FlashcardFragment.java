package com.peargrammers.flashcards.fragments;


import android.app.AlertDialog;
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
import com.peargrammers.flashcards.FlashcardAdapter;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.RecyclerViewClickInterface;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.viewmodels.management.AddFlashcardViewModel;
import com.peargrammers.flashcards.viewmodels.management.EditCatalogViewModel;
import com.peargrammers.flashcards.viewmodels.management.EditFlashcardViewModel;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlashcardFragment extends Fragment implements RecyclerViewClickInterface {

    private FlashcardsViewModel flashcardsViewModel;
    private AddFlashcardViewModel addFlashcardViewModel;
    private FloatingActionButton floatingActionButton;
    private final FragmentActivity flashcardActivity = getActivity();
    private EditFlashcardViewModel editFlashcardViewModel;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Flashcard> flashcardsList = new ArrayList<>();

    private Flashcard removedFlashcard = null;

    public FlashcardFragment() {
        flashcardsViewModel = FlashcardsViewModel.getInstance();
        addFlashcardViewModel = AddFlashcardViewModel.getInstance();
        editFlashcardViewModel = EditFlashcardViewModel.getInstance();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_flashcard, container, false);
    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.rv_flashcards);
        mRecyclerView.setHasFixedSize(true);
        floatingActionButton  = view.findViewById(R.id.fab_add_flashcard);
        TextView tvCurrentCatalog = view.findViewById(R.id.tv_current_catalog);
        tvCurrentCatalog.setText(flashcardsViewModel.getCurrentCatalog().getName());

        final RecyclerViewClickInterface rvci = this;
        flashcardsViewModel.getFlashcardsList().observe(FlashcardFragment.this, new Observer<ArrayList<Flashcard>>() {
            @Override
            public void onChanged(ArrayList<Flashcard> flashcards) {
                flashcardsList.clear();
                flashcardsList.addAll(flashcards);
                mLayoutManager = new LinearLayoutManager(flashcardActivity);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mAdapter = new FlashcardAdapter(flashcardsList, rvci);
                mRecyclerView.setAdapter(mAdapter);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddCardDialog();
            }
        });


        flashcardsViewModel.getFlashcardsListDB(flashcardsViewModel.getCurrentCatalog().getCID());
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
                    EditFlashcardViewModel.getInstance().setEditedFlashcard(flashcardsList.get(position));
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

    public void showEditDialog(final int position){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.alert_flashcard, null);
        Button cancelBut = view.findViewById(R.id.btn_cancel);
        Button saveBut = view.findViewById(R.id.btn_save_back);
        final EditText etFrontside = view.findViewById(R.id.et_flashcard_frontside);
        final EditText etBackside = view.findViewById(R.id.et_flashcard_backside);
        etFrontside.setText(flashcardsList.get(position).getFrontside());
        etBackside.setText(flashcardsList.get(position).getBackside());
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(view).create();
        alertDialog.show();
        saveBut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i("EDIT", String.valueOf(position));
                alertDialog.hide();
                editFlashcardViewModel.editFlashcardFromCatalod(flashcardsViewModel.getCurrentCatalog().getCID(), editFlashcardViewModel.getEditedFlashcard().getFID(), etFrontside.getText().toString(), etBackside.getText().toString());
            }
        });

        cancelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });
    }

    public void showAddCardDialog(){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.alert_flashcard, null);
        Button cancelBut = view.findViewById(R.id.btn_cancel);
        Button saveBut = view.findViewById(R.id.btn_save_back);
        final TextView tvMainText = view.findViewById(R.id.tv_main_text);
        final EditText etFrontside = view.findViewById(R.id.et_flashcard_frontside);
        final EditText etBackside = view.findViewById(R.id.et_flashcard_backside);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(view).create();
        tvMainText.setText(R.string.add_flashcard);
        alertDialog.show();
        saveBut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                alertDialog.hide();
                addFlashcardViewModel.addFlashcardToCatalog(flashcardsViewModel.getCurrentCatalog().getCID(), new Flashcard(etFrontside.getText().toString(), etBackside.getText().toString()));
            }
        });

        cancelBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.hide();
            }
        });
    }


    public void showRemoveDialog(final int position){
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View view = layoutInflater.inflate(R.layout.alert_remove_flashcard, null);
        Button acceptBut = view.findViewById(R.id.accept);
        Button declineBut = view.findViewById(R.id.decline);
        final AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setView(view).create();
        alertDialog.show();
        acceptBut.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Log.i("REMOVE", String.valueOf(position));
                alertDialog.hide();
                flashcardsViewModel.removeFlashcardFromCatalog(flashcardsViewModel.getCurrentCatalog().getCID(), flashcardsList.get(position).getFID());
                removedFlashcard = flashcardsList.remove(position);
                Snackbar.make(mRecyclerView, "Flashcard has been removed.", Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        addFlashcardViewModel.addFlashcardToCatalog(flashcardsViewModel.getCurrentCatalog().getCID(),removedFlashcard);
                        flashcardsList.add(position, removedFlashcard);
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


}

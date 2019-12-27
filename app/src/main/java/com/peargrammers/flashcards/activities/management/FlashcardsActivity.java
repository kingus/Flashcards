package com.peargrammers.flashcards.activities.management;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.peargrammers.flashcards.FlashcardAdapter;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.RecyclerViewClickInterface;
import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.viewmodels.management.AddFlashcardViewModel;
import com.peargrammers.flashcards.viewmodels.management.EditFlashcardViewModel;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;

import java.util.ArrayList;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class FlashcardsActivity extends AppCompatActivity implements RecyclerViewClickInterface{

    private TextView tv_current_catalog;
    private FlashcardsViewModel flashcardsViewModel;
    private AddFlashcardViewModel addFlashcardViewModel;
    private FloatingActionButton fab_add_flashcard;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<Flashcard> flashcardsList = new ArrayList<>();

    private Flashcard removedFlashcard = null;



    public FlashcardsActivity() {
        flashcardsViewModel = FlashcardsViewModel.getInstance();
        addFlashcardViewModel = AddFlashcardViewModel.getInstance();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashcards);

        tv_current_catalog = findViewById(R.id.tv_current_catalog);

        tv_current_catalog.setText(flashcardsViewModel.getCurrentCatalog().getName());

        mRecyclerView = findViewById(R.id.rv_flashcards);
        mRecyclerView.setHasFixedSize(true);
        fab_add_flashcard  = findViewById(R.id.fab_add_flashcard);
        final RecyclerViewClickInterface rvci = this;
        flashcardsViewModel.getFlashcardsList().observe(FlashcardsActivity.this, new Observer<ArrayList<Flashcard>>() {
            @Override
            public void onChanged(ArrayList<Flashcard> flashcards) {
                flashcardsList.clear();
                flashcardsList.addAll(flashcards);
                mLayoutManager = new LinearLayoutManager(FlashcardsActivity.this);
                mRecyclerView.setLayoutManager(mLayoutManager);

                mAdapter = new FlashcardAdapter(flashcardsList,  rvci);
                mRecyclerView.setAdapter(mAdapter);
            }
        });

        fab_add_flashcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(FlashcardsActivity.this, AddFlashcardActivity.class);
                startActivity(myIntent);
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
                    Log.i("REMOVE", String.valueOf(position));
                    flashcardsViewModel.removeFlashcardFromCatalog(flashcardsViewModel.getCurrentCatalog().getCID(), flashcardsList.get(position).getFID());
                    removedFlashcard = flashcardsList.remove(position);

                    //deletedCatalog = method removing the catalog and returning a catalog
                    //mAdapter.notifyItemRemoved(position);
                    Snackbar.make(mRecyclerView, "Undo", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener(){
                                @Override
                                public void onClick(View v) {
                                    //method that'll add removed catalog to the list
                                    System.out.println("UNDO CLICKED");
                                    addFlashcardViewModel.addFlashcardToCatalog(flashcardsViewModel.getCurrentCatalog().getCID(),removedFlashcard);
                                    flashcardsList.add(position, removedFlashcard);
                                    //NEEDED
                                    //mAdapter.notifyItemInserted();
                                }
                            }).show();
                    break;
                case ItemTouchHelper.RIGHT:
                    //EditCatalogActivity editCatalogActivity = EditCatalogActivity.get
                    EditFlashcardViewModel.getInstance().setEditedFlashcard(flashcardsList.get(position));
                    Intent myIntent = new Intent(FlashcardsActivity.this, EditFlashcardActivity.class);
                    startActivity(myIntent);

                    break;
            }
        }

        @Override
        public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
            new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeLeftBackgroundColor(ContextCompat.getColor(FlashcardsActivity.this, R.color.leadningColor))
                    .addSwipeLeftActionIcon(R.drawable.ic_delete)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(FlashcardsActivity.this, R.color.leadningColor))
                    .addSwipeRightActionIcon(R.drawable.ic_edit)
                    .create()
                    .decorate();
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        }
    };
    @Override
    public void onItemClick(int position) {
        //Catalog clicked
        //ManageFlashcardsRepository.getInstance().addFlashcardToCatalog("-Lx2q2n_CEUgZrRCrazX", new Flashcard("kot", "kot"));
        //FlashcardsViewModel.getInstance().setCurrentCatalog(flashcardsList.get(position));
//        Intent myIntent = new Intent(FlashcardsActivity.this   , FlashcardsActivity.class);
//        startActivity(myIntent);


    }

}

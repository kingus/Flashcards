package com.peargrammers.flashcards.activities.management;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.repositories.management.ManageFlashcardsRepository;
import com.peargrammers.flashcards.viewmodels.management.EditCatalogViewModel;
import com.peargrammers.flashcards.viewmodels.management.EditFlashcardViewModel;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;

public class EditFlashcardActivity extends AppCompatActivity {

    private EditFlashcardViewModel editFlashcardViewModel;
    private FlashcardsViewModel flashcardsViewModel;
    private Button btn_edit_flashcard_and_back, btn_cancel;
    private EditText et_edit_flashcard_frontside, et_edit_flashcard_backside;

    public EditFlashcardActivity() {
        editFlashcardViewModel = EditFlashcardViewModel.getInstance();
        flashcardsViewModel = FlashcardsViewModel.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_flashcard);



        btn_edit_flashcard_and_back = findViewById(R.id.btn_edit_flashcard_and_back);
        btn_cancel = findViewById(R.id.btn_cancel);
        et_edit_flashcard_frontside = findViewById(R.id.et_edit_flashcard_frontside);
        et_edit_flashcard_backside = findViewById(R.id.et_edit_flashcard_backside);

        et_edit_flashcard_frontside.setText(editFlashcardViewModel.getEditedFlashcard().getFrontside());
        et_edit_flashcard_backside.setText(editFlashcardViewModel.getEditedFlashcard().getBackside());

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_edit_flashcard_and_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editFlashcardViewModel.getIfEditFlashcardProprly().observe(EditFlashcardActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean) {

                            Toast.makeText(EditFlashcardActivity.this, "Catalog has been edited :)." , Toast.LENGTH_SHORT).show();
//                            et_new_cataog_name.setText("");
//                            et_new_catalog_catgory.setText("");

                            Intent myIntent = new Intent(EditFlashcardActivity.this, HomeActivity.class);
                            startActivity(myIntent);

                        } else {
                            Toast.makeText(EditFlashcardActivity.this, "Oupss, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        //usuwamy listenera przed nastepnym sluchaniem
                        editFlashcardViewModel.getIfEditFlashcardProprly().removeObservers(EditFlashcardActivity.this);
                    }
                });
                editFlashcardViewModel.editFlashcardFromCatalod(flashcardsViewModel.getCurrentCatalog().getCID(), editFlashcardViewModel.getEditedFlashcard().getFID(), et_edit_flashcard_frontside.getText().toString(), et_edit_flashcard_backside.getText().toString());
            }
        });


    }
}

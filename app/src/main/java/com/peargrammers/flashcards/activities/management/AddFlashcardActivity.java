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
import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.viewmodels.management.AddFlashcardViewModel;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;

public class AddFlashcardActivity extends AppCompatActivity {

    private AddFlashcardViewModel addFlashcardViewModel;
    private FlashcardsViewModel flashcardsViewModel;
    private Button btn_save_flashcard_and_add_next, btn_save_flashcard_and_back, btn_cancel;
    private EditText et_new_flashcard_frontside, et_new_flashcard_backside;

    public AddFlashcardActivity() {
        addFlashcardViewModel = AddFlashcardViewModel.getInstance();
        flashcardsViewModel = FlashcardsViewModel.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flashcard);

        btn_save_flashcard_and_add_next = findViewById(R.id.btn_save_flashcard_and_add_next);
        btn_save_flashcard_and_back = findViewById(R.id.btn_save_flashcard_and_back);
        btn_cancel = findViewById(R.id.btn_cancel);
        et_new_flashcard_frontside = findViewById(R.id.et_new_flashcard_frontside);
        et_new_flashcard_backside = findViewById(R.id.et_new_flashcard_backside);
        btn_save_flashcard_and_add_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFlashcardViewModel.getIfAddFlashcardProperly().observe(AddFlashcardActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean) {

                            Toast.makeText(AddFlashcardActivity.this, "Flashcard has been added :).", Toast.LENGTH_SHORT).show();



                        } else {
                            Toast.makeText(AddFlashcardActivity.this, "Oupss, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        //usuwamy listenera przed nastepnym sluchaniem
                        addFlashcardViewModel.getIfAddFlashcardProperly().removeObservers(AddFlashcardActivity.this);

                    }
                });
                addFlashcardViewModel.addFlashcardToCatalog(flashcardsViewModel.getCurrentCatalog().getCID(), new Flashcard(et_new_flashcard_frontside.getText().toString(), et_new_flashcard_backside.getText().toString()));
                et_new_flashcard_frontside.setText("");
                et_new_flashcard_backside.setText("");
            }

        });
        btn_save_flashcard_and_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFlashcardViewModel.getIfAddFlashcardProperly().observe(AddFlashcardActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean) {

                            Toast.makeText(AddFlashcardActivity.this, "Flashcard has been added :)." , Toast.LENGTH_SHORT).show();
//                            et_new_cataog_name.setText("");
//                            et_new_catalog_catgory.setText("");

                            Intent myIntent = new Intent(AddFlashcardActivity.this, HomeActivity.class);
                            startActivity(myIntent);

                        } else {
                            Toast.makeText(AddFlashcardActivity.this, "Oupss, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        //usuwamy listenera przed nastepnym sluchaniem
                        addFlashcardViewModel.getIfAddFlashcardProperly().removeObservers(AddFlashcardActivity.this);
                    }
                });
                addFlashcardViewModel.addFlashcardToCatalog(flashcardsViewModel.getCurrentCatalog().getCID(), new Flashcard(et_new_flashcard_frontside.getText().toString(), et_new_flashcard_backside.getText().toString()));
            }
        });


        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}

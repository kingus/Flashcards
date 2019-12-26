package com.peargrammers.flashcards.activities;

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
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.viewmodels.management.AddCatalogViewModel;
import com.peargrammers.flashcards.viewmodels.management.EditCatalogViewModel;

public class EditCatalogActivity extends AppCompatActivity {

    private EditCatalogViewModel editCatalogViewModel;
    private Button btn_edit_catalog_and_back, btn_cancel;
    private EditText et_edit_cataog_name, et_edit_catalog_catgory;


    public EditCatalogActivity() {
        editCatalogViewModel = EditCatalogViewModel.getInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_catalog);

        btn_edit_catalog_and_back = findViewById(R.id.btn_edit_catalog_and_back);
        btn_cancel = findViewById(R.id.btn_cancel);
        et_edit_cataog_name = findViewById(R.id.et_edit_cataog_name);
        et_edit_catalog_catgory = findViewById(R.id.et_edit_catalog_catgory);

        et_edit_cataog_name.setText(editCatalogViewModel.getEditedCatalog().getName());
        et_edit_catalog_catgory.setText(editCatalogViewModel.getEditedCatalog().getCategory());

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_edit_catalog_and_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editCatalogViewModel.getIfEditCatalogProperly().observe(EditCatalogActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean) {

                            Toast.makeText(EditCatalogActivity.this, "Catalog has been edited :)." , Toast.LENGTH_SHORT).show();
//                            et_new_cataog_name.setText("");
//                            et_new_catalog_catgory.setText("");

                            Intent myIntent = new Intent(EditCatalogActivity.this, HomeActivity.class);
                            startActivity(myIntent);

                        } else {
                            Toast.makeText(EditCatalogActivity.this, "Oupss, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        //usuwamy listenera przed nastepnym sluchaniem
                        editCatalogViewModel.getIfEditCatalogProperly().removeObservers(EditCatalogActivity.this);
                    }
                });
                editCatalogViewModel.editCatalog(editCatalogViewModel.getEditedCatalog().getCID() ,et_edit_cataog_name.getText().toString(), et_edit_catalog_catgory.getText().toString());
            }
        });
    }
}

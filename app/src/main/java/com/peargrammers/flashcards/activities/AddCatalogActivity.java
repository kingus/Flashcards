package com.peargrammers.flashcards.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.authentication.LoginActivity;
import com.peargrammers.flashcards.activities.authentication.RegisterActivity;
import com.peargrammers.flashcards.viewmodels.management.AddCatalogViewModel;

public class AddCatalogActivity extends AppCompatActivity {


    private AddCatalogViewModel addCatalogViewModel;
    private Button btn_save_catalog_and_back, btn_save_catalog_and_add_next, btn_cancel;
    private EditText et_new_cataog_name, et_new_catalog_catgory;

    public AddCatalogActivity() {
        addCatalogViewModel = AddCatalogViewModel.getInstance();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_catalog);

        btn_save_catalog_and_back = findViewById(R.id.btn_save_catalog_and_back);
        btn_save_catalog_and_add_next = findViewById(R.id.btn_save_catalog_and_add_next);
        btn_cancel = findViewById(R.id.btn_cancel);
        et_new_cataog_name = findViewById(R.id.et_new_cataog_name);
        et_new_catalog_catgory = findViewById(R.id.et_new_catalog_catgory);

        btn_save_catalog_and_add_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCatalogViewModel.getIfAddCatalogProperly().observe(AddCatalogActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean) {

                            Toast.makeText(AddCatalogActivity.this, "Catalog has been added :).", Toast.LENGTH_SHORT).show();
                            et_new_cataog_name.setText("");
                            et_new_catalog_catgory.setText("");


                        } else {
                            Toast.makeText(AddCatalogActivity.this, "Oupss, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        //usuwamy listenera przed nastepnym sluchaniem
                        addCatalogViewModel.getIfAddCatalogProperly().removeObservers(AddCatalogActivity.this);

                    }
                });
                addCatalogViewModel.addNewCatalog(et_new_cataog_name.getText().toString(), et_new_catalog_catgory.getText().toString());

            }

        });


        btn_save_catalog_and_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCatalogViewModel.getIfAddCatalogProperly().observe(AddCatalogActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if (aBoolean) {

                            Toast.makeText(AddCatalogActivity.this, "Catalog has been added :)." , Toast.LENGTH_SHORT).show();
//                            et_new_cataog_name.setText("");
//                            et_new_catalog_catgory.setText("");

                            Intent myIntent = new Intent(AddCatalogActivity.this, HomeActivity.class);
                            startActivity(myIntent);

                        } else {
                            Toast.makeText(AddCatalogActivity.this, "Oupss, something went wrong", Toast.LENGTH_SHORT).show();
                        }
                        //usuwamy listenera przed nastepnym sluchaniem
                        addCatalogViewModel.getIfAddCatalogProperly().removeObservers(AddCatalogActivity.this);
                    }
                });
                addCatalogViewModel.addNewCatalog(et_new_cataog_name.getText().toString(), et_new_catalog_catgory.getText().toString());
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

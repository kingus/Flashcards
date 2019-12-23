package com.peargrammers.flashcards.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.authentication.NavBar;

public class ContactActivity  extends AppCompatActivity {


    SpaceNavigationView navigationView;

    public ContactActivity() {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_activity);
        navigationView = findViewById(R.id.space);
        NavBar.setNavBar(navigationView);

        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                Intent myIntent = new Intent(ContactActivity.this, HomeActivity.class);
                startActivity(myIntent);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                Intent myIntent = new Intent(ContactActivity.this, NavBar.setContext(ContactActivity.class, itemIndex));
                startActivity(myIntent);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });

    }
}

package com.peargrammers.flashcards.activities.authentication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.viewmodels.management.HomeViewModel;

public class HomeActivity extends AppCompatActivity {

    HomeViewModel homeViewModel;
    private TextView tv_username;

    public HomeActivity() {
        this.homeViewModel = HomeViewModel.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        tv_username = findViewById(R.id.tv_username);

        homeViewModel.getUserEmail().observe(HomeActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                tv_username.setText(s);
            }
        });

        homeViewModel.getCurrentUserEmail();




    }
}

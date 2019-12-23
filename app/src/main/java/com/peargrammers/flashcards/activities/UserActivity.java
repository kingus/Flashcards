package com.peargrammers.flashcards.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;
import android.widget.TextView;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.models.User;
import com.peargrammers.flashcards.viewmodels.management.UserViewModel;

public class UserActivity extends AppCompatActivity {

    UserViewModel userViewModel;
    TextView tv_name, tv_email, tv_catalogs_amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        userViewModel = UserViewModel.getInstance();
        tv_name = findViewById(R.id.tv_name);
        tv_email = findViewById(R.id.tv_email);
        tv_catalogs_amount = findViewById(R.id.tv_catalogs_amount);

        userViewModel.getLoggedUser().observe(UserActivity.this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tv_name.setText(user.getName());
                tv_email.setText(user.getEmail());
                tv_catalogs_amount.setText(Integer.toString(user.getCatalogs().size()));
            }
        });

        userViewModel.getLoggedUserInfo();

    }
}

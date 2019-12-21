package com.peargrammers.flashcards.activities.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.viewmodels.authentication.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;
    private EditText mEmail, mPassword;
    private Button btnSignIn;

    public LoginActivity() {
        this.loginViewModel = LoginViewModel.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_sign_up);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                Log.i("email", email);
                Log.i("password", password);
                loginViewModel.getSigngInStatus().observe(LoginActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(Boolean aBoolean) {
                        if (aBoolean) {
                            Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                            startActivity(myIntent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Złe hasłko czy coś :(.",Toast.LENGTH_SHORT).show();

                        }
                    }

                });
                loginViewModel.signIn(email, password);

            }
        });



    }
}

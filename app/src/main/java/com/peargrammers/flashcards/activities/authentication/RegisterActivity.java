package com.peargrammers.flashcards.activities.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.viewmodels.authentication.RegisterViewModel;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;

    private EditText mName, mEmail, mPassword1, mPassword2;
    private Button btnSignUp, btnSignIn;

    public RegisterActivity() {
        registerViewModel = RegisterViewModel.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mName = findViewById(R.id.et_name);
        mEmail = findViewById(R.id.et_email);
        mPassword1 = findViewById(R.id.et_password1);
        mPassword2 = findViewById(R.id.et_password2);
        btnSignUp = findViewById(R.id.btn_sign_up);
        btnSignIn  = findViewById(R.id.btn_sign_in);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(myIntent);
            }
        });

        registerViewModel.getSigngUpStatus().observe(RegisterActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    Toast.makeText(RegisterActivity.this, "New user heas been registered.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "Ouuups, something went wrong...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String email = mEmail.getText().toString();
                String password1 = mPassword1.getText().toString();
                String password2 = mPassword2.getText().toString();
                Log.i("email", email);
                Log.i("password", password1);
                if (registerViewModel.validateUserRegister(email, password1, password2)) {

                    //przeniesiono obserwacje z tego miejsca nad listener
                    registerViewModel.createAccount(name, email, password1);

                }
            }
        });


    }

    public void onStart() {
        super.onStart();
    }

}

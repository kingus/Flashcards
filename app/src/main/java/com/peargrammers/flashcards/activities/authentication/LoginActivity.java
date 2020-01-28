package com.peargrammers.flashcards.activities.authentication;

import android.app.ProgressDialog;
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
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.viewmodels.authentication.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private EditText mEmail, mPassword;
    private Button btnSignIn, btnSignUp;
    private ProgressDialog dialog;
    private boolean ifLoginClicked;

    public LoginActivity() {
        this.loginViewModel = LoginViewModel.getInstance();
        ifLoginClicked = false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        mEmail = findViewById(R.id.et_email);
        mPassword = findViewById(R.id.et_password);
        btnSignIn = findViewById(R.id.btn_sign_in);
        btnSignUp  = findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(myIntent);
            }
        });


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                if(loginViewModel.checkEmailAndPasswdLength(email, password)) {
                    dialog = ProgressDialog.show(LoginActivity.this, "", "Please Wait...");

                    Log.i("email", email);
                    Log.i("password", password);
                    ifLoginClicked = true;
                    loginViewModel.signIn(email, password);
                }else{
                    Toast.makeText(LoginActivity.this, "Email or password is too short.", Toast.LENGTH_SHORT).show();

                }
                Log.i("email", email);
                Log.i("password", password);
            }
        });

        loginViewModel.getSigngInStatus().observe(LoginActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (ifLoginClicked){
                    ifLoginClicked = false;
                    if (aBoolean) {
                        Log.d("LOGIN_ACTIVITY", "LOGIN GIT - activity");
                        //if ()
                        dialog.dismiss();
                        mEmail.setText("");
                        mPassword.setText("");
                        Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(myIntent);
                    } else {
                        Log.d("LOGIN_ACTIVITY", "LOGIN ZLE - activity");
                    }
                }
            }

        });
        loginViewModel.getSignInException().observe(LoginActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                dialog.dismiss();
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

}

}



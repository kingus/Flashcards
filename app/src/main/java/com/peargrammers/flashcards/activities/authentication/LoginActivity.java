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
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.viewmodels.authentication.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    LoginViewModel loginViewModel;
    private EditText mEmail, mPassword;
    private Button btnSignIn, btnSignUp;

    public LoginActivity() {
        this.loginViewModel = LoginViewModel.getInstance();
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
                Log.i("email", email);
                Log.i("password", password);

                loginViewModel.signIn(email, password);

            }
        });

        loginViewModel.getSigngInStatus().observe(LoginActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Log.d("LOGIN_ACTIVITY", "LOGIN GIT - activity");

                    Intent myIntent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(myIntent);
                } else {
                    Log.d("LOGIN_ACTIVITY", "LOGIN ZLE - activity");

                    //Toast.makeText(LoginActivity.this, "Złe hasłko czy coś :(.",Toast.LENGTH_SHORT).show();

                }
                //loginViewModel.getSignInStatus().removeObservers(LoginActivity.this);
            }

        });
        loginViewModel.getSignInException().observe(LoginActivity.this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });

}
}

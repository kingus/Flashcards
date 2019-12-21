package com.peargrammers.flashcards.activities.authentication;

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

    RegisterViewModel registerViewModel;

    private EditText mEmail, mPassword;
    private Button btnSignUp;

    public RegisterActivity() {
        registerViewModel = RegisterViewModel.getInstance();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        mEmail = (EditText) findViewById(R.id.et_email);
        mPassword = (EditText) findViewById(R.id.et_password);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString();
                String password = mPassword.getText().toString();
                Log.i("email", email);
                Log.i("password", password);
                registerViewModel.getSigngUpStatus().observe(RegisterActivity.this, new Observer<Boolean>() {
                    @Override
                    public void onChanged(@Nullable Boolean aBoolean) {
                        if(aBoolean){
                            Toast.makeText(RegisterActivity.this, "Supi.",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(RegisterActivity.this, "Nie dziala pysiu.",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                registerViewModel.createAccount(email, password);
            }
        });
    }



    public void onStart() {
        super.onStart();
    }

}

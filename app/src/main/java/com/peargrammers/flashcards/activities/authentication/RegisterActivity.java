package com.peargrammers.flashcards.activities.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.repositories.authentication.RegisterRepository;
import com.peargrammers.flashcards.viewmodels.authentication.RegisterViewModel;

import static android.content.ContentValues.TAG;

public class RegisterActivity extends AppCompatActivity {

    RegisterViewModel registerViewModel;

    private EditText mEmail, mPassword;
    private Button btnSignUp;

    public RegisterActivity() {
        registerViewModel = RegisterViewModel.getInstance();
        System.out.println("regsiter activity constructor");
        Log.i("register", "regsiter activity constructor");
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
                registerViewModel.getLogginSuccess().observe(RegisterActivity.this, new Observer<Boolean>() {
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




//                if (registerViewModel.createAccount(email, password)) {
//                    Toast.makeText(RegisterActivity.this, "Supi.",Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(RegisterActivity.this, "Nie dziala pysiu.",Toast.LENGTH_SHORT).show();
//
//                }
//            }

            }
        });
    }



    public void onStart() {
        super.onStart();
        System.out.println("nanana");
       // createAccount("exmaplemail@domain.com", "password");
    }

//    public void  createAccount(String email, String password){
//        RegisterViewModel.getInstance().getRegisterRepository().getmAuth().createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d(TAG, "createUserWithEmail:success");
//
//                            FirebaseUser user = RegisterViewModel.getInstance().getRegisterRepository().getmAuth().getCurrentUser();
//                        } else {
//                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
//                        }
//
//                    }
//                });
//    }
}

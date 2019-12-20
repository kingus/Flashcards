package com.peargrammers.flashcards.repositories.authentication;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.peargrammers.flashcards.activities.authentication.RegisterActivity;

import java.util.concurrent.Executor;

import static android.content.ContentValues.TAG;

public class RegisterRepository {
    private FirebaseAuth mAuth;
    private MutableLiveData<Boolean> logginSuccess = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLogginSuccess() {
        return logginSuccess;
    }

    public FirebaseAuth getmAuth() {
        return mAuth;
    }

    public RegisterRepository() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    private static RegisterRepository instance;

    public static RegisterRepository getInstance() {
        if (instance == null) {
            instance = new RegisterRepository();
        }
        return instance;
    }


    public void register() {
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Log.i("RegisterViewModel", "****niezalogowany****");
        } else {
            Log.i("RegisterViewModel", "****zalogowany****");
        }
//        updateUI(currentUser);
    }

    public void createAccount(String email, String password) {
        Task<AuthResult> task = mAuth.createUserWithEmailAndPassword(email, password);

        task.addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    logginSuccess.postValue(true);
                   // tu musi byc live data i wyemitowanie w gore zmiany

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    logginSuccess.postValue(false);
                }
            }
        });

    }
}
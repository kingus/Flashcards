package com.peargrammers.flashcards.repositories.authentication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static android.content.ContentValues.TAG;

public class RegisterRepository {
    private static RegisterRepository instance;
    private FirebaseAuth mAuth;
    private MutableLiveData<Boolean> signgUpStatus = new MutableLiveData<>();

    public MutableLiveData<Boolean> getSigngUpStatus() {
        return signgUpStatus;
    }

    public RegisterRepository() {
        this.mAuth = FirebaseAuth.getInstance();
    }


    public static RegisterRepository getInstance() {
        if (instance == null) {
            instance = new RegisterRepository();
        }
        return instance;
    }


    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");
                    FirebaseUser user = mAuth.getCurrentUser();
                    signgUpStatus.postValue(true);

                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    signgUpStatus.postValue(false);
                }
            }
        });

    }
}
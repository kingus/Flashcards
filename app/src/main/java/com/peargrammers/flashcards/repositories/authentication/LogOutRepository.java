package com.peargrammers.flashcards.repositories.authentication;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.peargrammers.flashcards.models.User;

public class LogOutRepository {
    private static LogOutRepository instance;
    private FirebaseAuth mAuth;
    public LogOutRepository() {
        this.mAuth = FirebaseAuth.getInstance();
    }


    public static LogOutRepository getInstance() {
        if (instance == null) {
            instance = new LogOutRepository();
        }
        return instance;
    }


    public void logOut() {
        new Thread() {
            @Override
            public void run() {
                super.run();
                mAuth.getInstance().signOut();

            }
        }.start();

    }
}

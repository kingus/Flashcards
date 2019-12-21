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

public class LoginRepository {
    private static LoginRepository instance;
    private FirebaseAuth mAuth;
    private MutableLiveData<Boolean> signgInStatus = new MutableLiveData<>();

    public MutableLiveData<Boolean> getSigngInStatus() {
        return signgInStatus;
    }

    public LoginRepository() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public static LoginRepository getInstance() {
        if (instance == null) {
            instance = new LoginRepository();
        }
        return instance;
    }
    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "LOGIN GIT");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.d(TAG, user.toString());
                            signgInStatus.postValue(true);

                        } else {
                            Log.w(TAG, "LOGIN ZLE", task.getException());
                            signgInStatus.postValue(false);
                        }
                    }
                });

    }


}

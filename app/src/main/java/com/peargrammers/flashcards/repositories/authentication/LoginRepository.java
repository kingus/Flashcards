package com.peargrammers.flashcards.repositories.authentication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginRepository {
    private static LoginRepository instance;
    private FirebaseAuth mAuth;
    private MutableLiveData<Boolean> signInStatus = new MutableLiveData<>();
    private MutableLiveData<String> signInException = new MutableLiveData<>();

    public MutableLiveData<Boolean> getSignInStatus() {
        return signInStatus;
    }

    public MutableLiveData<String> getSignInException() { return signInException; }

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
                            Log.d("LOGIN_REPOSITORY", "LOGIN GIT  - REPOSITORY");
                            //FirebaseUser user = mAuth.getCurrentUser();
                            //Log.d("LOGIN_REPOSITORY", user.toString());
                            signInStatus.postValue(true);

                        } else {
                            //Log.w(TAG, "LOGIN ZLE", task.getException());
                            Log.d("LOGIN_REPOSITORY", "LOGIN ZLE  - REPOZITORY");


                            signInStatus.postValue(false);
                            signInException.postValue(task.getException().getMessage());
                        }
                    }
                });

    }


}

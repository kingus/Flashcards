package com.peargrammers.flashcards.repositories.authentication;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.peargrammers.flashcards.models.User;

import static android.content.ContentValues.TAG;

public class RegisterRepository {
    private static RegisterRepository instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbUsersRef;
    private MutableLiveData<Boolean> signgUpStatus = new MutableLiveData<>();
    private OnCompleteListener onCompleteListener;
    private String myEmail;
    private String myPassword;

    public MutableLiveData<Boolean> getSigngUpStatus() {
        return signgUpStatus;
    }

    public RegisterRepository() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance();
        this.dbUsersRef = mDatabase.getReference("/USERS");

    }


    public static RegisterRepository getInstance() {
        if (instance == null) {
            instance = new RegisterRepository();
        }
        return instance;
    }


    public void createAccount(final String name, final String email, String password) {

        onCompleteListener =  new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "createUserWithEmail:success");



                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            FirebaseUser newUser = mAuth.getCurrentUser();
                            final Task<Void> voidTask = dbUsersRef.child(newUser.getUid()).setValue(new User(name, email));
                            voidTask.addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(voidTask.isSuccessful()) {
                                        Log.d(TAG, "createUserWithEmail: ADD USER TO DATABASE");
                                    } else {
                                        Log.d(TAG, "createUserWithEmail: ERROR WHILE ADDING USER TO DATABASE");

                                    }
                                }
                            });
                        }
                    }.start();


                    signgUpStatus.postValue(true);


                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                    signgUpStatus.postValue(false);
                }
            }
        };
        myEmail = email;
        myPassword = password;
        new Thread(){
            @Override
            public void run() {
                super.run();
                mAuth.createUserWithEmailAndPassword(myEmail, myPassword)
                        .addOnCompleteListener(onCompleteListener);

            }
        }.start();



    }
}
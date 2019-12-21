package com.peargrammers.flashcards.repositories.management;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserInfoRepository {
    private static UserInfoRepository instance;
    private FirebaseAuth mAuth;
    private MutableLiveData<String> userEmail = new MutableLiveData<>();

    public MutableLiveData<String> getUserEmail() {
        return userEmail;
    }

    public UserInfoRepository() {
        this.mAuth = FirebaseAuth.getInstance();

    }

    public static UserInfoRepository getInstance() {
        if (instance == null) {
            instance = new UserInfoRepository();
        }
        return instance;
    }

    public void getCurrentUserEmail() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // Name, email address, and profile photo Url
            String name = user.getDisplayName();
            String email = user.getEmail();
//            Uri photoUrl = user.getPhotoUrl();
//
            // Check if user's email is verified
//            boolean emailVerified = user.isEmailVerified();
//
//            Log.d("HOME_LOG_name", name);
  //          Log.d("HOME_LOG_email", email);

            userEmail.postValue(email);


            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
//            String uid = user.getUid();

        } else {
          //  Log.d("HOME_LOG", "nie ma usera :)");
            userEmail.postValue("default_username");

        }
    }
}

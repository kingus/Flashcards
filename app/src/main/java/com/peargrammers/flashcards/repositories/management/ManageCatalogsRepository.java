package com.peargrammers.flashcards.repositories.management;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ManagementRepository {
    private static ManagementRepository instance;
    private FirebaseAuth mAuth;

    public ManagementRepository() {
        this.mAuth = FirebaseAuth.getInstance();
    }

    public static ManagementRepository getInstance() {
        if (instance == null) {
            instance = new ManagementRepository();
        }
        return instance;
    }

    public String getCurrentUserEmail() {
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
            Log.d("HOME_LOG_name", name);
            Log.d("HOME_LOG_email", email);



            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
//            String uid = user.getUid();
            return email;
        } else {
            Log.d("HOME_LOG", "nie ma usera :)");
            return "DEFOULT_EMAIL";
        }
    }

}

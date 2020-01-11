package com.peargrammers.flashcards.repositories.management;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.models.User;

import java.util.HashMap;
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class UserInfoRepository {
    private static UserInfoRepository instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbUsersRef;
    private MutableLiveData<String> userEmail = new MutableLiveData<>();
    private MutableLiveData<User> loggedUser = new MutableLiveData<>();


    public MutableLiveData<User> getLoggedUser() { return loggedUser; }
    public MutableLiveData<String> getUserEmail() {
        return userEmail;
    }

    public UserInfoRepository() {
        this.mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        dbUsersRef = mDatabase.getReference("/USERS");

        /*
              to git
         myRef.child("UID"+mAuth.getCurrentUser().getUid()).child("name").setValue("Szymek");
        adding multiple posts at the same time
        Map<String, User> users = new HashMap<>();
        users.put("UID:"+mAuth.getCurrentUser().getUid(), new User("gruz@wp.org", "AlanTuring"));
        users.put("gracehop", new User("buziaczek@onet.pl", "Grace Hopper"));

        myRef.setValue(users);


        myRef.setValue(new User("grusza123@gmail.com", "Szymon"));
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);

                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
*/
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
            String name = user.getDisplayName();
            String email = user.getEmail();

            userEmail.postValue(email);


        } else {
            userEmail.postValue("default_username");

        }
    }

    public void getLoggedUserInfo() {
        //User loggedUser = dbUsersRef.child(mAuth.getUid());
        ValueEventListener loggedUserListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User loggedUserDB = dataSnapshot.getValue(User.class);
                loggedUserDB.setCreationTimestamp(mAuth.getCurrentUser().getMetadata().getCreationTimestamp());
                if (loggedUserDB.getCatalogs() == null) {
                    loggedUserDB.setCatalogs(new HashMap<String, Catalog>());
                }

                //Log.d(TAG, loggedUserDB.toString());
                loggedUser.postValue(loggedUserDB);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "OOOOUPS, SOMETHING WENT WRONG :(");

            }
        };
        Log.d(TAG, "sciezka do current USER" + dbUsersRef.child(mAuth.getUid()).toString());
        dbUsersRef.child(mAuth.getUid()).addListenerForSingleValueEvent(loggedUserListener);
    }
}

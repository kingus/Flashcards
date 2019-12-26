package com.peargrammers.flashcards.repositories.management;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.models.Flashcard;

import java.util.ArrayList;
import java.util.HashMap;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ManageCatalogsRepository {
    private static ManageCatalogsRepository instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbCurrentUserRef;

    private MutableLiveData<Boolean> ifAddCatalogProperly = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Catalog>> usersCatalogsList = new MutableLiveData<>();



    public ManageCatalogsRepository() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance();
        this.dbCurrentUserRef = mDatabase.getReference("/USERS/" + mAuth.getUid());
        Log.d(TAG, "################ sciezka do current USER CATALOGS" + this.dbCurrentUserRef.toString());

    }

    public static ManageCatalogsRepository getInstance() {
        if (instance == null) {
            instance = new ManageCatalogsRepository();
        }
        return instance;
    }

    public MutableLiveData<Boolean> getIfAddCatalogProperly() {
        return ifAddCatalogProperly;
    }

    public MutableLiveData<ArrayList<Catalog>> getUsersCatalogsList() {
        return usersCatalogsList;
    }

    public void getUsersCatalogsListDB() {
        ValueEventListener usersCatalogsListListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Catalog> catalogs = new ArrayList<>();
                for (DataSnapshot elem: dataSnapshot.getChildren()) {
                    Log.d(TAG, "################ DATASNAPSHOT" + elem.toString());

                    Catalog tmp = elem.getValue(Catalog.class);

                    tmp.setCID(elem.getKey());

                    if (tmp.getFlashcards() == null) {
                        tmp.setFlashcards(new HashMap<String, Flashcard>());
                    }
                    catalogs.add(tmp);
                }
                usersCatalogsList.postValue(catalogs);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dbCurrentUserRef.child("catalogs").orderByKey().addValueEventListener(usersCatalogsListListener);
    }

    public void addNewCatalog(String name, String category) {

        // do przerobienia, dodanie jeszcze do listy katalogow
        dbCurrentUserRef.child("catalogs").push().setValue(new Catalog(name, category)).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifAddCatalogProperly.postValue(true);
                } else {
                    ifAddCatalogProperly.postValue(true);
                }
            }
        });
    }
    public void removeCatalogFromList(String CID) {
        dbCurrentUserRef.child("catalogs").child(CID).removeValue();

    }

}

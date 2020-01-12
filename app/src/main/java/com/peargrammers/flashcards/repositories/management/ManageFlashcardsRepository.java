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
import java.util.Map;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ManageFlashcardsRepository {
    private static ManageFlashcardsRepository instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbCurrentUserRef;
    private DatabaseReference dbRef;
    private MutableLiveData<ArrayList<Flashcard>> flashcardsList = new MutableLiveData<>();
    private MutableLiveData<Boolean> ifAddFlashcardProperly = new MutableLiveData<>();
    private MutableLiveData<Boolean> ifRemovedFlashcardProperly = new MutableLiveData<>();
    private MutableLiveData<Boolean> ifEditedFlashcardProperly = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIfEditedFlashcardProperly() {
        return ifEditedFlashcardProperly;
    }

    public MutableLiveData<Boolean> getIfAddFlashcardProperly() {
        return ifAddFlashcardProperly;
    }

    public MutableLiveData<Boolean> getIfRemovedFlashcardProperly() {
        return ifRemovedFlashcardProperly;
    }

    public MutableLiveData<ArrayList<Flashcard>> getFlashcardsList() {
        return flashcardsList;
    }


    public ManageFlashcardsRepository() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance();
        this.dbCurrentUserRef = mDatabase.getReference("/USERS/" + mAuth.getUid());
        this.dbRef = mDatabase.getReference();

    }

    public static ManageFlashcardsRepository getInstance() {
        if (instance == null) {
            instance = new ManageFlashcardsRepository();
        }
        return instance;
    }

    public void addFlashcardToCatalog(String CID, Flashcard flashcard) {
        this.dbRef.child("CATALOGS").child(CID).child("flashcards").push().setValue(flashcard).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifAddFlashcardProperly.postValue(true);
                } else {
                    ifAddFlashcardProperly.postValue(false);
                }
            }
        });
    }
    public void getFlashcardsListDB(String CID) {
        ValueEventListener flashcardsListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Flashcard> flashcards = new ArrayList<>();
                for (DataSnapshot elem: dataSnapshot.getChildren()) {
                    Flashcard tmp = elem.getValue(Flashcard.class);

                    tmp.setFID(elem.getKey());

                    flashcards.add(tmp);
                }
                flashcardsList.postValue(flashcards);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        dbRef.child("CATALOGS").child(CID).child("flashcards").orderByChild("smallBox").addValueEventListener(flashcardsListener);
    }

    public void removeFlashcardFromCatalog(String CID, String FID) {
        dbRef.child("CATALOGS").child(CID).child("flashcards").child(FID).setValue(null).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifRemovedFlashcardProperly.setValue(true);
                } else {
                    ifRemovedFlashcardProperly.setValue(false);
                }
            }
        });
    }
    public void editFlashcardFromCatalod(String CID, String FID, String frontside, String backside) {
        //dbRef.child("CATALOGS").child(CID).child("flashcards")

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/CATALOGS/" + CID + "/flashcards/" + FID + "/frontside/", frontside);
        childUpdates.put("/CATALOGS/" + CID + "/flashcards/" + FID + "/backside/", backside);

        dbRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifEditedFlashcardProperly.postValue(true);
                } else {
                    ifEditedFlashcardProperly.postValue(false);
                }
            }
        });
    }


}

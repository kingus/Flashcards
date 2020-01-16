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
    private String myCID;
    private String myFID;
    private Flashcard myflashcard;

    private OnCompleteListener addFlashcardListener;
    private OnCompleteListener removeFlashcardFromCatalogListener;
    private OnCompleteListener removeFlashcardsFromCatalogListener;
    private OnCompleteListener editFlashcardFromCatalodListener;
    private OnCompleteListener editFlashcardLevelFromCatalodListener;
    Map<String, Object> EditFlashcardChildUpdates;
    Map<String, Object> flashcardToRemoveChildRemove;
    Map<String, Object> EditFlashcardLevelChildUpdates;
    ValueEventListener getFlashcardsListener;

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
        addFlashcardListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifAddFlashcardProperly.postValue(true);
                } else {
                    ifAddFlashcardProperly.postValue(false);
                }
            }
        };
        myCID = CID;
        myflashcard = flashcard;
        new Thread() {
            @Override
            public void run() {
                super.run();
                dbRef.child("CATALOGS").child(myCID).child("flashcards").push().setValue(myflashcard).addOnCompleteListener(addFlashcardListener);
            }
        }.start();

    }
    public void getFlashcardsListDB(String CID) {
        getFlashcardsListener = new ValueEventListener() {

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
        myCID = CID;
        //System.out.println("MOJE CID");
        new Thread() {
            @Override
            public void run() {
                super.run();
                dbRef.child("CATALOGS").child(myCID).child("flashcards").orderByChild("smallBox").addValueEventListener(getFlashcardsListener);
            }
        }.start();

    }
    public void removeFlashcardFromCatalog(String CID, String FID) {
        removeFlashcardFromCatalogListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifRemovedFlashcardProperly.setValue(true);
                } else {
                    ifRemovedFlashcardProperly.setValue(false);
                }
            }
        };
        myCID = CID;
        myFID = FID;
        new Thread(){
            @Override
            public void run() {
                super.run();
                dbRef.child("CATALOGS").child(myCID).child("flashcards").child(myFID).setValue(null).addOnCompleteListener(removeFlashcardFromCatalogListener);

            }
        }.start();
    }
    public void editFlashcardFromCatalod(String CID, String FID, String frontside, String backside) {
        //dbRef.child("CATALOGS").child(CID).child("flashcards")

        EditFlashcardChildUpdates = new HashMap<>();
        EditFlashcardChildUpdates.put("/CATALOGS/" + CID + "/flashcards/" + FID + "/frontside/", frontside);
        EditFlashcardChildUpdates.put("/CATALOGS/" + CID + "/flashcards/" + FID + "/backside/", backside);

        editFlashcardFromCatalodListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifEditedFlashcardProperly.postValue(true);
                } else {
                    ifEditedFlashcardProperly.postValue(false);
                }
            }
        };

        new Thread() {
            @Override
            public void run() {
                super.run();
                dbRef.updateChildren(EditFlashcardChildUpdates).addOnCompleteListener(editFlashcardFromCatalodListener);
            }
        }.start();

    }
    public void editFlashcardsLevelFromCatalog(String CID, ArrayList<Flashcard> flashcards) {
        //dbRef.child("CATALOGS").child(CID).child("flashcards")
        System.out.println("zaczynam edycje small box");
        System.out.println("mam do zmodyfikowania: " + flashcards.size());
        EditFlashcardLevelChildUpdates = new HashMap<>();
        for (Flashcard tmp :
                flashcards) {
            System.out.println("NR" + tmp.getSmallBox());
            EditFlashcardLevelChildUpdates.put("/CATALOGS/" + CID + "/flashcards/" + tmp.getFID() + "/smallBox/", tmp.getSmallBox());

        }

//        editFlashcardLevelFromCatalodListener = new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    ifEditedFlashcardProperly.postValue(true);
//                } else {
//                    ifEditedFlashcardProperly.postValue(false);
//                }
//            }
//        };

        new Thread() {
            @Override
            public void run() {
                super.run();
                dbRef.updateChildren(EditFlashcardLevelChildUpdates).addOnCompleteListener(editFlashcardLevelFromCatalodListener);
            }
        }.start();
    }

    public void removeFlashcardsFromCatalog(String CID, ArrayList<Flashcard> flashcards) {

        flashcardToRemoveChildRemove = new HashMap<>();
        for (Flashcard tmp :
                flashcards) {
            flashcardToRemoveChildRemove.put("/CATALOGS/" + CID + "/flashcards/" + tmp.getFID() + "/", null);


        }

//        removeFlashcardsFromCatalogListener = new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    ifRemovedFlashcardProperly.setValue(true);
//                } else {
//                    ifRemovedFlashcardProperly.setValue(false);
//                }
//            }
//        };
        myCID = CID;
        new Thread(){
            @Override
            public void run() {
                super.run();
                dbRef.updateChildren(flashcardToRemoveChildRemove);
//                dbRef.child("CATALOGS").child(myCID).child("flashcards").child(myFID).setValue(null).addOnCompleteListener(removeFlashcardsFromCatalogListener);

            }
        }.start();
    }


}

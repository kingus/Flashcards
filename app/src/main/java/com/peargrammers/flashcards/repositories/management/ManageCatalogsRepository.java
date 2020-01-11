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

public class ManageCatalogsRepository {
    private static ManageCatalogsRepository instance;
    private FirebaseAuth mAuth;
    private FirebaseDatabase mDatabase;
    private DatabaseReference dbCurrentUserRef;
    private DatabaseReference dbRef;

    private MutableLiveData<Boolean> ifAddCatalogProperly = new MutableLiveData<>();
    private MutableLiveData<Boolean> ifRemoveCatalogProperly = new MutableLiveData<>();
    private MutableLiveData<Boolean> ifEditedCatalogProperly = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Catalog>> usersCatalogsList = new MutableLiveData<>();



    public ManageCatalogsRepository() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance();
        this.dbCurrentUserRef = mDatabase.getReference("/USERS/" + mAuth.getUid());
        this.dbRef = mDatabase.getReference();
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

    public MutableLiveData<Boolean> getIfRemoveCatalogProperly() {
        return ifRemoveCatalogProperly;
    }

    public MutableLiveData<Boolean> getIfEditedCatalogProperly() {
        return ifEditedCatalogProperly;
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

        //przerobic na dodawnaie obiektu tak aby przy UNDO dodawały sie tez fiszki

        String key = dbCurrentUserRef.child("catalogs").push().getKey();
        Catalog catalog = new Catalog(name, category);
        Catalog catalogWithOwner = new Catalog(name, category, mAuth.getCurrentUser().getUid());


        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/CATALOGS/" + key, catalogWithOwner);
        childUpdates.put("/USERS/" +  mAuth.getUid() + "/catalogs/" + key, catalog);

        dbRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifAddCatalogProperly.postValue(true);
                } else {
                    ifAddCatalogProperly.postValue(true);
                }


//        DatabaseReference currentRef =  dbCurrentUserRef.child("catalogs").push();
//        String currentCID = currentRef.getKey();
//                currentRef.setValue(new Catalog(name, category)).addOnCompleteListener(new OnCompleteListener<Void>() {
//            @Override
//            public void onComplete(@NonNull Task<Void> task) {
//                if (task.isSuccessful()) {
//                    ifAddCatalogProperly.postValue(true);
//                } else {
//                    ifAddCatalogProperly.postValue(true);
//                }
            }
        });
    }
    public void removeCatalogFromList(String CID) {
        String key = dbCurrentUserRef.child("catalogs").child(CID).getKey();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/CATALOGS/" + key, null);
        childUpdates.put("/USERS/" +  mAuth.getUid() + "/catalogs/" + key, null);

        //dbCurrentUserRef.child("catalogs").child(CID).removeValue();
        dbRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifRemoveCatalogProperly.postValue(true);
                } else {
                    ifRemoveCatalogProperly.postValue(true);
                }
            }

         });
    }
    public void editCatalog(String CID, String name, String category) {
        //dbCurrentUserRef.child("catalogs").child(CID).removeValue();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/CATALOGS/" + CID + "/name", name);
        childUpdates.put("/CATALOGS/" + CID + "/category", category);
        childUpdates.put("/USERS/" +  mAuth.getUid() + "/catalogs/" + CID + "/name", name);
        childUpdates.put("/USERS/" +  mAuth.getUid() + "/catalogs/" + CID + "/category", category);

        dbRef.updateChildren(childUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifEditedCatalogProperly.postValue(true);
                } else {
                    ifEditedCatalogProperly.postValue(true);
                }
            }
        });
    }


}
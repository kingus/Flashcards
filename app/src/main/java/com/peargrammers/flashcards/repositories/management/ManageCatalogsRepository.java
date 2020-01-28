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
    private ValueEventListener usersCatalogsListListener;
    private OnCompleteListener addNewCatalogListener;
    private OnCompleteListener removeCatalogListener;
    private OnCompleteListener editCatalogListener;
    private Map<String, Object> childUpdates;
    private Map<String, Object> childRemove;
    private Map<String, Object> childEdit;
    private MutableLiveData<Boolean> ifAddCatalogProperly;
    private MutableLiveData<Boolean> ifRemoveCatalogProperly;
    private MutableLiveData<Boolean> ifEditedCatalogProperly;
    private MutableLiveData<ArrayList<Catalog>> usersCatalogsList;


    public ManageCatalogsRepository() {
        this.mAuth = FirebaseAuth.getInstance();
        this.mDatabase = FirebaseDatabase.getInstance();
        this.dbCurrentUserRef = mDatabase.getReference("/USERS/" + mAuth.getUid());
        this.dbRef = mDatabase.getReference();
        this.ifAddCatalogProperly = new MutableLiveData<>();
        this.ifRemoveCatalogProperly = new MutableLiveData<>();
        this.ifEditedCatalogProperly = new MutableLiveData<>();
        this.usersCatalogsList = new MutableLiveData<>();

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
        usersCatalogsListListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Catalog> catalogs = new ArrayList<>();
                for (DataSnapshot elem: dataSnapshot.getChildren()) {
                    //Log.d(TAG, "################ DATASNAPSHOT" + elem.toString());

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
        new Thread() {
            @Override
            public void run() {
                super.run();
                System.out.println("WYSZUKUJE FISZKI DAL USERA: " + mAuth.getUid());
                dbCurrentUserRef = mDatabase.getReference("/USERS/" + mAuth.getUid());
                dbCurrentUserRef.child("catalogs").orderByKey().addValueEventListener(usersCatalogsListListener);

            }
        }.start();
    }

    public void addNewCatalog(String name, String category) {

        //przerobic na dodawnaie obiektu tak aby przy UNDO dodawały sie tez fiszki

        String key = dbCurrentUserRef.child("catalogs").push().getKey();
        Catalog catalog = new Catalog(name, category);
        Catalog catalogWithOwner = new Catalog(name, category, mAuth.getCurrentUser().getUid());


        childUpdates = new HashMap<>();
        childUpdates.put("/CATALOGS/" + key, catalogWithOwner);
        childUpdates.put("/USERS/" +  mAuth.getUid() + "/catalogs/" + key, catalog);

        addNewCatalogListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifAddCatalogProperly.postValue(true);
                } else {
                    ifAddCatalogProperly.postValue(true);
                }

            }
        };
        new Thread() {
            @Override
            public void run() {
                super.run();
                dbRef.updateChildren(childUpdates).addOnCompleteListener(addNewCatalogListener);
            }
        }.start();


    }
    public void removeCatalogFromList(String CID) {
        String key = dbCurrentUserRef.child("catalogs").child(CID).getKey();

        childRemove = new HashMap<>();
        childRemove.put("/CATALOGS/" + key, null);
        childRemove.put("/USERS/" +  mAuth.getUid() + "/catalogs/" + key, null);

        removeCatalogListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifRemoveCatalogProperly.postValue(true);
                } else {
                    ifRemoveCatalogProperly.postValue(true);
                }
            }
        };

        //dbCurrentUserRef.child("catalogs").child(CID).removeValue();
        new Thread() {
            @Override
            public void run() {
                super.run();
                dbRef.updateChildren(childRemove).addOnCompleteListener(removeCatalogListener);

            }
        }.start();
    }
    public void editCatalog(String CID, String name, String category) {
        //dbCurrentUserRef.child("catalogs").child(CID).removeValue();

        childEdit = new HashMap<>();
        childEdit.put("/CATALOGS/" + CID + "/name", name);
        childEdit.put("/CATALOGS/" + CID + "/category", category);
        childEdit.put("/USERS/" +  mAuth.getUid() + "/catalogs/" + CID + "/name", name);
        childEdit.put("/USERS/" +  mAuth.getUid() + "/catalogs/" + CID + "/category", category);

        editCatalogListener = new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    ifEditedCatalogProperly.postValue(true);
                } else {
                    ifEditedCatalogProperly.postValue(true);
                }
            }
        };
        new Thread() {
            @Override
            public void run() {
                super.run();
                dbRef.updateChildren(childEdit).addOnCompleteListener(editCatalogListener);
            }
        }.start();

    }


}
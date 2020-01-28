package com.peargrammers.flashcards.viewmodels.management;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.repositories.management.ManageFlashcardsRepository;

import java.util.ArrayList;

public class FlashcardsViewModel {
    private static FlashcardsViewModel instance;
    private ManageFlashcardsRepository manageFlashcardsRepository;
    private MutableLiveData<ArrayList<Flashcard>> flashcardsList = new MutableLiveData<>();
    private MutableLiveData<Boolean> ifRemovedFlashcardProperly = new MutableLiveData<>();




    public MutableLiveData<Boolean> getIfRemovedFlashcardProperly() {
        return ifRemovedFlashcardProperly;
    }

    private Catalog currentCatalog;
    private int currentFlashcardIndex = 0;

    public Catalog getCurrentCatalog() {
        return currentCatalog;
    }

    public int getCurrentFlashcardIndex() {
        return currentFlashcardIndex;
    }

    public void setCurrentFlashcardIndex(int currentFlashcardIndex) {
        this.currentFlashcardIndex = currentFlashcardIndex;
    }

    public void setCurrentCatalog(Catalog currentCatalog) {
        this.currentCatalog = currentCatalog;
    }

    public MutableLiveData<ArrayList<Flashcard>>
    getFlashcardsList() {
        return flashcardsList;
    }

    public FlashcardsViewModel() {
        manageFlashcardsRepository = ManageFlashcardsRepository.getInstance();
        manageFlashcardsRepository.getFlashcardsList().observeForever(new Observer<ArrayList<Flashcard>>() {
            @Override
            public void onChanged(ArrayList<Flashcard> flashcards) {
                flashcardsList.postValue(flashcards);
            }
        });
        manageFlashcardsRepository.getIfRemovedFlashcardProperly().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    ifRemovedFlashcardProperly.postValue(true);
                } else {
                    ifRemovedFlashcardProperly.postValue(false);
                }

            }
        });
    }

    public static FlashcardsViewModel getInstance() {
        if (instance == null) {
            instance = new FlashcardsViewModel();
        }
        return instance;
    }

    public void getFlashcardsListDB(String CID) {
        manageFlashcardsRepository.getFlashcardsListDB(CID);
    }
    public void removeFlashcardFromCatalog(String CID, String FID) {
        manageFlashcardsRepository.removeFlashcardFromCatalog(CID, FID);
    }
    public int findFlashcardPosition(String frontside, ArrayList<Flashcard> flashcardsList){
        for (int i=0; i<flashcardsList.size(); i++){
            if(frontside.toUpperCase().equals(flashcardsList.get(i).getFrontside().toUpperCase()) || frontside.toUpperCase().equals(flashcardsList.get(i).getBackside().toUpperCase())) {
                return i;
            }
        }
        return 0;
    }
}

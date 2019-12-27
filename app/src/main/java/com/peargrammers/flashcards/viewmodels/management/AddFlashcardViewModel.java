package com.peargrammers.flashcards.viewmodels.management;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.repositories.management.ManageFlashcardsRepository;

public class AddFlashcardViewModel {
    private static AddFlashcardViewModel instance;
    private ManageFlashcardsRepository manageFlashcardsRepository;
    private MutableLiveData<Boolean> ifAddFlashcardProperly = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIfAddFlashcardProperly() {
        return ifAddFlashcardProperly;
    }

    public AddFlashcardViewModel() {
        manageFlashcardsRepository = ManageFlashcardsRepository.getInstance();
        manageFlashcardsRepository.getIfAddFlashcardProperly().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    ifAddFlashcardProperly.postValue(true);
                } else {
                    ifAddFlashcardProperly.postValue(false);
                }
            }
        });
    }

    public static AddFlashcardViewModel getInstance(){
        if (instance == null){
            instance = new AddFlashcardViewModel();
        }
        return instance;
    }
    public void addFlashcardToCatalog(String CID, Flashcard flashcard){
        manageFlashcardsRepository.addFlashcardToCatalog(CID, flashcard);
    }
}

package com.peargrammers.flashcards.viewmodels.management;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.repositories.management.ManageFlashcardsRepository;

public class EditFlashcardViewModel {
    public static EditFlashcardViewModel instance;
    private ManageFlashcardsRepository manageFlashcardsRepository;

    public MutableLiveData<Boolean> getIfEditFlashcardProprly() {
        return ifEditFlashcardProprly;
    }

    private MutableLiveData<Boolean> ifEditFlashcardProprly = new MutableLiveData<>();
    public Flashcard getEditedFlashcard() {

        return editedFlashcard;
    }

    public void setEditedFlashcard(Flashcard editedFlashcard) {
        this.editedFlashcard = editedFlashcard;
    }

    private Flashcard editedFlashcard;




    public EditFlashcardViewModel() {
        manageFlashcardsRepository = ManageFlashcardsRepository.getInstance();
        manageFlashcardsRepository.getIfEditedFlashcardProperly().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    ifEditFlashcardProprly.postValue(true);
                } else {
                    ifEditFlashcardProprly.postValue(false);
                }
            }
        });
    }
    public static EditFlashcardViewModel getInstance(){
        if (instance == null) {
            instance = new EditFlashcardViewModel();
        }
        return instance;
    }
    public void editFlashcardFromCatalod(String CID, String FID, String frontside, String backside){
        manageFlashcardsRepository.editFlashcardFromCatalod(CID, FID, frontside, backside);
    }

}

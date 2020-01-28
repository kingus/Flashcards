package com.peargrammers.flashcards.viewmodels.game_modes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.models.QuizDataSet;
import com.peargrammers.flashcards.repositories.management.ManageFlashcardsRepository;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class RateYourselfViewModel {
    public static RateYourselfViewModel instance;
    private MutableLiveData<ArrayList<Flashcard>> flashcardsList = new MutableLiveData<>();
    private int currentFlashcardIndex;
    private ManageFlashcardsRepository manageFlashcardsRepository;
    private String currentCID;
    private ArrayList<Flashcard> flashcardsInput = new ArrayList<>();
    private ArrayList<Flashcard> flashcardsOutput = new ArrayList<>();
    private int goodAnsweredCounter;
    private int wrongAnsweredCounter;
    private SummarizeViewModel summarizeViewModel;
    private ArrayList<Flashcard> flashcardsToRemove = new ArrayList<>();
    private boolean side;

    public boolean isSide() {
        return side;
    }

    public void setSide(boolean side) {
        this.side = side;
    }

    public RateYourselfViewModel() {
        currentFlashcardIndex = 0;
        summarizeViewModel = SummarizeViewModel.getInstance();
        manageFlashcardsRepository = ManageFlashcardsRepository.getInstance();
        manageFlashcardsRepository.getFlashcardsList().observeForever(new Observer<ArrayList<Flashcard>>() {
            @Override
            public void onChanged(ArrayList<Flashcard> flashcards) {
                flashcardsInput = flashcards;
                flashcardsList.postValue(flashcards);
            }
        });
    }

    public ArrayList<Flashcard> getFlashcardsInput() {
        return flashcardsInput;
    }




    public QuizDataSet getSingleQuizDataSet(){

        Flashcard flashcard =  flashcardsInput.get(currentFlashcardIndex);
        QuizDataSet newQuizDataSet = new QuizDataSet(flashcard);
        currentFlashcardIndex++;
        return newQuizDataSet;
    }



    public static RateYourselfViewModel getInstance() {
        if (instance == null) {
            instance = new RateYourselfViewModel();
        }
        return instance;
    }
    public MutableLiveData<ArrayList<Flashcard>> getFlashcardsList() {
        return flashcardsList;
    }

    public int getCurrentFlashcardIndex() {
        return currentFlashcardIndex;
    }

    public void setCurrentFlashcardIndex(int currentFlashcardIndex) {
        this.currentFlashcardIndex = currentFlashcardIndex;
    }

    public void getFlashcardsListDB(String CID) {
        manageFlashcardsRepository.getFlashcardsListDB(CID);
    }


    public String getCurrentCID() {
        return currentCID;
    }

    public void setCurrentCID(String currentCID) {
        this.currentCID = currentCID;
    }

    public boolean processAnswer(boolean answer) {
        summarizeViewModel.setGoodAnsweredCounter(goodAnsweredCounter);
        summarizeViewModel.setWrongAnsweredCounter(wrongAnsweredCounter);
        Flashcard flashcard = flashcardsInput.get(currentFlashcardIndex-1);
        System.out.println("FISZKA ktora dodamy");
        System.out.println(flashcard.getFrontside());
        System.out.println(flashcard.getBackside());
        if (answer) {
            System.out.println("DOOBRZEEE");
            summarizeViewModel.setGoodAnsweredCounter(++goodAnsweredCounter);
            System.out.println(flashcard.getFrontside());
            System.out.println(flashcard.getSmallBox());
            flashcard.setSmallBox(flashcard.getSmallBox()+1);
            if (flashcard.getSmallBox() == 6){
                System.out.println("FISZKA NAUMIANA");
                flashcardsToRemove.add(flashcard);
            } else {
                flashcardsOutput.add(flashcard);

            }
            System.out.println(flashcard.getSmallBox());

            return true;
        } else {
            System.out.println("ŻLEEE");
            summarizeViewModel.setWrongAnsweredCounter(++wrongAnsweredCounter);
            flashcard.setSmallBox(0);
            flashcardsOutput.add(flashcard);
            return false;
        }

    }
    public void updateFlashcardsLevel(){
        manageFlashcardsRepository.editFlashcardsLevelFromCatalog(currentCID, flashcardsOutput);
    }

    public void removeLearnedFlashcards(){
        manageFlashcardsRepository.removeFlashcardsFromCatalog(currentCID, flashcardsToRemove);
    }

    public void resetStatistics(){

//        flashcardsInput = new ArrayList<>();
        flashcardsOutput = new ArrayList<>();
        flashcardsToRemove = new ArrayList<>();
        goodAnsweredCounter = 0;
        wrongAnsweredCounter = 0;
        System.out.println("RESET");
        side = false;
    }
}

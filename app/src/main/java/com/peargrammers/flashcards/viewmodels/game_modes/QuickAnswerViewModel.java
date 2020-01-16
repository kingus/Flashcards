package com.peargrammers.flashcards.viewmodels.game_modes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.models.QuizDataSet;
import com.peargrammers.flashcards.repositories.management.ManageFlashcardsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;

public class QuickAnswerViewModel extends ViewModel {
    public static QuickAnswerViewModel instance;
    private ManageFlashcardsRepository manageFlashcardsRepository;
    private SummarizeViewModel summarizeViewModel;
    private ArrayList<Flashcard> flashcardsInput = new ArrayList<>();
    private ArrayList<Flashcard> flashcardsOutput = new ArrayList<>();
    private ArrayList<Flashcard> flashcardsToRevome = new ArrayList<>();
    private MutableLiveData<ArrayList<Flashcard>> flashcardsList = new MutableLiveData<>();
    private MutableLiveData<QuizDataSet> quizDataSet = new MutableLiveData<>();
    private String currentCID;
    private int currentFlashardIndex;
    private int goodAnsweredCounter;
    private int wrongAnsweredCounter;
    private int hint;

    public int getCurrentFlashardIndex() {
        return currentFlashardIndex;
    }

    public void setCurrentFlashardIndex(int currentFlashardIndex) {
        this.currentFlashardIndex = currentFlashardIndex;
    }

    public int getGoodAnsweredCounter() {
        return goodAnsweredCounter;
    }

    public void setGoodAnsweredCounter(int goodAnsweredCounter) {
        this.goodAnsweredCounter = goodAnsweredCounter;
    }

    public int getWrongAnsweredCounter() {
        return wrongAnsweredCounter;
    }

    public void setWrongAnsweredCounter(int wrongAnsweredCounter) {
        this.wrongAnsweredCounter = wrongAnsweredCounter;
    }

    public MutableLiveData<ArrayList<Flashcard>> getFlashcardsList() {
        return flashcardsList;
    }

    public ArrayList<Flashcard> getFlashcardsInput() {
        return flashcardsInput;
    }

    public void setFlashcardsInput(ArrayList<Flashcard> flashcardsInput) {
        this.flashcardsInput = flashcardsInput;
    }

    public ArrayList<Flashcard> getFlashcardsOutput() {
        return flashcardsOutput;
    }

    public void setFlashcardsOutput(ArrayList<Flashcard> flashcardsOutput) {
        this.flashcardsOutput = flashcardsOutput;
    }

    public ArrayList<Flashcard> getFlashcardsToRevome() {
        return flashcardsToRevome;
    }

    public void setFlashcardsToRevome(ArrayList<Flashcard> flashcardsToRevome) {
        this.flashcardsToRevome = flashcardsToRevome;
    }

    public String getCurrentCID() {
        return currentCID;
    }

    public void setCurrentCID(String currentCID) {
        this.currentCID = currentCID;
    }

    public static QuickAnswerViewModel getInstance() {
        if (instance == null) {
            instance = new QuickAnswerViewModel();
        }
        return instance;
    }

    public int getHint() {
        return hint;
    }

    public void setHint(int hint) {
        this.hint = hint;
    }

    public QuickAnswerViewModel() {
        currentFlashardIndex = 0;
        hint = 0;
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
    public QuizDataSet getSingleQuizDataSet(){

        Flashcard flashcard =  flashcardsInput.get(currentFlashardIndex);
        QuizDataSet newQuizDataSet = new QuizDataSet(flashcard);
        currentFlashardIndex++;
        return newQuizDataSet;
    }

    public boolean processAnswer(String answer) {
        Flashcard flashcard = flashcardsInput.get(currentFlashardIndex-1);
        System.out.println("POROWNUJE: " + flashcard.getBackside() + " oraz " +answer );
        if (flashcard.getBackside().equals(answer)) {
            System.out.println("DOOBRZEEE");
            summarizeViewModel.setGoodAnsweredCounter(++goodAnsweredCounter);

            flashcard.setSmallBox(flashcard.getSmallBox()+1);
            if (flashcard.getSmallBox() == 6)
            {
                //co zrobic gdy fiszka naumiana
                System.out.println("FISZKA NAUMIANA");
                flashcardsToRevome.add(flashcard);
            } else {
                flashcardsOutput.add(flashcard);

            }
            return true;
        } else {
            System.out.println("ŻLEEE");
            summarizeViewModel.setWrongAnsweredCounter(++wrongAnsweredCounter);
            flashcard.setSmallBox(0);
            flashcardsOutput.add(flashcard);
            return false;
        }

    }

    public void getFlashcardsListDB(String CID) {
        manageFlashcardsRepository.getFlashcardsListDB(CID);
    }

    public void updateFlashcardsLevel(){
        manageFlashcardsRepository.editFlashcardsLevelFromCatalog(currentCID, flashcardsOutput);
    }

    public void removeLearnedFlashcards(){
        manageFlashcardsRepository.removeFlashcardsFromCatalog(currentCID, flashcardsToRevome);
    }

    public void resetStatistics(){
        flashcardsOutput = new ArrayList<>();
        flashcardsToRevome = new ArrayList<>();
        goodAnsweredCounter = 0;
        wrongAnsweredCounter = 0;
    }


}

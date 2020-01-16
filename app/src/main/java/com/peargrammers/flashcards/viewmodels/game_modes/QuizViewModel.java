package com.peargrammers.flashcards.viewmodels.game_modes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.models.Flashcard;
import com.peargrammers.flashcards.models.QuizDataSet;
import com.peargrammers.flashcards.repositories.management.ManageFlashcardsRepository;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class QuizViewModel extends ViewModel {
    private static QuizViewModel instance;
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


    public ArrayList<Flashcard> getFlashcardsInput() {
        return flashcardsInput;
    }

    public int getCurrentFlashardIndex() {
        return currentFlashardIndex;
    }

    public void setFlashcardsInput(ArrayList<Flashcard> flashcardsInput) {
        this.flashcardsInput = flashcardsInput;
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

    public String getCurrentCID() {
        return currentCID;
    }

    public void setCurrentCID(String currentCID) {
        this.currentCID = currentCID;
    }

    public MutableLiveData<QuizDataSet> getQuizDataSet() {
        return quizDataSet;
    }


    public MutableLiveData<ArrayList<Flashcard>> getFlashcardsList() {
        return flashcardsList;
    }



    public static QuizViewModel getInstance() {
        if (instance == null) {
            instance = new QuizViewModel();
        }
        return instance;
    }

    public QuizViewModel() {
        currentFlashardIndex = 0;
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
    public void getFlashcardsListDB(String CID) {
        manageFlashcardsRepository.getFlashcardsListDB(CID);
    }
    public QuizDataSet getSingleQuizDataSet(){

        Flashcard flashcard =  flashcardsInput.get(currentFlashardIndex);
        Random r = new Random();

        HashSet<String> answers = new HashSet<>();

        int p;
        while (answers.size() != 3) {
            p = r.nextInt(flashcardsInput.size());
            while (p==currentFlashardIndex) {
                p = r.nextInt(flashcardsInput.size());
            }
            answers.add(flashcardsInput.get(p).getBackside());
        }
        answers.add(flashcardsInput.get(currentFlashardIndex).getBackside());

        ArrayList<String> answs = new ArrayList<String>(answers);
        System.out.println("PRZED: ");
        System.out.println(answers.toString());
        Collections.shuffle(answs);
        System.out.println("PO: ");
        System.out.println(answs.toString());

        QuizDataSet newQuizDataSet = new QuizDataSet(flashcard, answs);
        currentFlashardIndex++;
        return newQuizDataSet;
    }
    public boolean processAnswer(String answer) {
        Flashcard flashcard = flashcardsInput.get(currentFlashardIndex-1);
        System.out.println("POROWNUJE: " + flashcard.getBackside() + " oraz " +answer );
        if (flashcard.getBackside().toUpperCase().equals(answer.toUpperCase())) {
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
    public void updateFlashcardsLevel(){
        manageFlashcardsRepository.editFlashcardsLevelFromCatalog(currentCID, flashcardsOutput);
    }

    public void removeLearnedFlashcards(){
        manageFlashcardsRepository.removeFlashcardsFromCatalog(currentCID, flashcardsToRevome);
    }

    public void resetStatistics(){
        goodAnsweredCounter = 0;
        wrongAnsweredCounter = 0;
        flashcardsOutput = new ArrayList<>();
        flashcardsToRevome = new ArrayList<>();
    }






}

package com.peargrammers.flashcards.viewmodels.game_modes;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

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

public class QuizViewModel {
    private static QuizViewModel instance;
    private ManageFlashcardsRepository manageFlashcardsRepository;

    public ArrayList<Flashcard> getFlashcardsInput() {
        return flashcardsInput;
    }

    public int getCurrentFlashardIndex() {
        return currentFlashardIndex;
    }

    private ArrayList<Flashcard> flashcardsInput = new ArrayList<>();
    private ArrayList<Flashcard> flashcardsOutput = new ArrayList<>();
    private MutableLiveData<ArrayList<Flashcard>> flashcardsList = new MutableLiveData<>();
    private MutableLiveData<QuizDataSet> quizDataSet = new MutableLiveData<>();
    private String currentCID;
    private int currentFlashardIndex;

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
        if (flashcard.getBackside().equals(answer)) {
            System.out.println("DOOBRZEEE");
            flashcard.setSmallBox(flashcard.getSmallBox()+1);
            if (flashcard.getSmallBox() == 6)
            {
                //co zrobic gdy fiszka naumiana
                System.out.println("FISZKA NAUMIANA");
            }
            flashcardsOutput.add(flashcard);
            return true;
        } else {
            System.out.println("ŻLEEE");

            flashcard.setSmallBox(0);
            flashcardsOutput.add(flashcard);
            return false;
        }

    }




}

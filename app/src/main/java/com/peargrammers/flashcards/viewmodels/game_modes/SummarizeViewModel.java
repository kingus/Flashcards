package com.peargrammers.flashcards.viewmodels.game_modes;

import androidx.lifecycle.ViewModel;

public class SummarizeViewModel  extends ViewModel {
    private static SummarizeViewModel instance;

    private int goodAnsweredCounter=0;
    private int wrongAnsweredCounter=0;

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

    public static SummarizeViewModel getInstance() {
        if (instance == null) {
            instance = new SummarizeViewModel();
        }
        return instance;
    }
}

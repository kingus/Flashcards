package com.peargrammers.flashcards.models;

import java.util.ArrayList;

public class QuizDataSet {
    private Flashcard flashcard;
    private ArrayList answers;

    public QuizDataSet(Flashcard flashcard, ArrayList answers) {
        this.answers = answers;
        this.flashcard = flashcard;
    }

    public QuizDataSet(Flashcard flashcard) {
        this.flashcard = flashcard;
        answers = null;
    }

    public ArrayList getAnswers() {
        return answers;
    }

    public Flashcard getFlashcard() {
        return flashcard;
    }
}

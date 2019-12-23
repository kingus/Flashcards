package com.peargrammers.flashcards.models;

import com.google.firebase.database.annotations.NotNull;

public class Flashcard {
    @NotNull
    private String backside, frontside;

    public Flashcard() { }

    public Flashcard(@NotNull String backside, @NotNull String frontside) {
        this.backside = backside;
        this.frontside = frontside;
    }
    @NotNull
    public String getBackside() {
        return backside;
    }

    public void setBackside(@NotNull String backside) {
        this.backside = backside;
    }
    @NotNull
    public String getFrontside() {
        return frontside;
    }

    public void setFrontside(@NotNull String frontside) {
        this.frontside = frontside;
    }
}

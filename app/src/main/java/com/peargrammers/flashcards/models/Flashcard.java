package com.peargrammers.flashcards.models;

import com.google.firebase.database.annotations.NotNull;

public class Flashcard {
    private String FID;
    @NotNull
    private String backside, frontside;
    @NotNull
    private int smallBox;

    public Flashcard() { }

    public Flashcard(@NotNull String backside, @NotNull String frontside) {
        this.backside = backside;
        this.frontside = frontside;
        this.smallBox = 0;
    }
    public Flashcard(@NotNull String backside, @NotNull String frontside, @NotNull int smallBox) {
        this.backside = backside;
        this.frontside = frontside;
        this.smallBox = smallBox;
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

    @NotNull
    public int getSmallBox() {
        return smallBox;
    }

    public void setSmallBox(@NotNull int smallBox) {
        this.smallBox = smallBox;
    }

    public String getFID() {
        return FID;
    }

    public void setFID(String FID) {
        this.FID = FID;
    }
}

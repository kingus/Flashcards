package com.peargrammers.flashcards.models;

import androidx.databinding.BaseObservable;

import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Catalog extends BaseObservable {
    private String CID;
    @NotNull
    private String name, category, owner;
    private Map<String, Flashcard> flashcards;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCID() {
        return CID;
    }

    public void setCID(String CID) {
        this.CID = CID;
    }

    public Catalog() { }

    public Catalog(@NotNull String name, @NotNull String category, @NotNull String owner) {
        this.name = name;
        this.category = category;
        this.owner = owner;
        this.flashcards = new HashMap<>();
    }
    public Catalog(@NotNull String name, @NotNull String category) {
        this.name = name;
        this.category = category;
        this.flashcards = new HashMap<>();
    }
    @NotNull
    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }
    @NotNull
    public String getCategory() {
        return category;
    }

    public void setCategory(@NotNull String category) {
        this.category = category;
    }

    public Map<String, Flashcard> getFlashcards() {
        return flashcards;
    }

    public void setFlashcards(Map<String, Flashcard> flashcards) {
        this.flashcards = flashcards;
    }
}

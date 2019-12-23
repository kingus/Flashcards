package com.peargrammers.flashcards.models;

import androidx.databinding.BaseObservable;

import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Catalog extends BaseObservable {
    @NotNull
    private String name, category;
    private Map<String, Flashcard> flashcards;

    public Catalog() { }

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
}

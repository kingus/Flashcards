package com.peargrammers.flashcards.models;
import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;

import java.util.HashMap;
import java.util.Map;

public class User extends BaseObservable {

    @NonNull
    private String email, name;
    private Map<String, Catalog> catalogs;

    public User(){ }

    public User(@NonNull String name, @NonNull String email){
        this.email=email;
        this.name=name;
        this.catalogs = new HashMap<>();
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String password) {
        this.name = password;
    }

//    public boolean isValidData(){
//        return !TextUtils.isEmpty(getEmail()) && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches() && getPassword().length() > 6;    }
}

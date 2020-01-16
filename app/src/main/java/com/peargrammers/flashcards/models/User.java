package com.peargrammers.flashcards.models;
import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;

import com.google.firebase.database.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class User extends BaseObservable {


    private Long creationTimestamp;
    @NonNull
    private String email, name;
    private Map<String, Catalog> catalogs;

    public void setCatalogs(Map<String, Catalog> catalogs) {
        this.catalogs = catalogs;
    }

    public User(){ }

    public User(@NonNull String name, @NonNull String email){
        this.email=email;
        this.name=name;
        this.catalogs = new HashMap<>();
        this.creationTimestamp = null;

    }

    public Long getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(Long creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
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

    @NotNull
    public Map<String, Catalog> getCatalogs() {
        return catalogs;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", catalogsSize=" + catalogs.size() +
                '}';
    }

    //    public boolean isValidData(){
//        return !TextUtils.isEmpty(getEmail()) && Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches() && getPassword().length() > 6;    }
}

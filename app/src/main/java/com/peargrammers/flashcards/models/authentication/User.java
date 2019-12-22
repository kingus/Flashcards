package com.peargrammers.flashcards.models.authentication;
import android.text.TextUtils;
import android.util.Patterns;
import androidx.annotation.NonNull;
import androidx.databinding.BaseObservable;

public class User extends BaseObservable {

    @NonNull
    private String email, name;

    public User(){

    }

    public User(@NonNull String email, @NonNull String name){
        this.email=email;
        this.name=name;
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

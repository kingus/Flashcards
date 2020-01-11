package com.peargrammers.flashcards.viewmodels.authentication;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.repositories.authentication.LogOutRepository;
import com.peargrammers.flashcards.repositories.authentication.RegisterRepository;

public class LogOutViewModel extends ViewModel {
    private static LogOutViewModel instance;
    private LogOutRepository logOutRepository;

    public LogOutViewModel() {
        logOutRepository = LogOutRepository.getInstance();
    }

    public void logOut(){
        this.logOutRepository.logOut();
    }

    public static LogOutViewModel getInstance(){
        if(instance == null){
            instance = new LogOutViewModel();
        }
        return instance;
    }
}

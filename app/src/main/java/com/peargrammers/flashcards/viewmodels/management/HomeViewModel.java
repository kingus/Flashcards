package com.peargrammers.flashcards.viewmodels.management;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.repositories.management.UserInfoRepository;
import com.peargrammers.flashcards.viewmodels.authentication.LoginViewModel;

public class HomeViewModel extends ViewModel {
    private UserInfoRepository userInfoRepository;
    private MutableLiveData<String> userEmail = new MutableLiveData<>();
    private static HomeViewModel instance;
    private int gameMode;

    public HomeViewModel() {
        this.userInfoRepository = UserInfoRepository.getInstance();
    }

    public static HomeViewModel getInstance(){
        if(instance == null){
            instance = new HomeViewModel();
        }
        return instance;
    }

    public int getGameMode() {
        return gameMode;
    }

    public void setGameMode(int gameMode) {
        this.gameMode = gameMode;
    }
}

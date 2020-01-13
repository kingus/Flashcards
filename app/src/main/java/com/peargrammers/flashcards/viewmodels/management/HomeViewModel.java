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


    public HomeViewModel() {
        this.userInfoRepository = UserInfoRepository.getInstance();
        userInfoRepository.getUserEmail().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                userEmail.postValue(s);
            }
        });
    }

    public MutableLiveData<String> getUserEmail() {
        return userEmail;
    }

    public static HomeViewModel getInstance(){
        if(instance == null){
            instance = new HomeViewModel();
        }
        return instance;
    }


}

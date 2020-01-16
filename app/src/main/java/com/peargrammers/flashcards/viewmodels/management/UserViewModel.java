package com.peargrammers.flashcards.viewmodels.management;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.models.User;
import com.peargrammers.flashcards.repositories.management.UserInfoRepository;

public class UserViewModel extends ViewModel {
    private static UserViewModel instance;
    private UserInfoRepository userInfoRepository;
    private MutableLiveData<User> loggedUser;

    public MutableLiveData<Long> getHowManyFlashcards() {
        return howManyFlashcards;
    }

    private MutableLiveData<Long> howManyFlashcards = new MutableLiveData<>();


    public MutableLiveData<User> getLoggedUser() {
        return loggedUser;
    }

    public UserViewModel() {
        userInfoRepository = UserInfoRepository.getInstance();
        loggedUser = new MutableLiveData<>();
        userInfoRepository.getLoggedUser().observeForever(new Observer<User>() {
            @Override
            public void onChanged(User user) {
                loggedUser.postValue(user);
            }
        });
        userInfoRepository.getHowManyFlashcards().observeForever(new Observer<Long>() {
            @Override
            public void onChanged(Long aLong) {
                howManyFlashcards.postValue(aLong);
            }
        });


    }

    public static UserViewModel getInstance(){
        if (instance == null){
            instance  = new UserViewModel();
        }
        return instance;
    }
    public void getLoggedUserInfo(){
        userInfoRepository.getLoggedUserInfo();
    }
    public void getHowManyFlashcard(){
        userInfoRepository.getFlashcardsAmount();
    }



}

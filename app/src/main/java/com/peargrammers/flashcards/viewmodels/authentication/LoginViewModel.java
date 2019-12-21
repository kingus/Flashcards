package com.peargrammers.flashcards.viewmodels.authentication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.repositories.authentication.LoginRepository;

public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;
    private MutableLiveData<Boolean> signgInStatus = new MutableLiveData<>();

    public MutableLiveData<Boolean> getSigngInStatus() {
        return signgInStatus;
    }

    private static LoginViewModel instance;


    public LoginViewModel() {
        loginRepository = LoginRepository.getInstance();
    }

    public static LoginViewModel getInstance(){
        if(instance == null){
            instance = new LoginViewModel();
        }
        return instance;
    }

    public void signIn(String email, String password){
        //validation
        loginRepository.getSigngInStatus().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    signgInStatus.postValue(true);
                } else {
                    signgInStatus.postValue(false);
                }
            }
        });
        loginRepository.signIn(email, password);

    }

}

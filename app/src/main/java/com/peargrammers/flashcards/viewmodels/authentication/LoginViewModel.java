package com.peargrammers.flashcards.viewmodels.authentication;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.repositories.authentication.LoginRepository;

public class LoginViewModel extends ViewModel {

    private LoginRepository loginRepository;
    private MutableLiveData<Boolean> signgInStatus = new MutableLiveData<>();
    private MutableLiveData<String> signInException = new MutableLiveData<>();
    private static LoginViewModel instance;

    public MutableLiveData<Boolean> getSigngInStatus() {
        return signgInStatus;
    }

    public MutableLiveData<String> getSignInException() { return signInException; }

    public LoginViewModel() {
        loginRepository = LoginRepository.getInstance();
        loginRepository.getSignInStatus().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Log.d("LOGIN_VIEW_MODEL", "LOGIN GIT - view_model");
                    signgInStatus.postValue(true);
                } else {
                    Log.d("LOGIN_VIEW_MODEL", "LOGIN ZLE - view_model");
                    signgInStatus.postValue(false);
                }
            }
        });
        loginRepository.getSignInException().observeForever(new Observer<String>() {
            @Override
            public void onChanged(String s) {
                signInException.postValue(s);
            }
        });
    }

    public static LoginViewModel getInstance(){
        if(instance == null){
            instance = new LoginViewModel();
        }
        return instance;
    }

    public void signIn(String email, String password){
        loginRepository.signIn(email, password);
    }

    public boolean checkEmailAndPasswdLength(String email, String passwd){
        if(email.length()>0 && passwd.length()>0)
            return true;
        return false;
    }

}

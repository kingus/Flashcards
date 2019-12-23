package com.peargrammers.flashcards.viewmodels.authentication;

import android.text.TextUtils;
import android.util.Patterns;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.repositories.authentication.RegisterRepository;

import java.util.regex.Pattern;

public class RegisterViewModel extends ViewModel {

    private RegisterRepository registerRepository;
    private MutableLiveData<Boolean> signgUpStatus = new MutableLiveData<>();

    public MutableLiveData<Boolean> getSigngUpStatus() {
        return signgUpStatus;
    }

    private static RegisterViewModel instance;


    public RegisterViewModel() {
        registerRepository = RegisterRepository.getInstance();
    }

    public static RegisterViewModel getInstance(){
        if(instance == null){
            instance = new RegisterViewModel();
        }
        return instance;
    }

    public void createAccount(String name, String email, String password){
        //validation
        registerRepository.getSigngUpStatus().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    signgUpStatus.postValue(true);
                } else {
                    signgUpStatus.postValue(false);
                }
            }
        });
        registerRepository.createAccount(name, email, password);

    }

    public boolean validateUserRegister(String email, String password1, String password2){
        if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password1.equals(password2) && password1.length() > 6)
            return true;
        else
            return false;
    }

    public boolean validateUserLogin(String email, String password1){
        if (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches() && password1.length() > 6)
            return true;
        else
            return false;
    }




}

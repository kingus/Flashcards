package com.peargrammers.flashcards.viewmodels.authentication;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.repositories.authentication.RegisterRepository;

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

    public void createAccount(String email, String password){
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
        registerRepository.createAccount(email, password);

    }



}

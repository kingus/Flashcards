package com.peargrammers.flashcards.viewmodels.authentication;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.peargrammers.flashcards.MainActivity;
import com.peargrammers.flashcards.activities.authentication.RegisterActivity;
import com.peargrammers.flashcards.repositories.authentication.RegisterRepository;

import static android.content.ContentValues.TAG;

public class RegisterViewModel extends ViewModel {

    private RegisterRepository registerRepository;
    private MutableLiveData<Boolean> logginSuccess = new MutableLiveData<>();

    public MutableLiveData<Boolean> getLogginSuccess() {
        return logginSuccess;
    }

    private static RegisterViewModel instance;

    public RegisterRepository getRegisterRepository() {
        return registerRepository;
    }


    public RegisterViewModel() {
        registerRepository = RegisterRepository.getInstance();
    }

    public static RegisterViewModel getInstance(){
        if(instance == null){
            instance = new RegisterViewModel();
        }


        return instance;
    }



//            .getIsUpdating().observe(this, new Observer<Boolean>() {
//        @Override
//        public void onChanged(@Nullable Boolean aBoolean) {
//            if(aBoolean){
//                showProgressBar();
//            }
//            else{
//                hideProgressBar();
//                mRecyclerView.smoothScrollToPosition(mMainActivityViewModel.getNicePlaces().getValue().size()-1);
//            }
//        }
//    });
//
    public void createAccount(String email, String password){
        //validation
        registerRepository.getLogginSuccess().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    logginSuccess.postValue(true);
                } else {
                    logginSuccess.postValue(false);
                }
            }
        });
        registerRepository.createAccount(email, password);

    }



}

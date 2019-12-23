package com.peargrammers.flashcards.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.UserActivity;
import com.peargrammers.flashcards.models.User;
import com.peargrammers.flashcards.viewmodels.management.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    UserViewModel userViewModel;
    TextView tv_name, tv_email, tv_catalogs_amount;

    public UserFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel = UserViewModel.getInstance();
        tv_name = view.findViewById(R.id.tv_name);
        tv_email = view.findViewById(R.id.tv_email);
        tv_catalogs_amount = view.findViewById(R.id.tv_catalogs_amount);

        userViewModel.getLoggedUser().observe(UserFragment.this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                tv_name.setText(user.getName());
                tv_email.setText(user.getEmail());
                tv_catalogs_amount.setText(Integer.toString(user.getCatalogs().size()));
            }
        });

        userViewModel.getLoggedUserInfo();


    }
}

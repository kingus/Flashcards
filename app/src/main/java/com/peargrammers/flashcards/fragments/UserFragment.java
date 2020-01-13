package com.peargrammers.flashcards.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.models.User;
import com.peargrammers.flashcards.viewmodels.management.UserViewModel;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserFragment extends Fragment {

    UserViewModel userViewModel;
    TextView tv_username, tv_username2, tv_email, tv_catalogs_number, tv_flashcards_number, tv_joined;

    public UserFragment() {
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
        HomeActivity.dialog = ProgressDialog.show(getContext(), "", "Please Wait...");

        userViewModel = UserViewModel.getInstance();
        tv_username = view.findViewById(R.id.tv_username);
        tv_username2 = view.findViewById(R.id.tv_username2);
        tv_email = view.findViewById(R.id.tv_email);
        tv_catalogs_number = view.findViewById(R.id.folders_number);
        tv_joined = view.findViewById(R.id.tv_joined);
        tv_flashcards_number = view.findViewById(R.id.tv_cards_number);

        userViewModel.getLoggedUser().observe(UserFragment.this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                HomeActivity.dialog.dismiss();
                tv_username.setText(user.getName());
                tv_username2.setText(user.getName());
                tv_email.setText(user.getEmail());
                tv_catalogs_number.setText(Integer.toString(user.getCatalogs().size()));
                tv_flashcards_number.setText("100");
                Date date = new Date(user.getCreationTimestamp());
                Format format = new SimpleDateFormat("dd MMMM yyyy");
                tv_joined.setText(format.format(date));
            }
        });

        userViewModel.getLoggedUserInfo();


    }
}

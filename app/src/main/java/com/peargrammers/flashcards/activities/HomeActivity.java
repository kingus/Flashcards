package com.peargrammers.flashcards.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.authentication.LoginActivity;
import com.peargrammers.flashcards.activities.authentication.NavBar;
import com.peargrammers.flashcards.fragments.CatalogFragment;
import com.peargrammers.flashcards.fragments.ContactFragment;
import com.peargrammers.flashcards.fragments.HomeFragment;
import com.peargrammers.flashcards.fragments.UserFragment;
import com.peargrammers.flashcards.fragments.game_modes.ChooseCatalogsQTFramgent;
import com.peargrammers.flashcards.fragments.game_modes.ViewFlashcards;
import com.peargrammers.flashcards.viewmodels.authentication.LogOutViewModel;
import com.peargrammers.flashcards.viewmodels.management.HomeViewModel;

public class  HomeActivity extends AppCompatActivity {

    HomeViewModel homeViewModel;
    LogOutViewModel logOutViewModel;
    SpaceNavigationView navigationView;
    ContactFragment contactFragment;
    HomeFragment homeFragment;
    CatalogFragment catalogFragment;
    ViewFlashcards viewFlashcards;
    UserFragment userFragment;
    FrameLayout mainFrame;
    public static ProgressDialog dialog;


    public HomeActivity() {
        this.homeViewModel = HomeViewModel.getInstance();
        this.logOutViewModel = LogOutViewModel.getInstance();
        this.contactFragment = new ContactFragment();
        this.homeFragment = new HomeFragment();
        this.catalogFragment = new CatalogFragment();
        this.userFragment = new UserFragment();
        this.viewFlashcards = new ViewFlashcards();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        mainFrame = findViewById(R.id.main_frame);
        navigationView = findViewById(R.id.space);
        navigationView.initWithSaveInstanceState(savedInstanceState);
        NavBar.setNavBar(navigationView);


        changeFragment(homeFragment);
        navigationView.changeCurrentItem(-1);
        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                changeFragment(homeFragment);
                navigationView.changeCurrentItem(-1);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        changeFragment(viewFlashcards);
                        break;
                    case 1:
                        changeFragment(catalogFragment);
                        break;
                    case 2:
                        changeFragment(userFragment);
                        break;
                    case 3:
                        logOutViewModel.logOut();
                        finish();
                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });

    }

    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragment.toString());
        fragmentTransaction.replace(R.id.main_frame, fragment).commit();
        Log.i("FRAGMENT", "changed");
    }


//    @Override
//    public void onBackPressed()
//    {
//        Log.i("PRESSED", "back button");
//        Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
//        startActivity(myIntent);
//    }
    
}

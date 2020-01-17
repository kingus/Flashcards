package com.peargrammers.flashcards.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.authentication.LoginActivity;
import com.peargrammers.flashcards.activities.authentication.NavBar;
import com.peargrammers.flashcards.fragments.AboutFragment;
import com.peargrammers.flashcards.fragments.CatalogFragment;
import com.peargrammers.flashcards.fragments.ContactFragment;
import com.peargrammers.flashcards.fragments.FlashcardFragment;
import com.peargrammers.flashcards.fragments.FragmentCoordinator;
import com.peargrammers.flashcards.fragments.HomeFragment;
import com.peargrammers.flashcards.fragments.SummarizeFragment;
import com.peargrammers.flashcards.fragments.UserFragment;
import com.peargrammers.flashcards.fragments.game_modes.ChooseCatalogsQTFramgent;
import com.peargrammers.flashcards.fragments.game_modes.ViewFlashcards;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.viewmodels.authentication.LogOutViewModel;
import com.peargrammers.flashcards.viewmodels.management.HomeViewModel;

import java.util.List;

public class  HomeActivity extends AppCompatActivity {

    HomeViewModel homeViewModel;
    LogOutViewModel logOutViewModel;
    SpaceNavigationView navigationView;
    ContactFragment contactFragment;
    public static HomeFragment homeFragment;
    CatalogFragment catalogFragment;
    ViewFlashcards viewFlashcards;
    UserFragment userFragment;
    FrameLayout mainFrame;
    AboutFragment aboutFragment;
    public static ProgressDialog dialog;
    Context context = this;


    public HomeActivity() {
        this.homeViewModel = HomeViewModel.getInstance();
        this.logOutViewModel = LogOutViewModel.getInstance();
        this.contactFragment = new ContactFragment();
        this.homeFragment = new HomeFragment();
        this.catalogFragment = new CatalogFragment();
        this.userFragment = new UserFragment();
        this.aboutFragment = new AboutFragment();
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

        FragmentCoordinator.changeFragment(homeFragment, getSupportFragmentManager());
        navigationView.changeCurrentItem(-1);
        navigationView.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                FragmentCoordinator.changeFragment(homeFragment, getSupportFragmentManager());
                navigationView.changeCurrentItem(-1);
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                switch (itemIndex){
                    case 0:
                        FragmentCoordinator.changeFragment(aboutFragment, getSupportFragmentManager());
                        break;
                    case 1:
                        FragmentCoordinator.changeFragment(catalogFragment, getSupportFragmentManager());
                        break;
                    case 2:
                        FragmentCoordinator.changeFragment(userFragment, getSupportFragmentManager());
                        break;
                    case 3:
                        showLogOutNotification();

                        break;
                }
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {

            }
        });

    }

    public void showLogOutNotification(){
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.alert_log_out, null);
        Button btnNo = view.findViewById(R.id.btn_no);
        Button btnYes = view.findViewById(R.id.btn_yes);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        final AlertDialog alert = builder.create();
        alert.setCancelable(false);
        alert.show();

        btnYes.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                logOutViewModel.logOut();
                finish();

            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert.hide();
                changeFragment(homeFragment);
                navigationView.changeCurrentItem(-1);
            }
        });

    }

    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragment.toString());
        System.out.println("FRAGMENT!!!!!!!!: " + fragment.toString());
        fragmentTransaction.replace(R.id.main_frame, fragment).commit();
        Log.i("FRAGMENT", "changed");
    }


    @Override
    public void onBackPressed()
    {
        Log.i("PRESSED", "back button");


        Fragment visibleFragment = getVisibleFragment();
        if (visibleFragment instanceof HomeFragment){
            //... do nothing
        } else if (visibleFragment instanceof FlashcardFragment){
            FragmentCoordinator.changeFragment(catalogFragment, getSupportFragmentManager());
        } else {
            FragmentCoordinator.changeFragment(homeFragment, getSupportFragmentManager());
            navigationView.changeCurrentItem(-1);
        }



//        Intent myIntent = new Intent(HomeActivity.this, HomeActivity.class);
//        startActivity(myIntent);
    }
    private Fragment getVisibleFragment() {
        FragmentManager fragmentManager = HomeActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            if (fragment != null && fragment.isVisible())
                return fragment;
        }
        return null;
    }
}

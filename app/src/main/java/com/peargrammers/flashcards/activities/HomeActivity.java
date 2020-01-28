package com.peargrammers.flashcards.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.activities.authentication.NavBar;
import com.peargrammers.flashcards.fragments.AboutFragment;
import com.peargrammers.flashcards.fragments.CatalogFragment;
import com.peargrammers.flashcards.fragments.FlashcardFragment;
import com.peargrammers.flashcards.fragments.FragmentCoordinator;
import com.peargrammers.flashcards.fragments.HomeFragment;
import com.peargrammers.flashcards.fragments.UserFragment;
import com.peargrammers.flashcards.viewmodels.authentication.LogOutViewModel;

import java.util.List;

public class  HomeActivity extends AppCompatActivity {

    private LogOutViewModel logOutViewModel;
    private SpaceNavigationView navigationView;
    private CatalogFragment catalogFragment;
    private UserFragment userFragment;
    private AboutFragment aboutFragment;
    public static ProgressDialog dialog;
    public static HomeFragment homeFragment;

    public HomeActivity() {
        this.logOutViewModel = LogOutViewModel.getInstance();
        this.homeFragment = new HomeFragment();
        this.catalogFragment = new CatalogFragment();
        this.userFragment = new UserFragment();
        this.aboutFragment = new AboutFragment();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
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
        } else if (visibleFragment instanceof FlashcardFragment){
            FragmentCoordinator.changeFragment(catalogFragment, getSupportFragmentManager());
        } else {
            FragmentCoordinator.changeFragment(homeFragment, getSupportFragmentManager());
            navigationView.changeCurrentItem(-1);
        }
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

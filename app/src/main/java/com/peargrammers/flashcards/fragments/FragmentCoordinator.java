package com.peargrammers.flashcards.fragments;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.peargrammers.flashcards.R;

public class FragmentCoordinator {
    public static  void changeFragment(Fragment fragment, FragmentManager fragmentManager){
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(fragment.toString());
//        fragmentTransaction2.hide(HomeFragment.this);
        fragmentTransaction.replace(R.id.main_frame, fragment);
        fragmentTransaction.commit();
        Log.i("FRAGMENT", "changed");
    }
}

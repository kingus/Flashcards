package com.peargrammers.flashcards.activities.authentication;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.peargrammers.flashcards.R;
//import com.peargrammers.flashcards.activities.ContactActivity;
//import com.peargrammers.flashcards.activities.EditActivity;


public class NavBar {
    public static void setNavBar(SpaceNavigationView navigationView){
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_phone));
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_edit));
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_user));
        navigationView.addSpaceItem(new SpaceItem("", R.drawable.ic_exit));
    }


}

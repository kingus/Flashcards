package com.peargrammers.flashcards.fragments.game_modes;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.peargrammers.flashcards.CatalogPlayAdapter;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.RecyclerViewClickInterface;
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.fragments.HomeFragment;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.viewmodels.management.CatalogsViewModel;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ViewCatalogsFragment extends Fragment implements RecyclerViewClickInterface {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private ArrayList<Catalog> catalogsList = new ArrayList<>();
    private CatalogsViewModel catalogsViewModel;
    private RecyclerView.LayoutManager mLayoutManager;
    ViewFlashcards viewFlashcards;
    QuizFragment quizFragment;

    public ViewCatalogsFragment() {
        catalogsViewModel = CatalogsViewModel.getInstance();
        this.viewFlashcards = new ViewFlashcards();
        this.quizFragment = new QuizFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_catalogs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = view.findViewById(R.id.recycler_catalogs);
        mRecyclerView.setHasFixedSize(true);
        HomeActivity.dialog = ProgressDialog.show(getContext(), "", "Please Wait...");

        final RecyclerViewClickInterface rvci = this;
        catalogsViewModel.getUsersCatalogsList().observe(ViewCatalogsFragment.this, new Observer<ArrayList<Catalog>>() {
            @Override
            public void onChanged(ArrayList<Catalog> catalogs) {
                catalogsList.clear();
                catalogsList.addAll(catalogs);
                GridLayoutManager glm = new GridLayoutManager(getContext(), 2);
                System.out.println(glm.getSpanCount());
                mLayoutManager = glm;
                mRecyclerView.setLayoutManager(mLayoutManager);
                mAdapter = new CatalogPlayAdapter(catalogsList, rvci);
                mRecyclerView.setAdapter(mAdapter);
                HomeActivity.dialog.dismiss();
            }
        });


        catalogsViewModel.getUsetsCatalogListDB();

    }

    @Override
    public void onItemClick(int position) {
        FlashcardsViewModel.getInstance().setCurrentCatalog(catalogsList.get(position));
        System.out.println(catalogsList.get(position).getName());
        System.out.println(position);
        mRecyclerView.findViewHolderForAdapterPosition(position).itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        switch (HomeFragment.gameType){
            case 0:
                break;
                case 1:
                changeFragment(quizFragment);
                break;
                case 2:
                break;
                case 3:
                changeFragment(viewFlashcards);
                break;

        }

    }

    public void changeFragment(Fragment fragment){
        FragmentManager fragmentManager2 = getFragmentManager();
        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
        fragmentTransaction2.addToBackStack(fragment.toString());
//        fragmentTransaction2.hide(HomeFragment.this);
        fragmentTransaction2.replace(R.id.main_frame, fragment);
        fragmentTransaction2.commit();
        Log.i("FRAGMENT", "changed");
    }

}

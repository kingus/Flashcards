package com.peargrammers.flashcards.fragments.game_modes;


import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.peargrammers.flashcards.adapters.CatalogPlayAdapter;
import com.peargrammers.flashcards.R;
import com.peargrammers.flashcards.adapters.RecyclerViewClickInterface;
import com.peargrammers.flashcards.activities.HomeActivity;
import com.peargrammers.flashcards.fragments.FragmentCoordinator;
import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.viewmodels.game_modes.QuickAnswerViewModel;
import com.peargrammers.flashcards.viewmodels.game_modes.RateYourselfViewModel;
import com.peargrammers.flashcards.viewmodels.management.CatalogsViewModel;
import com.peargrammers.flashcards.viewmodels.management.FlashcardsViewModel;
import com.peargrammers.flashcards.viewmodels.management.HomeViewModel;

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
    private HomeViewModel homeViewModel = HomeViewModel.getInstance();
    private ViewFlashcards viewFlashcards;
    private RateYourselfFragment rateYourselfFragment;
    private QuickAnswerFragment quickAnswerFragment;

    public ViewCatalogsFragment() {
        catalogsViewModel = CatalogsViewModel.getInstance();
        this.viewFlashcards = new ViewFlashcards();
        this.rateYourselfFragment = new RateYourselfFragment();
        this.quickAnswerFragment = new QuickAnswerFragment();
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
        mRecyclerView.findViewHolderForAdapterPosition(position).itemView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            switch (homeViewModel.getGameMode()) {
                case 0:
                        QuickAnswerViewModel.getInstance().setCurrentCID(catalogsList.get(position).getCID());
                        FragmentCoordinator.changeFragment(quickAnswerFragment, getFragmentManager());
                    break;

            case 2:
                    RateYourselfViewModel.getInstance().setCurrentCID((catalogsList.get(position).getCID()));
                    FragmentCoordinator.changeFragment(rateYourselfFragment, getFragmentManager());

                break;
            case 3:
                FlashcardsViewModel.getInstance().setCurrentCatalog(catalogsList.get(position));
                FragmentCoordinator.changeFragment(viewFlashcards, getFragmentManager());
                break;
        }
    }
}

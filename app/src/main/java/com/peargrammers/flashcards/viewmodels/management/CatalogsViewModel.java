package com.peargrammers.flashcards.viewmodels.management;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.repositories.management.ManageCatalogsRepository;

import java.util.ArrayList;

public class CatalogsViewModel extends ViewModel {
    private static CatalogsViewModel instance;
    private ManageCatalogsRepository manageCatalogsRepository;
    private MutableLiveData<ArrayList<Catalog>> usersCatalogsList = new MutableLiveData<>();
    private MutableLiveData<Boolean> ifRemoveCatalogProperly = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIfRemoveCatalogProperly() {
        return ifRemoveCatalogProperly;
    }

    public MutableLiveData<ArrayList<Catalog>> getUsersCatalogsList() {
        return usersCatalogsList;
    }

    public CatalogsViewModel() {
        manageCatalogsRepository = ManageCatalogsRepository.getInstance();
        manageCatalogsRepository.getUsersCatalogsList().observeForever(new Observer<ArrayList<Catalog>>() {
            @Override
            public void onChanged(ArrayList<Catalog> catalogs) {
                usersCatalogsList.postValue(catalogs);
            }
        });
        manageCatalogsRepository.getIfRemoveCatalogProperly().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    ifRemoveCatalogProperly.postValue(true);

                } else {
                    ifRemoveCatalogProperly.postValue(false);
                }
            }
        });
    }

    public static CatalogsViewModel getInstance() {
        if (instance == null) {
            instance = new CatalogsViewModel();
        }
        return instance;
    }
    public void getUsetsCatalogListDB() {
        manageCatalogsRepository.getUsersCatalogsListDB();
    }
    public void removeCatalogFromList(String CID) {
        manageCatalogsRepository.removeCatalogFromList(CID);

    }

}

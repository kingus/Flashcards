package com.peargrammers.flashcards.viewmodels.management;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.repositories.management.ManageCatalogsRepository;

public class AddCatalogViewModel extends ViewModel {

    private static AddCatalogViewModel instance;
    private ManageCatalogsRepository manageCatalogsRepository;
    private MutableLiveData<Boolean> ifAddCatalogProperly = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIfAddCatalogProperly() {
        return ifAddCatalogProperly;
    }

    public AddCatalogViewModel() {
        manageCatalogsRepository = ManageCatalogsRepository.getInstance();
        manageCatalogsRepository.getIfAddCatalogProperly().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    ifAddCatalogProperly.postValue(true);

                } else {
                    ifAddCatalogProperly.postValue(false);
                }
            }
        });
    }

    public static AddCatalogViewModel getInstance(){
        if(instance == null){
            instance = new AddCatalogViewModel();
        }
        return instance;
    }

    public void addNewCatalog(String name, String category) {
        manageCatalogsRepository.addNewCatalog(name, category);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        ifAddCatalogProperly.setValue(null);
    }
}

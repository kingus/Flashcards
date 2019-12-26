package com.peargrammers.flashcards.viewmodels.management;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.peargrammers.flashcards.models.Catalog;
import com.peargrammers.flashcards.repositories.management.ManageCatalogsRepository;

public class EditCatalogViewModel extends ViewModel {

    private static EditCatalogViewModel instance;
    private ManageCatalogsRepository manageCatalogsRepository;
    private Catalog editedCatalog;

    private MutableLiveData<Boolean> ifEditCatalogProperly = new MutableLiveData<>();
    public MutableLiveData<Boolean> getIfEditCatalogProperly() {
        return ifEditCatalogProperly;
    }

    public void setEditedCatalog(Catalog editedCatalog) {
        this.editedCatalog = editedCatalog;
    }

    public Catalog getEditedCatalog() {
        return editedCatalog;
    }

    public EditCatalogViewModel() {
        manageCatalogsRepository = ManageCatalogsRepository.getInstance();
        manageCatalogsRepository.getIfAddCatalogProperly().observeForever(new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    ifEditCatalogProperly.postValue(true);

                } else {
                    ifEditCatalogProperly.postValue(false);
                }
            }
        });
    }

    public static EditCatalogViewModel getInstance(){
        if(instance == null){
            instance = new EditCatalogViewModel();
        }
        return instance;
    }

    public void editCatalog(String CID, String name, String category) {
        manageCatalogsRepository.editCatalog(CID, name, category);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        ifEditCatalogProperly.setValue(null);
    }
}

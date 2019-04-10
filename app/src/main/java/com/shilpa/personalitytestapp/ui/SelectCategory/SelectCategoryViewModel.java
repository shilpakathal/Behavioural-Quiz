package com.shilpa.personalitytestapp.ui.SelectCategory;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.shilpa.personalitytestapp.repository.server.GetPersonalityDataServerAPI;


public class SelectCategoryViewModel extends AndroidViewModel {

    private GetPersonalityDataServerAPI api;

    public SelectCategoryViewModel(@NonNull Application application, GetPersonalityDataServerAPI mapi) {
        super(application);
        this.api = mapi;
    }

    public LiveData<String> getCategory()
    {
        return this.api.getJsonResponse();
    }
}

package com.shilpa.personalitytestapp.repository.server;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.shilpa.personalitytestapp.PersonalityDataApp;
import com.shilpa.personalitytestapp.repository.local.PersonalityData;
import com.shilpa.personalitytestapp.repository.local.PersonalityDataLocalAPI;
import com.shilpa.personalitytestapp.repository.model.ReadingJson;

import java.util.List;

public class GetPersonalityDataServerAPI implements GetPersonalityDataServerInterface {

    private MutableLiveData<String> jsonResponse;
    private PersonalityDataLocalAPI localAPI;
    private MutableLiveData<PersonalityData> data;
    private MutableLiveData<List<PersonalityData>> mPersonalityDataList;
    private ReadingJson readingJson;
    private Context context;

    public GetPersonalityDataServerAPI(Context context) {
        this.jsonResponse = new MutableLiveData<>();
        this.context = context;
    }

    @Override
    public LiveData<String> getJsonResponse() {
       readingJson = new ReadingJson(context);
       String jsonResponseString = readingJson.readJSONFromAsset();
       if(jsonResponseString!=null)
       {
           jsonResponse.postValue(readingJson.readJSONFromAsset());

       }
      return jsonResponse;
    }

    public void saveOptions(PersonalityData personalityData) {
        data = new MutableLiveData<>();
        localAPI = new PersonalityDataLocalAPI(PersonalityDataApp.getApp().getDataBase().personalityDataDao(), data);
        localAPI.saveOptionToDatabase(personalityData);
    }

    public LiveData<List<PersonalityData>> retrieveData() {
        mPersonalityDataList = new MutableLiveData<>();
        localAPI = new PersonalityDataLocalAPI(PersonalityDataApp.getApp().getDataBase().personalityDataDao(), data);
        LiveData<List<PersonalityData>> retrievedData = localAPI.retrievePersonalityDataFromDatabase();
        retrievedData.observeForever(new Observer<List<PersonalityData>>() {
            @Override
            public void onChanged(@Nullable List<PersonalityData> personalityData) {
                mPersonalityDataList.postValue(personalityData);
            }
        });
        return mPersonalityDataList;
    }

}

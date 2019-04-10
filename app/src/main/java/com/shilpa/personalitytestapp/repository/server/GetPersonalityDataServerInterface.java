package com.shilpa.personalitytestapp.repository.server;

import android.arch.lifecycle.LiveData;

public interface GetPersonalityDataServerInterface {
    LiveData<String> getJsonResponse();
}

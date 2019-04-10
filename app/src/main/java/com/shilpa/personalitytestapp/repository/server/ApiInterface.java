package com.shilpa.personalitytestapp.repository.server;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("Add Endpoints here")
    Call<Response>getPersonalityData();
}

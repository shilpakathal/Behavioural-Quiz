package com.shilpa.personalitytestapp.ui.SelectCategory;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.shilpa.personalitytestapp.R;
import com.shilpa.personalitytestapp.repository.local.PersonalityData;
import com.shilpa.personalitytestapp.repository.server.GetPersonalityDataServerAPI;
import com.shilpa.personalitytestapp.ui.SavedPersonalityDataActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectCategoryActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SelectCategoryViewModel selectCategoryViewModel;
    private SelectCategoryViewModelFactory selectCategoryViewModelFactory;
    private SelectCategoryAdapter selectCategoryAdapter;
    private List<String> categoriesList;
    private GetPersonalityDataServerAPI mApi;
    private LiveData<List<PersonalityData>> personalityDataList;
    private List<String> questionList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        recyclerView = findViewById(R.id.select_category_rv);
        mApi = new GetPersonalityDataServerAPI(getApplicationContext());

        categoriesList = new ArrayList<>();
        questionList = new ArrayList<>();

        selectCategoryViewModelFactory = new SelectCategoryViewModelFactory(getApplication(), mApi);
        selectCategoryViewModel = ViewModelProviders.of(SelectCategoryActivity.this, selectCategoryViewModelFactory).get(SelectCategoryViewModel.class);

        selectCategoryViewModel.getCategory().observe(SelectCategoryActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                Log.d("Json is",s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArrayCategories = new JSONArray();
                    JSONArray jsonArrayQuestions = new JSONArray();

                    jsonArrayCategories= jsonObject.getJSONArray("categories");
                    jsonArrayQuestions = jsonObject.getJSONArray("questions");
                    for(int i = 0;i<jsonArrayCategories.length(); i++)
                    {
                        categoriesList.add(jsonArrayCategories.getString(i));
                    }
                    for(int j = 0; j < jsonArrayQuestions.length(); j++)
                    {
                        questionList.add(jsonArrayQuestions.getJSONObject(j).getString("question"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadCategory(categoriesList,questionList);
            }
        });


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                personalityDataList = mApi.retrieveData();
                personalityDataList.observeForever(new Observer<List<PersonalityData>>() {
                    @Override
                    public void onChanged(@Nullable List<PersonalityData> personalityData) {
                        if (personalityData != null) {
                            Intent intent = new Intent(SelectCategoryActivity.this, SavedPersonalityDataActivity.class);
                            intent.putParcelableArrayListExtra("RetrievedData", (ArrayList) personalityData);
                            startActivity(intent);

                        } else {
                            Snackbar.make(view, "No data is Saved", Snackbar.LENGTH_LONG);
                        }
                    }
                });
            }
        });
    }



    private void loadCategory(List<String> categories, List<String> questionList) {
        //Pass to adapter
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        selectCategoryAdapter = new SelectCategoryAdapter(getApplicationContext(), categories, questionList);
        recyclerView.setAdapter(selectCategoryAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

package com.shilpa.personalitytestapp.ui.QuestionsList;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.shilpa.personalitytestapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class QuestionsListActivity extends AppCompatActivity {

    private ViewPager mViewPager;
    private QuestionsPageAdapter questionsPageAdapter;
    private QuestionListViewModel mViewModel;
    private QuestionListViewModelFactory questionListViewModelFactory;
    private String category = null;
    int position;
    private List<String> questionList;
    private List<String> questionCategory;
    private JSONArray jsonArrayoptions;
    private List<JSONArray> optionsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_list);
        init();
        category = getIntent().getExtras().getString("CategoryName");

        questionList = new ArrayList<>();
        questionCategory = new ArrayList<>();
        jsonArrayoptions = new JSONArray();
        optionsList = new ArrayList<JSONArray>();

        mViewPager = findViewById(R.id.question_list_view_pager);

        questionListViewModelFactory = new QuestionListViewModelFactory(getApplication());
        mViewModel = ViewModelProviders.of(QuestionsListActivity.this, questionListViewModelFactory).get(QuestionListViewModel.class);

        mViewModel.getQuestionsList().observe(QuestionsListActivity.this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray jsonArrayQuestions = new JSONArray();

                    jsonArrayQuestions = jsonObject.getJSONArray("questions");

                    for (int i = 0; i < jsonArrayQuestions.length(); i++) {
                        questionList.add(jsonArrayQuestions.getJSONObject(i).getString("question"));
                        questionCategory.add(jsonArrayQuestions.getJSONObject(i).getString("category"));
                        jsonArrayoptions = jsonArrayQuestions.getJSONObject(i).getJSONObject("question_type").getJSONArray("options");
                        optionsList.add(jsonArrayoptions);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                loadQuestions(questionList, position, questionCategory, optionsList);
            }
        });

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        position = savedInstanceState.getInt("current_item", 0);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("current_item", mViewPager.getCurrentItem());
    }

    private void init() {
        Toolbar actionbar = findViewById(R.id.toolbar);
        setSupportActionBar(actionbar);
        actionbar.setTitle(R.string.app_name);
        actionbar.setTitleTextColor(Color.WHITE);
    }

    private void loadQuestions(List<String> questions, int position, List<String> questionCategory, List<JSONArray> optionsList) {
        List<String> categorisedQuestions = new ArrayList<>();
        List<JSONArray> categorisedOptions = new ArrayList<>();


        for (int i = 0; i < questions.size(); i++)

            if (category.equalsIgnoreCase(questionCategory.get(i))) {
                categorisedQuestions.add(questions.get(i));
                categorisedOptions.add(optionsList.get(i));


            }


        questionsPageAdapter = new QuestionsPageAdapter(getSupportFragmentManager(), categorisedQuestions.size()
                , categorisedQuestions, categorisedOptions);
        mViewPager.setAdapter(questionsPageAdapter);
        mViewPager.setCurrentItem(position);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}




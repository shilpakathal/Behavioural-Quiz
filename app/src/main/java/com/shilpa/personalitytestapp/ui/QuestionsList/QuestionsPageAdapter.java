package com.shilpa.personalitytestapp.ui.QuestionsList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import org.json.JSONArray;

import java.util.List;

public class QuestionsPageAdapter extends FragmentStatePagerAdapter {

    private int mQuestionCount;
    private List<String> mQuestionsList;
    private List<JSONArray> mOptionsList;

    public QuestionsPageAdapter(FragmentManager fm, int questionsCount, List<String> categorisedQuestions,List<JSONArray> optionList) {
        super(fm);
        mQuestionCount = questionsCount;
        mQuestionsList = categorisedQuestions;
        mOptionsList = optionList;
    }

    @Override
    public Fragment getItem(int position) {

        QuestionListFragment questionListFragment = new QuestionListFragment();
        return questionListFragment.newInstance(position, mQuestionsList.get(position), mOptionsList.get(position));
    }

    @Override
    public int getCount() {
        return mQuestionCount;
    }
}

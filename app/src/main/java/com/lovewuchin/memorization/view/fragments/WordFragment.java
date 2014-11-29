package com.lovewuchin.memorization.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lovewuchin.memorization.R;
import com.lovewuchin.memorization.adapter.WordFragmentAdapter;
import com.lovewuchin.memorization.util.Utility;
import com.viewpagerindicator.TabPageIndicator;
import com.viewpagerindicator.TitlePageIndicator;

/**
 * @author Chin
 * @since 2014/11/28
 */
public class WordFragment extends Fragment {

    private ViewPager mPager;
    private TabPageIndicator mIndicator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_word, container, false);
        mPager = Utility.findViewById(v, R.id.pager);
        mIndicator = Utility.findViewById(v, R.id.tab_page_indicator);
        mPager.setAdapter(new WordFragmentAdapter(getFragmentManager()));
        mIndicator.setBackgroundColor(getResources().getColor(R.color.blue));
        mIndicator.setViewPager(mPager);
        return v;
    }
}

package com.lovewuchin.memorization.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.lovewuchin.memorization.MApplication;
import com.lovewuchin.memorization.R;
import com.lovewuchin.memorization.view.fragments.ReviewFragment;
import com.lovewuchin.memorization.view.fragments.StudyFragment;

/**
 * @author Chin
 * @since 2014/11/28
 */
public class WordFragmentAdapter extends FragmentPagerAdapter {

    protected static final String[] CONTENT = MApplication.getInstance().getResources().getStringArray(R.array.view_pager_indicator_name);

    private int mCount = CONTENT.length;

    public WordFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            return new StudyFragment();
        }else if(position == 1) {
            return new ReviewFragment();
        }else return null;
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return WordFragmentAdapter.CONTENT[position % CONTENT.length];
    }

}

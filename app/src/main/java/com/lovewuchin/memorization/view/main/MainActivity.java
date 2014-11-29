package com.lovewuchin.memorization.view.main;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;

import com.lovewuchin.memorization.R;
import com.lovewuchin.memorization.util.Utility;
import com.lovewuchin.memorization.view.common.CommonActivity;
import com.lovewuchin.memorization.view.fragments.DrawerFragments;
import com.lovewuchin.memorization.view.fragments.NoteFragment;
import com.lovewuchin.memorization.view.fragments.WordFragment;

/**
 * @author Chin
 * @since 2014/11/27
 */
public class MainActivity extends CommonActivity implements DrawerFragments.DrawerCallbacks{

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    //Fragments
    private DrawerFragments mDrawerFragments;
    private Fragment[] mFragments = new Fragment[2];
    private FragmentManager mFragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mDrawerFragments = new DrawerFragments();
        mFragmentManager = getSupportFragmentManager();
        mFragmentManager.beginTransaction().replace(R.id.drawer_view, mDrawerFragments).commit();

        //init fragments;
        mFragments[0] = new WordFragment();
        mFragments[1] = new NoteFragment();
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        for (Fragment f : mFragments) {
            ft.add(R.id.container, f);
            ft.hide(f);
        }
        ft.commit();

        mDrawerLayout = Utility.findViewById(this, R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, 0, 0);
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }

    @Override
    public void onDrawerItemSelected(int position) {
        switchTo(position);
    }

    private void switchTo(int id) {
        FragmentTransaction ft = mFragmentManager.beginTransaction();
        ft.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out);
        for (int i = 0; i < mFragments.length; i++) {
            Fragment f = mFragments[i];

            if (f != null) {
                if (i != id) {
                    ft.hide(f);
                } else {
                    ft.show(f);
                }
            }
        }
        ft.commit();

        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }
}

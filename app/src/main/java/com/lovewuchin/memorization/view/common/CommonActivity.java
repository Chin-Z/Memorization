package com.lovewuchin.memorization.view.common;


import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.lovewuchin.memorization.R;
import com.lovewuchin.memorization.util.Utility;

/**
 * @author Chin
 * @since 2014/11/27
 */
public abstract class CommonActivity extends ActionBarActivity {

    protected Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(getLayoutResource());

        mToolbar = Utility.findViewById(this, R.id.toolbar);

        setSupportActionBar(mToolbar);
    }

    protected abstract int getLayoutResource();
}

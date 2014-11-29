package com.lovewuchin.memorization.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.lovewuchin.memorization.R;
import com.lovewuchin.memorization.util.Utility;

/**
 * @author Chin
 * @since 2014/11/28
 */
public class DrawerFragments extends Fragment {

    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    private int mCurrentItemPosition;

    private ListView mDrawerListView;
    private DrawerCallbacks mCallbacks;

    public DrawerFragments() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            mCurrentItemPosition = savedInstanceState
                    .getInt(STATE_SELECTED_POSITION);
        }

        selectItem(mCurrentItemPosition);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_drawer,container, false);

        mDrawerListView = Utility.findViewById(v, R.id.drawer_list);
        mDrawerListView
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        selectItem(position);
                    }
                });
        mDrawerListView.setAdapter(new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_activated_1, android.R.id.text1,
                getResources().getStringArray(R.array.drawer_item_name)));
        mDrawerListView.setItemChecked(mCurrentItemPosition, true);
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (DrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentItemPosition);
    }

    private void selectItem(int position) {
        mCurrentItemPosition = position;
        if(mDrawerListView != null) {
            mDrawerListView.setItemChecked(position, true);
        }
        if(mCallbacks != null) {
            mCallbacks.onDrawerItemSelected(position);
        }
    }

    public static interface DrawerCallbacks {
        void onDrawerItemSelected(int position);
    }
}

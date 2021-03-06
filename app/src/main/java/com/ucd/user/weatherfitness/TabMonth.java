package com.ucd.user.weatherfitness;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


/**
 * Created by mihhail_shapovalov on 11/18/17.
 */
//Class for weekly activities fragment

public class TabMonth extends Fragment {
    private static final String TAG = "TabWeek";
    DBAdapter myDb;

    //we inflate the fragment with view and populate list of workouts
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_month, container, false);
        populateListView(view);
        return view;

    }
    //methods to run on fragment creation
    public void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDb = new DBAdapter(getActivity());
        myDb.open();
    }

    //method to populate list from SQLite and using item_layout, custom layout to show records in particular order and size
    private void populateListView(View view) {
        Calendar cal = Calendar.getInstance();
        //Date in simpleformat
        cal.add(Calendar.MONTH, -1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        String monthAgo =format1.format(cal.getTime());
        Log.d("Tabweek", monthAgo);
        Cursor cursor = myDb.getRow(monthAgo);
        String[] fromFieldNames = new String[]{DBAdapter.KEY_ROWID, DBAdapter.KEY_DATETIME, DBAdapter.KEY_LOCATION, DBAdapter.KEY_SCORE};
        int[] toViewIDs = new int[]{R.id.TextViewID, R.id.TextViewDate, R.id.TextViewLocation, R.id.TextViewScore};
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(getActivity().getBaseContext(), R.layout.item_layout, cursor, fromFieldNames, toViewIDs, 0);
        ListView myList = view.findViewById(R.id.Listview_month);
        myList.setAdapter(myCursorAdapter);

    }

}

package com.example.travhelp;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentListPatientsJourney extends Fragment {

    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Surname = new ArrayList<String>();
    public PatientsDbHelper myDbHelper;
    public SQLiteDatabase db = null;
    public ListView lv;

    public static FragmentListPatientsJourney newInstance(){
        FragmentListPatientsJourney fragment = new FragmentListPatientsJourney();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Context context = getContext();
        Thread thread = new Thread(){
            @Override
            public void run() {
                myDbHelper = new PatientsDbHelper(context);
                db = myDbHelper.getReadableDatabase();
            }
        };
        thread.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        while (db==null){}
        View view = inflater.inflate(R.layout.layout_list_patients_fragments, container,false);
        Cursor cursor = db.rawQuery("SELECT name, id, surname FROM  Patients ORDER BY surname",null);

        //Erase lists from previous iteration to avoid printing data twice
        Name.clear();
        Surname.clear();
        Id.clear();

        //Adds the data from the SQL query in the the Name, Surname and Id lists
        if (cursor.moveToFirst()) {
            do {
                Name.add(cursor.getString(cursor.getColumnIndex("name")));
                Id.add(cursor.getString(cursor.getColumnIndex("id")));
                Surname.add(cursor.getString(cursor.getColumnIndex("surname")));
                System.out.println(Name.toString());
            } while (cursor.moveToNext());
        }

        //Prints a patient for each data in the lists
        Context context = getContext();
        //CustomAdapter ca = new CustomAdapter(context, Name, Surname, Id, R.id.layout_list_patients_fragments);
        //lv.setAdapter(ca);

        cursor.close();
        return view;
    }
}

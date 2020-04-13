package com.example.travhelp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class tournee extends AppCompatActivity {
    public JourneyDbHelper myDbHelper;
    public SQLiteDatabase db;
    ArrayList<String> Name;
    ArrayList<String> Surname;
    ArrayList<String> Time;
    ArrayList<String> Address;
    ListView lv;
    public fragment_tournee fragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournee);

        //Sets the title and display it
        getSupportActionBar().setTitle(R.string.fr_my_new_journey);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        fragment = fragment_tournee.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_listview, fragment).commit();
    }

    public void getJourney(View v)
    {

        fragment.getJourney();
    }

}

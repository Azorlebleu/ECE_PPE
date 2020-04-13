package com.example.travhelp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travhelp.R;
import com.example.travhelp.addperson;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.util.ArrayList;



public class Selected_Patients extends AppCompatActivity {


    public SelectionDbHelper myDbHelper;
    public SQLiteDatabase db;
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Surname = new ArrayList<String>();
    private ArrayList<String> Address = new ArrayList<String>();
    private ArrayList<String> Time_min = new ArrayList<String>();
    private ArrayList<String> Time_max= new ArrayList<String>();
    private ArrayList<String> Duration = new ArrayList<String>();


    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected__patients);

        //Sets the title and display it
        getSupportActionBar().setTitle("SELECTED PATIENTS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        lv = (ListView) findViewById(R.id.listview);

        //Instanciating the database in a thread for better performances
        final Context context = this;
        Thread thread = new Thread(){
            @Override
            public void run() {
                myDbHelper = new SelectionDbHelper(context);
                db = myDbHelper.getWritableDatabase();
            }
        };
        thread.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        //ensures that the thread has come to completion and has instanciated the ReadableDatabase before trying to access the database on the first run
        while (db == null){}

        //Fills the database
        displayData();
    }

    //Function called to open the activity where we see one patient's data
    public void viewPatient(View v)
    {
        //Patient's ID is stored in the tag
        String tag = (String)v.getTag();

        Intent otherActivity= new Intent(getApplicationContext(), data.class);
        otherActivity.putExtra("id", tag);
        startActivity(otherActivity);
    }

    @Override
    protected void  onDestroy(){
        super.onDestroy();
        db.close();
    }

    //Function called to display the list of all the patients, using a template for each patient's row
    private void displayData() {

        //Get all the patients in a Cursor object which lets us extract the data within it easily

        Cursor cursor = db.rawQuery("SELECT * FROM  selection ORDER BY surname",null);

        //Erase lists from previous iteration to avoid printing data twice
        Name.clear();
        Surname.clear();
        Id.clear();
        Address.clear();
        Time_max.clear();
        Time_min.clear();
        Duration.clear();


        //Adds the data from the SQL query in the the Name, Surname and Id lists
        if (cursor.moveToFirst()) {
            do {
                Name.add(cursor.getString(cursor.getColumnIndex("name")));
                Id.add(cursor.getString(cursor.getColumnIndex("id")));
                Surname.add(cursor.getString(cursor.getColumnIndex("surname")));
                Address.add(cursor.getString(cursor.getColumnIndex("address")));
                System.out.println(Name.toString());
            } while (cursor.moveToNext());
        }

        //Prints a patient for each data in the lists
        CustomAdapterSelection ca = new CustomAdapterSelection(Selected_Patients.this, Name, Surname,Address, Time_min, Time_max, Duration);

        lv.setAdapter(ca);

        cursor.close();
    }
}






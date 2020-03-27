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

import java.util.ArrayList;


public class contact_directory extends AppCompatActivity {
    private ImageView add;
    public PatientsDbHelper myDbHelper;
    public SQLiteDatabase db;
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Surname = new ArrayList<String>();

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_directory);

        //Sets the title and display it
        getSupportActionBar().setTitle("CONTACTS DIRECTORY");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Add new person button
        add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherActivity=new Intent(getApplicationContext(), addperson.class);
                startActivity(otherActivity);
            }
        });

        lv = (ListView) findViewById(R.id.listview);

        //Instanciating the database in a thread for better performances
        final Context context = this;
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

    //Function called to display the list of all the patients, using a template for each patient's row
    private void displayData() {

        //Get all the patients in a Cursor object which lets us extract the data within it easily
        Cursor cursor = db.rawQuery("SELECT name, id, surname FROM  Patients",null);

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
            } while (cursor.moveToNext());
        }

        //Prints a patient for each data in the lists
        CustomAdapter ca = new CustomAdapter(contact_directory.this, Name, Surname, Id);
        lv.setAdapter(ca);

        cursor.close();
    }
}


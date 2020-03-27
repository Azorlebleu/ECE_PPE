package com.example.travhelp;

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
    PatientsDbHelper myDbHelper;
    SQLiteDatabase db;
    private ArrayList<String> Name = new ArrayList<String>();
    private ArrayList<String> Id = new ArrayList<String>();
    private ArrayList<String> Surname = new ArrayList<String>();

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_directory);

        //Sets the title
        getSupportActionBar().setTitle("CONTACTS DIRECTORY");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.add=findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherActivity=new Intent(getApplicationContext(), addperson.class);
                startActivity(otherActivity);
            }
        });


        lv = (ListView) findViewById(R.id.listview);
        myDbHelper = new PatientsDbHelper(this);
        db = myDbHelper.getReadableDatabase();

    }
    @Override
    protected void onResume() {
        displayData();
        super.onResume();

    }
    public void viewPatient(View v)
    {
        String tag = (String)v.getTag();
        System.out.println("The tag  = " + tag);

        Intent otherActivity= new Intent(getApplicationContext(), data.class);
        otherActivity.putExtra("id", tag);
        System.out.println("The value of the id of the next patient is " + tag);
        startActivity(otherActivity);
    }
    private void displayData() {
        db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name, id, surname FROM  Patients",null);
        Name.clear();
        Surname.clear();
        Id.clear();


        if (cursor.moveToFirst()) {
            do {
                Name.add(cursor.getString(cursor.getColumnIndex("name")));
                Id.add(cursor.getString(cursor.getColumnIndex("id")));
                Surname.add(cursor.getString(cursor.getColumnIndex("surname")));
            } while (cursor.moveToNext());
        }
        CustomAdapter ca = new CustomAdapter(contact_directory.this, Name, Surname, Id);
        lv.setAdapter(ca);
        //code to set adapter to populate list
        cursor.close();
    }
}


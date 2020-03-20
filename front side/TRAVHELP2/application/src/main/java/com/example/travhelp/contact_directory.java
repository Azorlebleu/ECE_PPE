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

    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_directory);




        getSupportActionBar().setTitle("CONTACTS DIRECTORY");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.add=findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherActivity=new Intent(getApplicationContext(),data.class);
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

        System.out.println("Bonjour1 ");
        Intent otherActivity=new Intent(getApplicationContext(),data.class);
        System.out.println("Bonjour2");
        otherActivity.putExtra("id", tag);
        System.out.println("The value of the id of the next patient is " + tag);
        System.out.println("Bonjour3");
    }
    private void displayData() {
        db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name, id FROM  Patients",null);
        System.out.println("jaja1");
        Name.clear();
        System.out.println("jaja2");

        if (cursor.moveToFirst()) {
            do {
                System.out.println("jaja_qui_se_repete");
                Name.add(cursor.getString(cursor.getColumnIndex("name")));
                Id.add(cursor.getString(cursor.getColumnIndex("id")));

            } while (cursor.moveToNext());
        }
        CustomAdapter ca = new CustomAdapter(contact_directory.this, Name, Id);
        lv.setAdapter(ca);
        //code to set adapter to populate list
        cursor.close();
    }
}


package com.example.travhelp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.travhelp.R;
import com.example.travhelp.addperson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class contact_directory extends AppCompatActivity {
    private ImageView add;
    private Button save_in_database;
    private Button selection;
    public PatientsDbHelper myDbHelper;
    public SelectionDbHelper mySelectionDbHelper;
    public SQLiteDatabase db;
    public SQLiteDatabase db_selection;
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

        save_in_database = (Button)findViewById(R.id.save_in_database);
        save_in_database.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    savePatientsInDatabase();
                                                }
                                            }
        );
        selection = (Button)findViewById(R.id.selection);
        selection.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View view) {
                                                    SelectionPatients();
                                                }
                                            }
        );

        lv = (ListView) findViewById(R.id.listview);

        //Instanciating the database in a thread for better performances
        final Context context = this;
        Thread thread = new Thread(){
            @Override
            public void run() {
                myDbHelper = new PatientsDbHelper(context);
                db = myDbHelper.getReadableDatabase();
                mySelectionDbHelper = new SelectionDbHelper(context);
                db_selection = mySelectionDbHelper.getWritableDatabase();

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
        while(db_selection==null){}
        System.out.println("Deleting all");
        db_selection.delete("selection",   "1=1" , null);
    }

    //Function called to open the activity where we see one patient's data
    public void viewPatient(View v)
    {
        //Patient's ID is stored in the tag
        String tag = (String)v.getTag();

        Intent otherActivity= new Intent(getApplicationContext(), data.class);
        System.out.println("Viewing patient with tag " + tag);
        otherActivity.putExtra("id", tag);
        startActivity(otherActivity);
    }

    //Function called to display the list of all the patients, using a template for each patient's row
    private void displayData() {

        //Get all the patients in a Cursor object which lets us extract the data within it easily
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
        CustomAdapter ca = new CustomAdapter(contact_directory.this, Name, Surname, Id);
        lv.setAdapter(ca);

        cursor.close();
    }

    public void savePatientsInDatabase()  {
        //Gets all data to send in a Cursor item
        Cursor cursor = db.rawQuery("SELECT * FROM  Patients",null);

        //Creates a JSON object for each patient
        if (cursor.moveToFirst()) {
            do {
                JSONObject data = new JSONObject();
                try {
                    data.put("id_nurse", "1");
                    data.put("id_patient", cursor.getString(cursor.getColumnIndex(PatientsContract.PatientsEntry._ID)));
                    data.put("name", cursor.getString(cursor.getColumnIndex(PatientsContract.PatientsEntry.COLUMN_NAME_name)));
                    data.put("address", cursor.getString(cursor.getColumnIndex(PatientsContract.PatientsEntry.COLUMN_NAME_address )));
                    data.put("notes", cursor.getString(cursor.getColumnIndex(PatientsContract.PatientsEntry.COLUMN_NAME_data)) );
                    data.put("number", cursor.getString(cursor.getColumnIndex(PatientsContract.PatientsEntry.COLUMN_NAME_phone)) );
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //Send the data to the address
                new myPostHTTP().execute("http://10.0.2.2:8080/api/new-patient", data);
            }
            while (cursor.moveToNext());
        }
    }

    public void SelectionPatients()  {

        Intent otherActivity=new Intent(getApplicationContext(),Selected_Patients.class);
        startActivity(otherActivity);
    }

    public void MyHandler(View view) {
        CheckBox cb = (CheckBox) view;
        //on récupère la position à l'aide du tag défini dans la classe MyListAdapter
        int position = Integer.parseInt(cb.getTag().toString());
        System.out.println("ID du patient à ajoute rdans la base de données selection : " + position);
        Cursor cursor = db.rawQuery("SELECT * FROM  Patients WHERE id = " + position,null);
        Cursor cursor_position = db_selection.rawQuery("SELECT * FROM selection WHERE id =" + position, null );

        if (cursor_position.moveToFirst())
        {
            if (!cb.isChecked()) {
                System.out.println("deleting this patient (jaja)");
                db_selection.delete("selection",   "id =" + position, null);
                //db.rawQuery("CREATE TABLE selection AS SELECT  Patients",null);
                //ajouter les patient sélectionnés dans la tabledelection
            }
        }
        else
        {
            //On change la couleur
            if (cb.isChecked()) {
                System.out.println("adding new patient to be selested for the 1st time");
                if (cursor.moveToFirst()){
                    ContentValues values = new ContentValues();
                    values.put(SelectionContract.SelectionEntry._ID, position);
                    values.put(SelectionContract.SelectionEntry.COLUMN_NAME_name, cursor.getString(cursor.getColumnIndex(PatientsContract.PatientsEntry.COLUMN_NAME_name)));
                    values.put(SelectionContract.SelectionEntry.COLUMN_NAME_surname, cursor.getString(cursor.getColumnIndex(PatientsContract.PatientsEntry.COLUMN_NAME_surname)));
                    values.put(SelectionContract.SelectionEntry.COLUMN_NAME_address, cursor.getString(cursor.getColumnIndex(PatientsContract.PatientsEntry.COLUMN_NAME_address)));
                    // Insert the new row in the database
                    db_selection.insert(SelectionContract.SelectionEntry.TABLE_NAME, null, values);
                }

            }

        }
    }



}

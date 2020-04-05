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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tournee);

        //Sets the title and display it
        getSupportActionBar().setTitle(R.string.fr_my_new_journey);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        //Instanciating the database in a thread for better performances
        final Context context = this;
        Thread thread = new Thread(){
            @Override
            public void run() {
                myDbHelper = new JourneyDbHelper(context);
                db = myDbHelper.getWritableDatabase();
            }
        };
        thread.start();
         Name = new ArrayList<String>();
         Surname = new ArrayList<String>();
         Time = new ArrayList<String>();
         Address = new ArrayList<String>();
         lv = (ListView)findViewById(R.id.listview);
    }

    public void getJourney(View v)
    {
        JSONArray jsonArray;
        try {
            jsonArray= new JSONArray("[{\"name\":\"Jean\",\"surname\":\"Dupond\",\"time\":\"8h30\",\"address\":\"4 rue de la mare Jouane Saint Arnoult en Yvelines France\"},{\"name\":\"Anouk\",\"surname\":\"Costa\",\"time\":\"9h00\",\"address\":\"15 rue Andr\u00e9 Thome Sonchamp France\"},{\"name\":\"Alice\",\"surname\":\"Simon\",\"time\":\"9h30\",\"address\":\"26 rue des paradis Saint Arnoult en Yvelines France\"}]");
            System.out.println(jsonArray);



            for (int i=0; i<jsonArray.length(); i++) {
                JSONObject patient = jsonArray.getJSONObject(i);
                Name.add(patient.getString("name"));
                Surname.add(patient.getString("surname"));
                Address.add(patient.getString("address"));
                Time.add(patient.getString("time"));

            }
            System.out.println(Name);
            System.out.println(Surname);
            System.out.println(Address);
            System.out.println(Time);

            for (int i =0; i<Name.size();i++)
            {
                ContentValues values = new ContentValues();
                values.put(JourneyContract.JourneyEntry.COLUMN_NAME_name, Name.get(i));
                values.put(JourneyContract.JourneyEntry.COLUMN_NAME_surname, Surname.get(i));
                values.put(JourneyContract.JourneyEntry.COLUMN_NAME_address, Address.get(i));
                values.put(JourneyContract.JourneyEntry.COLUMN_NAME_time, Time.get(i));
                db.insert(JourneyContract.JourneyEntry.TABLE_NAME, null, values);

            }
            displayData();

        } catch (JSONException e) {
            e.printStackTrace();


        }
    }


    private void displayData(){
        //Get all the patients in a Cursor object which lets us extract the data within it easily
        Cursor cursor = db.rawQuery("SELECT * FROM  Journey",null);

        //Erase lists from previous iteration to avoid printing data twice
        Name.clear();
        Surname.clear();
        Address.clear();
        Time.clear();

        //Adds the data from the SQL query in the the Name, Surname and Id lists
        if (cursor.moveToFirst()) {
            do {
                Name.add(cursor.getString(cursor.getColumnIndex("name")));
                Surname.add(cursor.getString(cursor.getColumnIndex("surname")));
                Time.add(cursor.getString(cursor.getColumnIndex("time")));
                Address.add(cursor.getString(cursor.getColumnIndex("address")));

            } while (cursor.moveToNext());
        }

        //Prints a patient for each data in the lists
        CustomAdapterJourney ca = new CustomAdapterJourney(tournee.this, Name, Surname, Address, Time);
        lv.setAdapter(ca);
        cursor.close();
    }


}

package com.example.travhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class addperson extends AppCompatActivity {
    private Button buttoncancel;

    PatientsDbHelper myDbHelper;
    EditText TextName;
    EditText TextSurname;
    EditText TextData;
    EditText TextAdress;
    EditText TextMail;
    EditText TextPhone;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addperson);
        getSupportActionBar().setTitle("ADD PERSON");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextName = (EditText) findViewById(R.id.editTextName);
        TextSurname = (EditText) findViewById(R.id.editTextSurname);
        TextData = (EditText) findViewById(R.id.editTextData);
        TextAdress = (EditText) findViewById(R.id.editTextAdress);
        TextMail = (EditText) findViewById(R.id.editTextMail);
        TextPhone=(EditText)findViewById(R.id.editTextPhone);

        this.buttoncancel=(Button) findViewById(R.id.buttoncancel);
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        myDbHelper = new PatientsDbHelper(this);
        db = myDbHelper.getWritableDatabase();
    }


    public void Save(View view){

        String name = TextName.getText().toString();
        String surname = TextSurname.getText().toString();
        String email = TextMail.getText().toString();
        String adress = TextAdress.getText().toString();
        String data = TextData.getText().toString();
        String phone = TextPhone.getText().toString();

        //Create a ContentValues item that hold the values we just got
        ContentValues values = new ContentValues();
        values.put(PatientsContract.PatientsEntry.COLUMN_NAME_name, name);
        values.put(PatientsContract.PatientsEntry.COLUMN_NAME_surname, surname);
        values.put(PatientsContract.PatientsEntry.COLUMN_NAME_email, email);
        values.put(PatientsContract.PatientsEntry.COLUMN_NAME_address, adress);
        values.put(PatientsContract.PatientsEntry.COLUMN_NAME_data, data);
        values.put(PatientsContract.PatientsEntry.COLUMN_NAME_phone, phone);

        // Insert the new row in the database
        db.insert(PatientsContract.PatientsEntry.TABLE_NAME, null, values);
        finish();
    }
}

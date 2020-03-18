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
import android.widget.ImageView;
import android.widget.TextView;

public class addperson extends AppCompatActivity {
    private Button buttoncancel;
    private Button buttonsave;

    PatientsDbHelper myDbHelper;
    EditText editTextName;
    EditText editTextSurname;
    TextView textView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addperson);
        getSupportActionBar().setTitle("ADD PERSON");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextSurname = (EditText) findViewById(R.id.editTextSurname);
        textView = (TextView) findViewById(R.id.tv1);

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        String name = sharedPreferences.getString("name", "");
        String surname = sharedPreferences.getString("surname", "");
        textView.setText("Save " + name + " " + surname + "?");
        this.buttoncancel=(Button) findViewById(R.id.buttoncancel);
        buttoncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent otherActivity=new Intent(getApplicationContext(),contact_directory.class);
                startActivity(otherActivity);
                finish();

            }
        });

    }
    public void Save(View view){
        myDbHelper = new PatientsDbHelper(getApplicationContext());

        SQLiteDatabase db = myDbHelper.getWritableDatabase();
        System.out.println("THis is my db " + db);
        ContentValues values = new ContentValues();
        values.put(PatientsContract.PatientsEntry.COLUMN_NAME_name, "Caro");
        values.put(PatientsContract.PatientsEntry.COLUMN_NAME_surname, "Caro");
        values.put(PatientsContract.PatientsEntry.COLUMN_NAME_email, "Caro");
        values.put(PatientsContract.PatientsEntry.COLUMN_NAME_address, "Caro");
        values.put(PatientsContract.PatientsEntry.COLUMN_NAME_data, "Caro");


// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(PatientsContract.PatientsEntry.TABLE_NAME, null, values);

        String name = editTextName.getText().toString();
        String surname = editTextSurname.getText().toString();

        SharedPreferences sharedPreferences = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("name", name);
        editor.putString("surname", surname);

        editor.apply();
        editor.remove("name");
        editor.remove("surname");

    }

}

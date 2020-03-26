package com.example.travhelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.view.View;


import static com.example.travhelp.PatientsContract.PatientsEntry.TABLE_NAME;


public class update extends AppCompatActivity {


    PatientsDbHelper myDbHelper;
    SQLiteDatabase db;

    private String Name1;
    private String Surname1;
    private String Address1;
    private String Mail1;
    private String Phone1;
    private String Data1;
    public String ID2;
    Button btnCancel, btnUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Intent intent = getIntent();

        ID2 = intent.getStringExtra("id");//)Id du patient à aller chercher
        TextView myTextView1 = (TextView) findViewById(R.id.id_patient1);
        myTextView1.setText(ID2);

        System.out.println("LA ICI L'ID  VAUT:   ID=" +ID2);


        myDbHelper = new PatientsDbHelper(this);
        db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  Patients WHERE id="+ID2 , null);



        if (cursor.moveToFirst()) {
            do {
                //Id1 = (cursor.getString(cursor.getColumnIndex("id")));
                Name1 = (cursor.getString(cursor.getColumnIndex("name")));
                Surname1 = (cursor.getString(cursor.getColumnIndex("surname")));
                Address1 = (cursor.getString(cursor.getColumnIndex("address")));
                Mail1 = (cursor.getString(cursor.getColumnIndex("phone")));
                Phone1 = (cursor.getString(cursor.getColumnIndex("name")));
                Data1 = (cursor.getString(cursor.getColumnIndex("data")));
            } while (cursor.moveToNext());
        }


        EditText myTextViewName1 = (EditText) findViewById(R.id.name_patient1);
        EditText myTextViewSurname1 = (EditText) findViewById(R.id.surname_patient1);
        EditText myTextViewAddress1 = (EditText) findViewById(R.id.adress_patient1);
        EditText myTextViewMail1 = (EditText) findViewById(R.id.mail_patient1);
        EditText myTextViewData1 = (EditText) findViewById(R.id.data_patient1);
        EditText myTextViewPhone1 = (EditText) findViewById(R.id.phone_patient1);

        myTextViewName1.setText(Name1);
        myTextViewSurname1.setText(Surname1);
        myTextViewAddress1.setText(Address1);
        myTextViewMail1.setText(Phone1);
        myTextViewData1.setText(Data1);
        myTextViewPhone1.setText(Mail1);



        cursor.close();


        this.btnCancel=findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
            } // fin supprimer

        });

        this.btnUpdate=findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText myTextViewName2 = (EditText) findViewById(R.id.name_patient1);
                EditText myTextViewSurname2 = (EditText) findViewById(R.id.surname_patient1);
                EditText myTextViewAddress2 = (EditText) findViewById(R.id.adress_patient1);
                EditText myTextViewMail2 = (EditText) findViewById(R.id.mail_patient1);
                EditText myTextViewData2 = (EditText) findViewById(R.id.data_patient1);
                EditText myTextViewPhone2 = (EditText) findViewById(R.id.phone_patient1);


                String a = myTextViewName2.getText().toString();
                String b = myTextViewSurname2.getText().toString();
                String c = myTextViewAddress2.getText().toString();
                String d = myTextViewMail2.getText().toString();
                String e = myTextViewData2.getText().toString();
                String f = myTextViewPhone2.getText().toString();

                ContentValues cv = new ContentValues();
                cv.put("name",a);
                cv.put("surname", b);
                cv.put("email", c);
                cv.put("phone",d);
                cv.put("data", e );
                cv.put("address",f);



                db.update(TABLE_NAME, cv, "id="+ID2, null);
                System.out.println("ENCORE ICI L'ID  VAUT:   ID=" +ID2);
                finish();
            }
        });




    }
}

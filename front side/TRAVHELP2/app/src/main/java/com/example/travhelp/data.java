package com.example.travhelp;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.widget.Button;
        import android.widget.TextView;
        import android.view.View;



        import java.util.ArrayList;

public class data extends AppCompatActivity {

    PatientsDbHelper myDbHelper;
    SQLiteDatabase db;

    private String Name;
    private String Surname;
    private String Address;
    private String Mail;
    private String Phone;
    private String Data;

    Button btnSupprimer, btnModifier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Intent intent = getIntent();

        String ID = intent.getStringExtra("id");//)Id du patient à aller chercher
        TextView myTextView = (TextView) findViewById(R.id.id_patient);
        myTextView.setText(ID);


        myDbHelper = new PatientsDbHelper(this);
        db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  Patients WHERE id =" + ID, null);

        if (cursor.moveToFirst()) {
            do {
                Name = (cursor.getString(cursor.getColumnIndex("name")));
                Surname = (cursor.getString(cursor.getColumnIndex("surname")));
                Address = (cursor.getString(cursor.getColumnIndex("address")));
                Mail = (cursor.getString(cursor.getColumnIndex("phone")));
                Phone = (cursor.getString(cursor.getColumnIndex("name")));
                Data = (cursor.getString(cursor.getColumnIndex("data")));
            } while (cursor.moveToNext());
        }


        TextView myTextViewName = (TextView) findViewById(R.id.name_patient);
        TextView myTextViewSurname = (TextView) findViewById(R.id.surname_patient);
        TextView myTextViewAddress = (TextView) findViewById(R.id.adress_patient);
        TextView myTextViewMail = (TextView) findViewById(R.id.mail_patient);
        TextView myTextViewData = (TextView) findViewById(R.id.data_patient);
        TextView myTextViewPhone = (TextView) findViewById(R.id.phone_patient);

        myTextViewName.setText(Name);
        myTextViewSurname.setText(Surname);
        myTextViewAddress.setText(Address);
        myTextViewMail.setText(Phone);
        myTextViewData.setText(Data);
        myTextViewPhone.setText(Mail);

        cursor.close();




        this.btnSupprimer=findViewById(R.id.btnSupprimer);


        btnSupprimer.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = getIntent();
            String ID = intent.getStringExtra("id");//)Id du patient à aller chercher
            TextView myTextView = (TextView) findViewById(R.id.id_patient);
            myTextView.setText(ID);

            db = myDbHelper.getWritableDatabase();

            db.delete("patients",   "id =" + ID, null);
            onBackPressed();
        } // fin supprimer

    });
}
    }

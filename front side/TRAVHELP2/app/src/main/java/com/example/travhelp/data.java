package com.example.travhelp;

        import androidx.appcompat.app.AppCompatActivity;

        import android.content.Intent;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.os.Bundle;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.view.View;

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
    public String ID ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        Intent intent = getIntent();

        ID = intent.getStringExtra("id");
        TextView myTextView = (TextView) findViewById(R.id.id_patient);
        myTextView.setText(ID);

        System.out.println("LOL ICI CA VAUT ="+ID);

        myDbHelper = new PatientsDbHelper(this);
        db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM  patients WHERE id =" + ID, null);
        System.out.println("LOL ICI CA VAUT ="+ID);
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
        System.out.println("LOL ICI CA VAUT ="+ID);

        TextView myTextViewName = (TextView) findViewById(R.id.name_patient);
        TextView myTextViewSurname = (TextView) findViewById(R.id.surname_patient);
        TextView myTextViewAddress = (TextView) findViewById(R.id.adress_patient);
        TextView myTextViewMail = (TextView) findViewById(R.id.mail_patient);
        TextView myTextViewData = (TextView) findViewById(R.id.data_patient);
        TextView myTextViewPhone = (TextView) findViewById(R.id.phone_patient);
        System.out.println("LOL ICI CA VAUT ="+ID);
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

        this.btnModifier=findViewById(R.id.btnModifier);
        btnModifier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String)v.getTag();
                Intent otherActivity=new Intent(getApplicationContext(), update.class);
                otherActivity.putExtra("id", ID);
                startActivity(otherActivity);
            } // fin supprimer

        });
    }
}

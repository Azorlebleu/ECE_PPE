package com.example.travhelp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class contact_directory extends AppCompatActivity {
    private ImageView add;
    ListView mListView;
    String[] prenoms = new String[]{
            "Antoine", "Benoit", "Cyril", "David", "Eloise", "Florent",
            "Gerard", "Hugo", "Ingrid", "Jonathan", "Kevin", "Logan",
            "Mathieu", "Noemie", "Olivia", "Philippe", "Quentin", "Romain","Sophie",
            "Tristan", "Ulric", "Vincent", "Willy", "Xavier","Yann", "Zoé"};
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
                Intent otherActivity=new Intent(getApplicationContext(),addperson.class);
                startActivity(otherActivity);
            }
        });



        mListView = (ListView) findViewById(R.id.listView);
        //android.R.layout.simple_list_item_1 est une vue disponible de base dans le SDK android,
        //Contenant une TextView avec comme identifiant "@android:id/text1"
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(contact_directory.this,
                android.R.layout.simple_list_item_1, prenoms);
        mListView.setAdapter(adapter);

    }

}



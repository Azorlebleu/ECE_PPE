package com.example.travhelp;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=(ProgressBar) findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(0);
        Thread thread = new Thread(){
            @Override
            public void run() {
                    try {
                        for (int i = 0; i < 100; i++) {
                        progressBar.setProgress(i);
                        sleep(20);}
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally{
                        Intent intent=new Intent(getApplicationContext(), Home.class);
                        startActivity(intent);
                        finish();
                    }
                }
        };
        thread.start();
    }
}



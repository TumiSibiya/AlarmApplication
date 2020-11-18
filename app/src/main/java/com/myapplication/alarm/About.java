package com.myapplication.alarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.widget.Button;

import android.view.View;

public class About extends AppCompatActivity {

    Button buttonClose;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);
        setTitle("About");

        buttonClose = findViewById(R.id.button);

        buttonClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                onStop();
            }
        });

    }
}

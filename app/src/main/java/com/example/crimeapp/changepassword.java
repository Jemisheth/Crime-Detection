package com.example.crimeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class changepassword extends AppCompatActivity {

    EditText changenewpass, confirmchangepass;
    Button btnchangepass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changepassword);

        changenewpass = findViewById(R.id.changenewpass);
        confirmchangepass = findViewById(R.id. confirmchangepass);

        Button btnchangepass = findViewById(R.id.btnchangepass);

    }
}
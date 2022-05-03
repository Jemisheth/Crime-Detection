package com.example.crimeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class Forgetpassword extends AppCompatActivity {

    TextView btnback;
    EditText frgtpass;
    Button resetbtn;
    FirebaseAuth mAuth;
    private String email;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);

        mAuth = FirebaseAuth.getInstance();


        frgtpass = findViewById(R.id.frgtpass);

        Button resetbtn = findViewById(R.id.resetbtn);
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        btnback = findViewById(R.id.btnback);
        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Forgetpassword.this, MainActivity.class);
                startActivity(i);
            }
        });
    }

    private void validateData() {

        email = frgtpass.getText().toString();

        if(TextUtils.isEmpty((email)) )
        {
            frgtpass.setError("Email cannot be empty");
            frgtpass.requestFocus();
        }
        else{
            forgetPass();
        }
    }

    private void forgetPass() {

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if (task.isSuccessful()){

                    Toast.makeText(Forgetpassword.this, "Check Your Email", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Forgetpassword.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }else{

                    Toast.makeText(Forgetpassword.this, "Error  : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
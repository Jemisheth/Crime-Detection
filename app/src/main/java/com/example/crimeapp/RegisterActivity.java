package com.example.crimeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class RegisterActivity extends AppCompatActivity  {

    Button buttonregister;
    private EditText firstname,lastname,email,contactNo,password;
    FirebaseAuth mAuth;
    private Object User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.email);
        contactNo = findViewById(R.id.contactNo);
        password = findViewById(R.id.password);
        TextView btn = findViewById(R.id.btnregister);

        mAuth = FirebaseAuth.getInstance();

        btn.setOnClickListener(view ->{
                createUser();

        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(i);
            }
        });
        buttonregister = findViewById(R.id.buttonregister);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUser();
            }
        });
    }

    private void createUser() {

            String fname = firstname.getText().toString();
            String Email = email.getText().toString();
            String phone = contactNo.getText().toString();
            String Password = password.getText().toString();

        if(fname.isEmpty() || firstname.length()<4)
        {
            showerror(firstname,"Username Not Valid");
        }
        else if (Email.isEmpty() || !Email.contains("@"))
        {
            showerror(email, "Email is not valid");
            email.requestFocus();
        }
        else if(Password.isEmpty() || password.length()<8)
        {
            showerror(password,"Password must be atleast 8 characters");
            password.requestFocus();
        }
        else if(phone.isEmpty() || phone.length()<10)
        {
            showerror(contactNo,"Number not valid");
            contactNo.requestFocus();
        }
        else
        {

            mAuth.createUserWithEmailAndPassword(Email,Password).addOnCompleteListener(this , new OnCompleteListener<AuthResult>() {

                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        User user = new User(fname,Email,Password,phone);
                        FirebaseDatabase.getInstance().getReference("users")
                                .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                .setValue(User).addOnCompleteListener(new OnCompleteListener<Void>() {

                                  @Override
                                  public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "User registered successfully!!!!", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(RegisterActivity.this,MainActivity.class);
                                        startActivity(i);

                                }else{
                                    Toast.makeText(RegisterActivity.this, "User registration failed" +task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                    }else{
                        Toast.makeText(RegisterActivity.this, "User registration failed!!!!", Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
    private void showerror(EditText input, String s) {
        input.setError(s);
        input.requestFocus();
    }
}
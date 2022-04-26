package com.example.crimeapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Crime_Activity extends AppCompatActivity {

    Button Murderbtn,Violencebtn,Friringbtn,Robberybtn,IdentityTheft,Kidnappingbtn,Burglarybtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.crime);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuitem) {

                switch (menuitem.getItemId()){
                    case R.id.crime:
                        return;

                    case R.id.home: startActivity(new Intent(getApplicationContext(),HomePage.class));
                        overridePendingTransition(0,0);
                        return;

                    case R.id.addimage: startActivity(new Intent(getApplicationContext(),ImageActivity.class));
                        overridePendingTransition(0,0);
                        return;
                }
            }
        });

        Murderbtn = findViewById(R.id.Murderbtn);
        Murderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Crime_Activity.this,MurderActivity.class);
                startActivity(i);
            }
        });

        Robberybtn = findViewById(R.id.Robberybtn);
        Robberybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Crime_Activity.this,RobberyActivity.class);
                startActivity(i);
            }
        });

        Violencebtn = findViewById(R.id.Violencebtn);
        Violencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Crime_Activity.this,ViolenceActivity.class);
                startActivity(i);
            }
        });

        Friringbtn = findViewById(R.id.Firingbtn);
        Friringbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Crime_Activity.this,FiringActivity.class);
                startActivity(i);
            }
        });

        IdentityTheft = findViewById(R.id.IdentityTheft);
        IdentityTheft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Crime_Activity.this, IdentityTheftActivity.class);
                startActivity(i);
            }
        });

        Kidnappingbtn = findViewById(R.id.Kidnappingbtn);
        Kidnappingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Crime_Activity.this,KidnappingActivity.class);
                startActivity(i);
            }
        });

        Burglarybtn = findViewById(R.id.Burglarybtn);
        Burglarybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Crime_Activity.this,BurglaryActivity.class);
                startActivity(i);
            }
        });
    }
}
package com.example.crimeapp;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;

public class ImageActivity extends FragmentActivity implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    // initialize the variable fot the date.
    TextView tvdate;
    DatePickerDialog.OnDateSetListener setListener;

    // initialize the variable fot the time.
    TextView tvtime;
    int t1Hour,t1Minute;
    EditText Details;
    SupportMapFragment mapFragment;
    GoogleMap map;
    int SELECT_PHOTO = 10;
    Uri uri;
    ImageView imageView;
    private Spinner cType;
    Button Submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        //Assign Variable for the time picker

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("message");

        myRef.setValue("Hello, World!");

        tvtime = findViewById(R.id.tvtime);
        tvtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // initialize the time picker dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(ImageActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        // initialize hour and minute
                        t1Hour = hourOfDay;
                        t1Minute = minute;

                        // initialize the calender

                        Calendar calendar = Calendar.getInstance();

                        // Set hour and minute

                        calendar.set(0,0,0,t1Hour,t1Minute);

                        // set the selected time on the textview

                        tvtime.setText(DateFormat.format("hh:mm aa",calendar));

                    }
                }, 12,0,false
                );

                // display previous selected time

                timePickerDialog.updateTime(t1Hour,t1Minute);
                timePickerDialog.show();


            }

        });


        //Assign Variable for the date picker
        tvdate = findViewById(R.id.tvdate);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        tvdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(ImageActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth
                ,setListener,year,month,day);

                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                month = month+1;
                String date = day+"/"+month+"/"+year;
                tvdate.setText(date);

            }
        };

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.crime);

        //Creating Spinner for crime type selection

        cType = findViewById(R.id.crimetypes);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.crimeType, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cType.setAdapter(adapter);

        cType.setOnItemSelectedListener(this);

        bottomNavigationView.setOnNavigationItemReselectedListener(new BottomNavigationView.OnNavigationItemReselectedListener() {
            @Override
            public void onNavigationItemReselected(@NonNull MenuItem menuitem) {

                switch (menuitem.getItemId()) {
                    case R.id.crime:
                        startActivity(new Intent(getApplicationContext(), Crime_Activity.class));
                        overridePendingTransition(0, 0);
                        return;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                        overridePendingTransition(0, 0);
                        return;

                    case R.id.addimage:
                        return;
                }
            }
        });

        Button Choose = findViewById(R.id.choose);
        imageView = findViewById(R.id.imageview);

        Choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK);
                i.setType("image/*");
                startActivityForResult(i, SELECT_PHOTO);
            }
        });
//        Details = findViewById(R.id.adddetails);
//        Submit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String detail = Details.getText().toString();
//                if(detail.isEmpty()){
//                    Toast.makeText(ImageActivity.this, "Description is empty", Toast.LENGTH_SHORT).show();
//                }else {
//                    FirebaseDatabase.getInstance().getReference().child("addReport").child("Description").setValue("");
//                }
//            }
//        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                imageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        map = googleMap;

        googleMap.getUiSettings().setZoomControlsEnabled(true);
        googleMap.getUiSettings().setCompassEnabled(true);
        googleMap.getUiSettings().setScrollGesturesEnabled(true);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        String choice =  adapterView.getItemAtPosition(1).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
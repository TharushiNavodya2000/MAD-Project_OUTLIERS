package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Create_Itinerary extends AppCompatActivity {

    DatabaseReference dbRef;
    Itinerary Itin;
    Button startPlanning;
    SearchView Location;
    Button StartDate, EndDate;
    EditText startDate, endDate;
    private int mYear, mMonth, mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_itinerary);

        Itin = new Itinerary();

        Location = findViewById(R.id.idSearchView);
        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Itinerary.this, MapsActivity.class);
                startActivity(intent);
            }
        });

        StartDate = findViewById(R.id.btn_Startdate);
        startDate = findViewById(R.id.startDate);

        StartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Create_Itinerary.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                startDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        EndDate = findViewById(R.id.btn_EndDate);
        endDate = findViewById(R.id.endDate);

        EndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Create_Itinerary.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                endDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        startPlanning = findViewById(R.id.PlanButton);
        startPlanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create functionality
                dbRef = FirebaseDatabase.getInstance().getReference().child("Itinerary");//creating the document in the firebase
                DatabaseReference pushRef = dbRef.push();
                try{
                    if(TextUtils.isEmpty(Location.getQuery().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a location",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(startDate.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a start date",Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(endDate.getText().toString()))
                        Toast.makeText(getApplicationContext(),"Please enter a end date",Toast.LENGTH_SHORT).show();
                    else {
                        Itin.setLocation(Location.getQuery().toString().trim());
                        Itin.setStartDate(startDate.getText().toString().trim());
                        Itin.setEndDate(endDate.getText().toString().trim());

                        pushRef.setValue(Itin);
                        Toast.makeText(getApplicationContext(),"Itinerary created successfully",Toast.LENGTH_SHORT).show();

                        //Assigning the document key
                        String ItineraryKey = pushRef.getKey();

                        Intent intent = new Intent(Create_Itinerary.this, StartItinerary.class);
                        intent.putExtra("Key",ItineraryKey);
                        startActivity(intent);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

//    public void showStartDatePickerDialog(View v) {
//        DialogFragment newStartFragment = new DatePickerFragment();
//        newStartFragment.show(getSupportFragmentManager(), "datePicker");
//    }
//
//    public void showEndDatePickerDialog(View v) {
//        DialogFragment newEndFragment = new DatePickerFragment();
//        newEndFragment.show(getSupportFragmentManager(), "datePicker");
//    }
}

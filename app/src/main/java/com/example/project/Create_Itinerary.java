package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Create_Itinerary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_itinerary);

        Button startPlanning = findViewById(R.id.PlanButton);
        startPlanning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Itinerary.this, StartItinerary.class);
                startActivity(intent);
            }
        });

        SearchView Location = findViewById(R.id.idSearchView);
        Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Create_Itinerary.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void showStartDatePickerDialog(View v) {
        DialogFragment newStartFragment = new DatePickerFragment();
        newStartFragment.show(getSupportFragmentManager(), "datePicker");
    }

    public void showEndDatePickerDialog(View v) {
        DialogFragment newEndFragment = new DatePickerFragment();
        newEndFragment.show(getSupportFragmentManager(), "datePicker");
    }
}

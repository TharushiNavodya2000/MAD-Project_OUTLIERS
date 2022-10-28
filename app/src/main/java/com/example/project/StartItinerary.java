package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class StartItinerary extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_itinerary);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        TextView Location = findViewById(R.id.textView2);
        ImageView Delete = findViewById(R.id.DeleteBtn);

        String key = getIntent().getStringExtra("Key");

        //read functionality
        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Itinerary").child(key);
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    Location.setText(snapshot.child("location").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //delete functionality
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readRef.removeValue();
                Toast.makeText(getApplicationContext(),"Itinerary deleted successfully",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(StartItinerary.this,Create_Itinerary.class);
                startActivity(intent);
            }
        });

        tabLayout.setupWithViewPager(viewPager);

        ViewpageAdapter vpAdapter = new ViewpageAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdapter.addFragment(new ItineraryFragment(),"ITINERARY");
        vpAdapter.addFragment(new MapFragment(),"MAP");
        vpAdapter.addFragment(new ExpensesFragment(),"EXPENSES");

        viewPager.setAdapter(vpAdapter);
    }
}
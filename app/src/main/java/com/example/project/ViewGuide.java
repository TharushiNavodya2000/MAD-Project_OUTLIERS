package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ViewGuide extends AppCompatActivity {

    Button closeCreateGuide, UpdateBtn;
    DatabaseReference dbRef;
    GuidesInfo guideInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_guide);

        TextView GuideTitle = findViewById(R.id.guidetitleview);
        TextView GuideDescription = findViewById(R.id.guidedescriptionview);
        ImageView Delete = findViewById(R.id.deleteguide);
        closeCreateGuide = findViewById(R.id.closeCreateGuide);
        UpdateBtn = findViewById(R.id.updateguide);

        String key = getIntent().getStringExtra("Key");

        //read functionality
        DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Guides").child(key);
        readRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChildren()){
                    GuideTitle.setText(snapshot.child("guideTitle").getValue(String.class));
                    GuideDescription.setText(snapshot.child("guideDescription").getValue(String.class));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        closeCreateGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        UpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //View functionality
                DatabaseReference viewRef = FirebaseDatabase.getInstance().getReference().child("Guides").child(key);
                viewRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.hasChildren()) {
                            GuideTitle.setText(snapshot.child("guideTitle").getValue().toString());
                            GuideDescription.setText(snapshot.child("guideDescription").getValue().toString());

                            Intent intent = new Intent(ViewGuide.this, ViewGuide.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(getApplicationContext(), "No source to display", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        //delete functionality
        Delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readRef.removeValue();
                Toast.makeText(getApplicationContext(), "Guide deleted successfully", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ViewGuide.this, GuidesForm.class);
                startActivity(intent);
            }
        });

    }
}
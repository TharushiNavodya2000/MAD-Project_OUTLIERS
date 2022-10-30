package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GuidesForm extends AppCompatActivity {

    DatabaseReference dbRef;

    GuidesInfo guideInfo;
    Button SaveButton, closeCreateGuide, UpdateButton;
    EditText GuideTitle, GuideDescription;
    ImageButton ViewButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides_form);

        guideInfo = new GuidesInfo();

        GuideTitle = findViewById(R.id.editText3);
        GuideDescription = findViewById(R.id.editText);
        SaveButton = findViewById(R.id.SaveButton);
        closeCreateGuide = findViewById(R.id.closeCreateGuide);
        UpdateButton = findViewById(R.id.UpdateButton);
        ViewButton = findViewById(R.id.ViewGuides);

        dbRef = FirebaseDatabase.getInstance().getReference().child("Guides");//creating the document in the firebase
        DatabaseReference pushRef = dbRef.push();

        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (TextUtils.isEmpty(GuideTitle.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a title", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(GuideDescription.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter a description", Toast.LENGTH_SHORT).show();
                    else {
                        guideInfo.setGuideTitle(GuideTitle.getText().toString().trim());
                        guideInfo.setGuideDescription(GuideDescription.getText().toString().trim());

                        pushRef.setValue(guideInfo);
                        Toast.makeText(getApplicationContext(), "Guide created successfully", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });

        UpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pushRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                            try {
                                guideInfo.setGuideTitle(GuideTitle.getText().toString().trim());
                                guideInfo.setGuideDescription(GuideDescription.getText().toString().trim());

                                pushRef.setValue(guideInfo);

                                Toast.makeText(getApplicationContext(), "Updated Successfully", Toast.LENGTH_SHORT).show();

                            } catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Invalid Input", Toast.LENGTH_SHORT).show();
                            }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        ViewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guideInfo.setGuideTitle(GuideTitle.getText().toString().trim());
                guideInfo.setGuideDescription(GuideDescription.getText().toString().trim());

                pushRef.setValue(guideInfo);

                //Assigning the document key
                String GuideKey = pushRef.getKey();

                Intent intent = new Intent(GuidesForm.this, ViewGuide.class);
                intent.putExtra("Key", GuideKey);
                startActivity(intent);

            }
        });


        closeCreateGuide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
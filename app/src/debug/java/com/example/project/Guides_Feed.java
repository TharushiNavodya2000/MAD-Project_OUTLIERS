package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class Guides_Feed extends AppCompatActivity {

    int[] images = {R.drawable.galle, R.drawable.kandy, R.drawable.vatadageya};
    String[] guideDescription;
    String[] user;
    RecyclerView recycleViewGuides;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides_feed);

        guideDescription = getResources().getStringArray(R.array.guideDescription);
        user = getResources().getStringArray(R.array.users);
        recycleViewGuides = findViewById(R.id.recycleViewGuides);


        GuideAdapter guideAdapter = new GuideAdapter(this, guideDescription, user, images);
        recycleViewGuides.setAdapter(guideAdapter);
        recycleViewGuides.setLayoutManager(new LinearLayoutManager(this));

    }
}
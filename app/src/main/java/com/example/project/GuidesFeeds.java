package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class GuidesFeeds extends AppCompatActivity {
    String places[];
    String desriptions[];
    int images[]= {R.drawable.galle,R.drawable.kandy,R.drawable.vatadageya};

    RecyclerView recyclerView;

    FloatingActionButton CreateGuide;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guides_feeds);

        places = getResources().getStringArray(R.array.users);
        desriptions = getResources().getStringArray(R.array.guideDescription);
        recyclerView = findViewById(R.id.recycleView);

        GuidesAdapter myAdapter = new GuidesAdapter(this,places, desriptions, images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CreateGuide = findViewById(R.id.guide);
        CreateGuide.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
               Intent intent = new Intent(GuidesFeeds.this, GuidesForm.class);
               startActivity(intent);
            }
        });
    }
}

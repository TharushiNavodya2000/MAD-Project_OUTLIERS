package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewGuidesFeed extends AppCompatActivity {
    String places[];
    String desriptions[];
    int images[]= {R.drawable.galle,R.drawable.kandy,R.drawable.mahamewnawa,R.drawable.nelumkuluna,R.drawable.seema_malakaya,
            R.drawable.sigiriya,R.drawable.vatadageya};

    RecyclerView recyclerView;

    FloatingActionButton GoToNextPage;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_guides_feed);

        places = getResources().getStringArray(R.array.users);
        desriptions = getResources().getStringArray(R.array.guideDescription);
        recyclerView = findViewById(R.id.recycleView);

        AdapterGuides myAdapter = new AdapterGuides(this,places,desriptions,images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GoToNextPage = findViewById(R.id.guide);
        GoToNextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewGuidesFeed.this, Create_Guide_Search.class);
                startActivity(intent);
            }
        });

    }
}
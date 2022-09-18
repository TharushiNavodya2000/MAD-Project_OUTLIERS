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

public class ViewBlog extends AppCompatActivity {
    String places[];
    String desriptions[];
    int images[]= {R.drawable.galle,R.drawable.kandy,R.drawable.mahamewnawa,R.drawable.nelumkuluna,R.drawable.seema_malakaya,
                R.drawable.sigiriya,R.drawable.vatadageya};
    Button button;
    Button buttonguide;
    RecyclerView recyclerView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blog);
        places = getResources().getStringArray(R.array.place);
        desriptions = getResources().getStringArray(R.array.description);
        recyclerView = findViewById(R.id.recycleView);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ViewBlog.this,"Click button",Toast.LENGTH_LONG).show();
            }
        });
        BlogAdapter myAdapter = new BlogAdapter(this,places,desriptions,images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        buttonguide = findViewById(R.id.guide);
        buttonguide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewBlog.this, CreateGuide.class);
                startActivity(intent);
            }
        });
    }
}
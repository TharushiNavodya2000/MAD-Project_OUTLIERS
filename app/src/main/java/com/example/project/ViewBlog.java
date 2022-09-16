package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

public class ViewBlog extends AppCompatActivity {


    String places[];
    String desriptions[];
    int images[]= {R.drawable.galle,R.drawable.kandy,R.drawable.mahamewnawa,R.drawable.nelumkuluna,R.drawable.seema_malakaya,
                R.drawable.sigiriya,R.drawable.vatadageya};
    RecyclerView recyclerView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_blog);
        places = getResources().getStringArray(R.array.place);
        desriptions = getResources().getStringArray(R.array.description);
        recyclerView = findViewById(R.id.recycleView);
        BlogAdapter myAdapter = new BlogAdapter(this,places,desriptions,images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
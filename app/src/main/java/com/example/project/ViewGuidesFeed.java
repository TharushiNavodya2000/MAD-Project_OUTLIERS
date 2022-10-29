package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ViewGuidesFeed extends AppCompatActivity {
    String places[];
    String desriptions[];
    int images[]= {R.drawable.galle,R.drawable.kandy,R.drawable.mahamewnawa,R.drawable.nelumkuluna,R.drawable.seema_malakaya,
            R.drawable.sigiriya,R.drawable.vatadageya};

    Dialog myDialog;

    RecyclerView recyclerView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_guides_feed);
        setContentView(R.layout.create_guide_popup);
        myDialog = new Dialog(this);

        places = getResources().getStringArray(R.array.users);
        desriptions = getResources().getStringArray(R.array.guideDescription);
        recyclerView = findViewById(R.id.recycleView);

        AdapterGuides myAdapter = new AdapterGuides(this,places,desriptions,images);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public void ShowPopup(View view) {
        TextView txtclose;
        Button btnOk;
        myDialog.setContentView(R.layout.create_guide_popup);
        txtclose = (TextView) myDialog.findViewById(R.id.txtclose);
        btnOk = (Button) myDialog.findViewById(R.id.btnok);
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDialog.dismiss();
            }
        });
        myDialog.show();
    }
}
package com.example.project;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


public class Blog extends AppCompatActivity {

    private EditText placeTxt;
    private EditText discriptionTxt;
    private TextView addimage;
    private Button upload;
    private ImageView images;
    private ProgressBar progressBar;
    private Uri imageuri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        placeTxt = findViewById(R.id.bplaceTxt);
        discriptionTxt = findViewById(R.id.bdiscriptionTxt);
        addimage = findViewById(R.id.badd);
        upload = findViewById(R.id.bbutton);
        images = findViewById(R.id.bimage);
        progressBar = findViewById(R.id.bprogressBar);
        addimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] option = {"Take Photo","From Gallary","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Blog.this);
                builder.setTitle("Select your option");
                builder.setItems(option, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch(i)
                        {
                            case 0:
                            {
                                Intent takephoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                startActivityForResult(takephoto,0);
                            }
                            case 1:
                            {
                                Intent pickphoto = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickphoto,0);
                            }
                            case 2:
                            {
                                dialogInterface.dismiss();
                            }
                        }
                    }
                });
                builder.show();
            }
        });

    }

}

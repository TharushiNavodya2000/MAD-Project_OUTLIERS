package com.example.project;
import static java.security.AccessController.getContext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.Map;


public class Blog extends AppCompatActivity {

    private EditText placeTxt;
    private EditText discriptionTxt;
    private TextView addimage;
    private Button upload;
    private ImageView images;
    private ProgressBar progressBar;
    private Uri imageuri;
    Bitmap image_file;
    ConstraintLayout bloglaout;
    FirebaseAuth auth;
    FirebaseFirestore store;
    String UserID;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != RESULT_CANCELED)
        {
            switch(requestCode)
            {
                case 0:
                    if(resultCode == RESULT_OK && data != null) {
                        image_file = (Bitmap) data.getExtras().get("data");
                        images.setImageBitmap(image_file);
                        Snackbar snackbar = Snackbar.make(bloglaout,"Your Image Successfully upload",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar secoundsnackbar = Snackbar.make(bloglaout,"Done",Snackbar.LENGTH_SHORT);
                                secoundsnackbar.show();
                            }
                        });
                        snackbar.show();
                    }
                    else
                    {
                        Snackbar snackbar = Snackbar.make(bloglaout,"Your Image can not upload Try again",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar secoundsnackbar = Snackbar.make(bloglaout,"Done",Snackbar.LENGTH_SHORT);
                                secoundsnackbar.show();
                            }
                        });
                        snackbar.show();
                    }
                    break;
                case 1:
                {
                    if(resultCode == RESULT_OK && data != null)
                    {
                        Uri path = data.getData();
                        try {
                            image_file = MediaStore.Images.Media.getBitmap(getBaseContext().getContentResolver(),path);
                            images.setImageBitmap(image_file);
                            Snackbar snackbar = Snackbar.make(bloglaout,"Your Image Successfully upload",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Snackbar secoundsnackbar = Snackbar.make(bloglaout,"Done",Snackbar.LENGTH_SHORT);
                                    secoundsnackbar.show();
                                }
                            });
                            snackbar.show();
                        }catch(IOException I)
                        {
                            I.printStackTrace();
                        }
                    }
                    else {
                        Snackbar snackbar = Snackbar.make(bloglaout,"Your Image can not upload Try again",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Snackbar secoundsnackbar = Snackbar.make(bloglaout,"Done",Snackbar.LENGTH_SHORT);
                                secoundsnackbar.show();
                            }
                        });
                        snackbar.show();
                    }
                }
                break;

            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog);
        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();
        UserID = auth.getUid();
        placeTxt = findViewById(R.id.bplaceTxt);
        bloglaout = findViewById(R.id.bloglaout);
        discriptionTxt = findViewById(R.id.bdiscriptionTxt);
        addimage = findViewById(R.id.badd);
        upload = findViewById(R.id.bbutton);
        images = findViewById(R.id.bimage);
        progressBar = findViewById(R.id.bprogressBar);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ID = store.collection("blog").document().getId();
                DocumentReference documentReference = store.collection("blog").document(ID);
                String discription = discriptionTxt.getText().toString();
                String place = placeTxt.getText().toString();

            }
        });
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
                                break;
                            }
                            case 1:
                            {
                                Intent pickphoto = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                startActivityForResult(pickphoto,1);
                                break;
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

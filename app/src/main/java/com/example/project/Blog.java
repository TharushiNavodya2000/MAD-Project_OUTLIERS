package com.example.project;
import static java.security.AccessController.getContext;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;


public class Blog extends AppCompatActivity {


    private EditText placeTxt;
    private EditText discriptionTxt;
    private Button addimage;
    private Button upload;
    private ImageView images;
    Calendar calander = Calendar.getInstance();
    SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("h:mm a");
    String date = simpleDateFormat1.format(calander.getTime());
    String time = simpleDateFormat2.format(calander.getTime());
    Bitmap image_file;
    ConstraintLayout bloglaout;
    FirebaseAuth auth;
    FirebaseFirestore store;
    String UserID;
    StorageReference storageReference;
    UploadTask uploadTask;
    String ID;
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


        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ID = store.collection("blog").document().getId();
                DocumentReference documentReference = store.collection("blog").document(ID);
                String discription = discriptionTxt.getText().toString();
                String place = placeTxt.getText().toString();
                Map<String,Object> blog = new HashMap<>();
                blog.put("userID",UserID);
                blog.put("place",place);
                blog.put("discription",discription);
                blog.put("time",time);
                blog.put("date",date);
                documentReference.set(blog).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        uploadImage(image_file,ID);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
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

    private void uploadImage(Bitmap image_file, String id)
    {
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        image_file.compress(Bitmap.CompressFormat.PNG,100,arrayOutputStream);
        byte[] bytearray = arrayOutputStream.toByteArray();
        storageReference = FirebaseStorage.getInstance().getReference().child("blog_image/"+id);
        uploadTask = storageReference.putBytes(bytearray);
        uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Intent intent = new Intent(Blog.this, ViewBlog.class);
                LoardingDialog loardingDialog = new LoardingDialog(Blog.this);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loardingDialog.startLordingDialog();
                        Toast.makeText(Blog.this,"Successfully uploading your blog",Toast.LENGTH_LONG).show();
                        startActivity(intent);
                    }
                },5000);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

}

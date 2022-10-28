package com.example.project;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity  {

    private TextView forgetpassword;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText email = findViewById(R.id.phone);
        final EditText password = findViewById(R.id.password);
        final Button login = findViewById(R.id.login);
        final Button flogin = findViewById(R.id.flogin);
        final TextView register = findViewById(R.id.registerNow);

        forgetpassword = findViewById(R.id.reset);
        forgetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,FogetPassword.class);
                startActivity(intent);
            }
        });
        mAuth = FirebaseAuth.getInstance();

        //RatingBar ratingBar = (RatingBar) findViewById(R.id.ratelevel);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                final String emailtxt = email.getText().toString();
                final String passwordtxt = password.getText().toString();

                if (emailtxt.isEmpty() && passwordtxt.isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter your username and password", Toast.LENGTH_LONG).show();
                } else if (emailtxt.isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter your User name", Toast.LENGTH_LONG).show();
                } else if (passwordtxt.isEmpty()) {
                    Toast.makeText(Login.this, "Please Enter your Password", Toast.LENGTH_LONG).show();
                }
                else
                {
                    validateUser(emailtxt,passwordtxt);
                }

            }

        });
        flogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,fingerlog.class);
                startActivity(intent);
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Open register activity
                Intent intent = new Intent(Login.this,Register.class);
                startActivity(intent);
            }
        });

    }
    private void validateUser(String emailtxt,String passwordtxt)
    {
        mAuth.signInWithEmailAndPassword(emailtxt,passwordtxt).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Dialog dialog = new Dialog(Login.this);
                dialog.setContentView(R.layout.rate);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView close = dialog.findViewById(R.id.close);
                Button btnok = dialog.findViewById(R.id.ok);
                Button btnlater = dialog.findViewById(R.id.later);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                         Intent intent = new Intent(Login.this, ViewBlog.class);
                        startActivity(intent);
                    }
                });
                btnok.setOnClickListener(new  View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                         Intent intent = new Intent(Login.this,ViewBlog.class);
                        startActivity(intent);

                    }
                });
                btnlater.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                         Intent intent = new Intent(Login.this,ViewBlog.class);
                        startActivity(intent);
                    }
                });
                dialog.show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Dialog dialog = new Dialog(Login.this);
                dialog.setContentView(R.layout.unsuccessfullogin);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                ImageView close = dialog.findViewById(R.id.logclose);
                Button btnok = dialog.findViewById(R.id.logbuttonOK);
                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(Login.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
                btnok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent intent = new Intent(Login.this,MainActivity.class);
                        startActivity(intent);

                    }
                });
                dialog.show();
            }
        });
    }


}
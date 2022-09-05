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
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class FogetPassword extends AppCompatActivity {

    private EditText email;
    private Button send;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foget_password);
        email = findViewById(R.id.resetemail);
        send = findViewById(R.id.send);
        auth = FirebaseAuth.getInstance();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail = email.getText().toString();
                if(getEmail.isEmpty())
                {
                    email.setError("Full name is Required");
                    email.requestFocus();
                }
                else
                {
                    auth.sendPasswordResetEmail(getEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Dialog dialog = new Dialog(FogetPassword.this);
                            dialog.setContentView(R.layout.successlayout);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            ImageView close = dialog.findViewById(R.id.close);
                            Button btnok = dialog.findViewById(R.id.buttonOK);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(FogetPassword.this,Login.class);
                                    dialog.dismiss();
                                    startActivity(intent);
                                }
                            });
                            btnok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(FogetPassword.this,Login.class);
                                    dialog.dismiss();
                                    startActivity(intent);
                                }
                            });
                            dialog.show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Dialog dialog = new Dialog(FogetPassword.this);
                            dialog.setContentView(R.layout.unsuccesslayout);
                            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            ImageView close = dialog.findViewById(R.id.close);
                            Button btnok = dialog.findViewById(R.id.buttonOK);
                            close.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(FogetPassword.this,Login.class);
                                    dialog.dismiss();
                                    startActivity(intent);
                                }
                            });
                            btnok.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(FogetPassword.this,Login.class);
                                    dialog.dismiss();
                                    startActivity(intent);
                                }
                            });
                            dialog.show();
                        }
                    });
                }

            }

        });
    }
}
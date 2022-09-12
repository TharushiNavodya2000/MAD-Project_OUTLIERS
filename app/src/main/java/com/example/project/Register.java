package com.example.project;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    private EditText efullname;
    private EditText eemail;
    private EditText ebirthday;
    private EditText emobile;
    private EditText epassword;
    private EditText eaddress;
    private EditText econfirmpassword;
    private Button sucsses;
    private Dialog dialog;
    FirebaseAuth auth;
    FirebaseFirestore store;
    String userID;

    DatePickerDialog.OnDateSetListener setListner;
    private static final String TAG ="Registeractivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        dialog = new Dialog(Register.this);

        efullname =findViewById(R.id.rfullname);
        eaddress = findViewById(R.id.raddress);
        eemail = findViewById(R.id.remail);
        ebirthday = findViewById(R.id.rbirthday);
        emobile = findViewById(R.id.rphone);
        epassword = findViewById(R.id.rpassword);
        econfirmpassword = findViewById(R.id.rcpassword);
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);
        final TextView login = findViewById(R.id.LoginNow);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Login.class);
                startActivity(intent);
            }
        });
        ebirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Register.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, (DatePickerDialog.OnDateSetListener) setListner,year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });
        setListner = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month+1;
                String date = day+"/"+month+"/"+year;
                ebirthday.setText(date);
            }
        };

        Button register = findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fullnameTxt = efullname.getText().toString();
                String addressTxt = eaddress.getText().toString();
                String birthdayTxt = ebirthday.getText().toString();
                String emailTxt = eemail.getText().toString();
                String phoneTxt = emobile.getText().toString();
                String passwordTxt = epassword.getText().toString();
                String confirm_passwordTxt = econfirmpassword.getText().toString();

                //check user enter details
                if(fullnameTxt.isEmpty())
                {
                    Toast.makeText(Register.this,"Please Enter your full name",Toast.LENGTH_LONG).show();
                    efullname.setError("Full name is Required");
                    efullname.requestFocus();
                }
                else if(emailTxt.isEmpty())
                {
                    Toast.makeText(Register.this,"Please Enter your Email",Toast.LENGTH_LONG).show();
                    eemail.setError("Email is Required");
                    eemail.requestFocus();
                }
                else if(phoneTxt.isEmpty())
                {
                    Toast.makeText(Register.this,"Please Enter your Phone number",Toast.LENGTH_LONG).show();
                    emobile.setError("Phone number is Required");
                    emobile.requestFocus();
                }
                else if(addressTxt.isEmpty())
                {
                    Toast.makeText(Register.this,"Please Enter your Address",Toast.LENGTH_LONG).show();
                    eaddress.setError("Address is Required");
                    eaddress.requestFocus();
                }
                else if(birthdayTxt.isEmpty())
                {
                    Toast.makeText(Register.this,"Please Enter your Birthday",Toast.LENGTH_LONG).show();
                    ebirthday.setError("Birthday is Required");
                    ebirthday.requestFocus();
                }
                else if(passwordTxt.isEmpty())
                {
                    Toast.makeText(Register.this,"Please Enter Password",Toast.LENGTH_LONG).show();
                    epassword.setError("Password is Required");
                    epassword.requestFocus();
                }
                else if(confirm_passwordTxt.isEmpty())
                {
                    Toast.makeText(Register.this,"Please Enter Confirm Password",Toast.LENGTH_LONG).show();
                    econfirmpassword.setError("Confirm Password is Required");
                    econfirmpassword.requestFocus();
                }
                else if(passwordTxt.length() <8)
                {
                    Toast.makeText(Register.this,"Password should be at least 8 digit",Toast.LENGTH_LONG).show();
                }
                else if(!passwordTxt.equals(confirm_passwordTxt))
                {
                    Toast.makeText(Register.this,"Password not match",Toast.LENGTH_LONG).show();
                }
                else
                {
                    registerUser(fullnameTxt,addressTxt,emailTxt,phoneTxt,birthdayTxt,passwordTxt);
                }
            }
        });

    }
    private void registerUser(String fullnameTxt,String addressTxt,String emailTxt,String phoneTxt,String birthdayTxt,String passwordTxt)
    {
        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();

        auth.createUserWithEmailAndPassword(emailTxt,passwordTxt).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userID = auth.getCurrentUser().getUid();
                    Map<String,Object> user = new HashMap<>();
                    user.put("fullname",fullnameTxt);
                    user.put("address",addressTxt);
                    user.put("email",emailTxt);
                    user.put("phone",phoneTxt);
                    user.put("birthday",birthdayTxt);
                    user.put("password",passwordTxt);
                    DocumentReference documentReference = store.collection("users").document(userID);
                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            successmessage();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            unsuccessmessage();
                        }
                    });

                }
                else
                {
                    unsuccessmessage();
                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                unsuccessmessage();
            }
        });


    }
    private void successmessage()
    {
        dialog.setContentView(R.layout.successlayout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView close = dialog.findViewById(R.id.sclose);
        Button btnok = dialog.findViewById(R.id.sbuttonOK);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Blog.class);
                dialog.dismiss();
                startActivity(intent);
            }
        });
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,Blog.class);
                dialog.dismiss();
                startActivity(intent);
            }
        });
        dialog.show();
    }

    private void unsuccessmessage()
    {
        dialog.setContentView(R.layout.unsuccesslayout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView close = dialog.findViewById(R.id.close);
        Button btnok = dialog.findViewById(R.id.buttonOK);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,MainActivity.class);
                dialog.dismiss();
                startActivity(intent);
            }
        });
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this,MainActivity.class);
                dialog.dismiss();
                startActivity(intent);
            }
        });
        dialog.show();
    }
}

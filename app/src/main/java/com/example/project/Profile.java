package com.example.project;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {
    EditText fullname;
    EditText address;
    EditText email;
    EditText birthday;
    EditText phonenumber;
    Button update;
    String userID;
    FirebaseAuth auth;
    FirebaseFirestore store;
    String gname ;
    String gaddress;
    String gemail;
    String gbirthday;
    String gphone;
    FirebaseUser user;
    Dialog dialog;
    String uusername ;
    String uaddress ;
    String uemail ;
    String uphone ;
    String ubirthday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dialog = new Dialog(Profile.this);
        fullname = findViewById(R.id.pfullnameTxt);
        address = findViewById(R.id.paddressTxt);
        email = findViewById(R.id.pemailTxt);
        birthday = findViewById(R.id.pbirthdayTxt);
        phonenumber = findViewById(R.id.pmobileTxt);
        auth = FirebaseAuth.getInstance();
        store = FirebaseFirestore.getInstance();
        userID = auth.getCurrentUser().getUid();
        user = auth.getCurrentUser();
        update = findViewById(R.id.update);

        DocumentReference documentReference = store.collection("users").document(userID);

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                gname = (String) documentSnapshot.getData().get("fullname");
                gaddress = (String) documentSnapshot.getData().get("address");
                gemail = (String) documentSnapshot.getData().get("email");
                gbirthday = (String) documentSnapshot.getData().get("birthday");
                gphone = (String) documentSnapshot.getData().get("phone");
                fullname.setText(gname);
                address.setText(gaddress);
                email.setText(gemail);
                birthday.setText(gbirthday);
                phonenumber.setText(gphone);

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Profile.this);
                builder.setTitle("Re Authenticaion");
                builder.setMessage("Enter your password :");

                final EditText input = new EditText(Profile.this);
                input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String password = input.getText().toString();
                        AuthCredential credential = EmailAuthProvider.getCredential(gemail, password);
                        user.reauthenticate(credential).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                uusername = fullname.getText().toString();
                                uaddress = address.getText().toString();
                                uemail = email.getText().toString();
                                uphone = phonenumber.getText().toString();
                                ubirthday = birthday.getText().toString();
                                user.updateEmail(uemail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        store.collection("users").document(userID).update("fullname",uusername);
                                        store.collection("users").document(userID).update("address",uaddress);
                                        store.collection("users").document(userID).update("birthday",ubirthday);
                                        store.collection("users").document(userID).update("email",uemail);
                                        store.collection("users").document(userID).update("phone",uphone);
                                        displaySucess();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Profile.this,"Failed update",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(Profile.this,UpdateProfile.class);
                                        intent.putExtra("Name",gname);
                                        intent.putExtra("Address",gaddress);
                                        intent.putExtra("Email",gemail);
                                        intent.putExtra("Phone",gphone);
                                        intent.putExtra("Birthday",gbirthday);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Profile.this,"Failed update",Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                builder.show();

            }
    });

}

    private void displaySucess() {
        Toast.makeText(Profile.this,"Successfully update",Toast.LENGTH_LONG).show();

        dialog.setContentView(R.layout.successfulupdateprofile);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        ImageView close = dialog.findViewById(R.id.profclose);
        Button btnok = dialog.findViewById(R.id.profbuttonOK);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,UpdateProfile.class);
                intent.putExtra("Name",uusername);
                intent.putExtra("Address",uaddress);
                intent.putExtra("Email",uemail);
                intent.putExtra("Phone",uphone);
                intent.putExtra("Birthday",ubirthday);
                dialog.dismiss();
                startActivity(intent);
            }
        });
        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profile.this,UpdateProfile.class);
                intent.putExtra("Name",uusername);
                intent.putExtra("Address",uaddress);
                intent.putExtra("Email",uemail);
                intent.putExtra("Phone",uphone);
                intent.putExtra("Birthday",ubirthday);
                dialog.dismiss();
                startActivity(intent);
            }
        });
        dialog.show();

    }
}

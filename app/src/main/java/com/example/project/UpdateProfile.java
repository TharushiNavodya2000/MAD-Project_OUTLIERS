package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class UpdateProfile extends AppCompatActivity {

    TextView welcome;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        welcome = findViewById(R.id.upwelcome);
        fullname = findViewById(R.id.upfullnameTxt);
        address = findViewById(R.id.upaddressTxt);
        email = findViewById(R.id.upemailTxt);
        birthday = findViewById(R.id.upbirthdayTxt);
        phonenumber = findViewById(R.id.upmobileTxt);
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
                welcome.setText("Hello, " + gname);

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdateProfile.this);
                builder.setTitle("Re Authenticaion");
                builder.setMessage("Enter your password :");

                final EditText input = new EditText(UpdateProfile.this);
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
                                String uusername = fullname.getText().toString();
                                String uaddress = address.getText().toString();
                                String uemail = email.getText().toString();
                                String uphone = phonenumber.getText().toString();
                                String ubirthday = birthday.getText().toString();
                                user.updateEmail(uemail).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        store.collection("users").document(userID).update("fullname",uusername);
                                        store.collection("users").document(userID).update("address",uaddress);
                                        store.collection("users").document(userID).update("birthday",ubirthday);
                                        store.collection("users").document(userID).update("email",uemail);
                                        store.collection("users").document(userID).update("phone",uphone);
                                        Intent intent = new Intent(UpdateProfile.this,Blog.class);
                                        startActivity(intent);
                                        Toast.makeText(UpdateProfile.this,"Successfully update",Toast.LENGTH_LONG).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(UpdateProfile.this,"Failed update",Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(UpdateProfile.this,Blog.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateProfile.this,"Failed update",Toast.LENGTH_LONG).show();
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

}
package com.example.project;


import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Profile extends AppCompatActivity {

    TextView welcome;
    TextView fullname;
    TextView address;
    TextView email;
    TextView birthday;
    TextView phonenumber;
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
        setContentView(R.layout.activity_profile);
        welcome = findViewById(R.id.welcome);
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
                welcome.setText("Hello, " + gname);

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(Profile.this,UpdateProfile.class);
               startActivity(intent);
        }
    });

}
}

package com.example.project;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UpdateProfile extends AppCompatActivity {

    TextView fullname;
    TextView address;
    TextView email;
    TextView birthday;
    TextView phonenumber;
    Button ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        fullname = findViewById(R.id.upfullnameTxt);
        address = findViewById(R.id.upaddressTxt);
        email = findViewById(R.id.upemailTxt);
        birthday = findViewById(R.id.upbirthdayTxt);
        phonenumber = findViewById(R.id.upmobileTxt);
        ok = findViewById(R.id.upok);
       Intent intent = getIntent();
       String gname = intent.getStringExtra("Name");
       String gaddress = intent.getStringExtra("Address");
       String gphone = intent.getStringExtra("Phone");
       String gbirthday = intent.getStringExtra("Birthday");
       String gemail = intent.getStringExtra("Email");

        fullname.setText(gname);
        address.setText(gaddress);
        phonenumber.setText(gphone);
        email.setText(gemail);
        birthday.setText(gbirthday);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateProfile.this,ViewBlog.class);
                startActivity(intent);
            }
        });

    }
}

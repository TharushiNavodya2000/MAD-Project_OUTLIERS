package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoardingDialog loardingDialog = new LoardingDialog(MainActivity.this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loardingDialog.startLordingDialog();
                openLogin();
            }
        },5000);

    }
    public void openLogin()
    {

        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }

}
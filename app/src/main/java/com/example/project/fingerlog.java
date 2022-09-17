package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.*;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.concurrent.Executor;

import static androidx.biometric.BiometricManager.Authenticators.*;

import com.google.android.material.snackbar.Snackbar;


public class fingerlog extends AppCompatActivity {

    private static final int REQUEST_CODE = 101010 ;
    private Executor executor;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;
    ImageView fingericon;
    ConstraintLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fingerlog);
        fingericon = findViewById(R.id.fingure);
        layout = findViewById(R.id.fingerprint);
        Intent i = getIntent();
        String name = i.getStringExtra("key");

        //Use android documentation to implement figure print option
        //I change log messages as Toast message
        BiometricManager biometricManager = BiometricManager.from(this);
        switch (biometricManager.canAuthenticate(BIOMETRIC_STRONG | DEVICE_CREDENTIAL)) {
            case BiometricManager.BIOMETRIC_SUCCESS:
                Snackbar snackbar1 = Snackbar.make(layout,"App can authenticate using biometrics.",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar2 = Snackbar.make(layout,"Done",Snackbar.LENGTH_SHORT);
                        snackbar2.show();
                    }
                });
                snackbar1.show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE:
                Snackbar snackbar2 = Snackbar.make(layout,"No biometric features available on this device.",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar3 = Snackbar.make(layout,"Done",Snackbar.LENGTH_SHORT);
                        snackbar3.show();
                    }
                });
                snackbar2.show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE:
                Snackbar snackbar4 = Snackbar.make(layout,"Biometric features are currently unavailable.",Snackbar.LENGTH_LONG).setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Snackbar snackbar5 = Snackbar.make(layout,"Done",Snackbar.LENGTH_SHORT);
                        snackbar5.show();
                    }
                });
                snackbar4.show();
                break;
            case BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED:
                // Prompts the user to create credentials that your app accepts.
                final Intent enrollIntent = new Intent(Settings.ACTION_BIOMETRIC_ENROLL);
                enrollIntent.putExtra(Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BIOMETRIC_STRONG | DEVICE_CREDENTIAL);
                startActivityForResult(enrollIntent, REQUEST_CODE);
                break;
        }
        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(fingerlog.this,
                executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, @NonNull CharSequence errString) {
                super.onAuthenticationError(errorCode, errString);
                Toast.makeText(getApplicationContext(), "Authentication error: " + errString, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAuthenticationSucceeded(
                    @NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(getApplicationContext(), "Authentication succeeded!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(fingerlog.this,Profile.class));
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(getApplicationContext(), "Authentication failed", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login for Travel App")
                .setSubtitle("Log in using your biometric credential")
                .setNegativeButtonText("Use account password In here")
                .build();


       fingericon.setOnClickListener(view -> {
            biometricPrompt.authenticate(promptInfo);
        });
    }
    }

package com.example.project;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class LoardingDialog
{
    private Activity activity;
    private AlertDialog dialog;

    LoardingDialog(Activity activity)
    {
        this.activity = activity;
    }
    void startLordingDialog()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_loading_dialog,null));
        builder.setCancelable(true);
        dialog = builder.create();
        dialog.show();

    }
    void dismissDialog()
    {

        dialog.dismiss();
    }
}

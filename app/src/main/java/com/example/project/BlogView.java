package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat;

import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class BlogView extends AppCompatActivity {

    ImageView women;
    ImageView heart;
    AnimatedVectorDrawableCompat avd;
    AnimatedVectorDrawable avd2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blog_view);
        this.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        |View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        |View.SYSTEM_UI_FLAG_FULLSCREEN
                        |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        women =findViewById(R.id.women);
        heart = findViewById(R.id.heart);
        final Drawable drawable = heart.getDrawable();
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                heart.setAlpha(0.70f);
                if(drawable instanceof AnimatedVectorDrawableCompat)
                {
                    avd = (AnimatedVectorDrawableCompat) drawable;
                    avd.start();
                }
                else if(drawable instanceof AnimatedVectorDrawable)
                {
                    avd2 = (AnimatedVectorDrawable) drawable;
                    avd2.start();
                }
            }
        });

    }
}
package com.filbertfilbert.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import gr.net.maroulis.library.EasySplashScreen;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        EasySplashScreen config = new EasySplashScreen(SplashActivity.this)
                .withFullScreen()
                .withTargetActivity(LoginActivity.class)
                .withSplashTimeOut(3000)
                .withBackgroundColor(Color.parseColor("#FFFFFF"))
                .withAfterLogoText("WOWHANA")
                .withLogo(R.drawable.ferriswheel)
                .withFooterText("Kelompok D");
        config.getAfterLogoTextView().setTextColor(Color.BLACK);
        config.getFooterTextView().setTextColor(Color.BLACK);
        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
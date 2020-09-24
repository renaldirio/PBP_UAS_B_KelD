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
                .withSplashTimeOut(1000)
                .withBackgroundColor(Color.parseColor("#000000"))
                .withAfterLogoText("NAMA LOGO")
                .withLogo(R.drawable.ic_android_black_24dp)
                .withFooterText("WAHANA");
        config.getAfterLogoTextView().setTextColor(Color.WHITE);
        config.getFooterTextView().setTextColor(Color.WHITE);
        View easySplashScreen = config.create();
        setContentView(easySplashScreen);
    }
}
package com.example.edulockapp.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edulockapp.MainActivity;
import com.example.edulockapp.R;
import com.example.edulockapp.ui.onboardingscreen.IntroActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent introStart = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(introStart);
                finish();

                finish();
            }
        }, 2000);
    }
}

package com.example.edulockapp.ui.splashscreen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.example.edulockapp.R;
import com.example.edulockapp.ui.onboardingscreen.IntroActivity;
import com.example.edulockapp.utils.Utils;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        String EXTRA_LAST_APP = "EXTRA_LAST_APP";
        new Utils(this).setLastApp(EXTRA_LAST_APP);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent introStart = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(introStart);
                finish();
            }
        }, 2000);
    }
}

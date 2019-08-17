package id.ac.umn.made_basiliusbias_submission3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import id.ac.umn.made_basiliusbias_submission3.LangApp;
import id.ac.umn.made_basiliusbias_submission3.R;

public class SplashActivity extends LangApp {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Load Custom Animation
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade_in);

        // Find UI Elements
        ImageView splashPicture = findViewById(R.id.splash_picture);
        TextView splashText = findViewById(R.id.splash_text);

        // Run Animate
        splashPicture.startAnimation(animation);
        splashText.startAnimation(animation);

        // Multi-Thread For Delay Animating Splash Screen
        new Thread(() -> {
            try {

                // Delay 2.5 Second Showing Logo
                Thread.sleep(1234);

                // Go To Main Apps
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            catch(InterruptedException e) {

                // Print Error
                e.printStackTrace();
            }
        }).start();
    }
}
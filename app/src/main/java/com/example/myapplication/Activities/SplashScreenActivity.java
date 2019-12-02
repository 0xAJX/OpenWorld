package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.example.myapplication.Helpers.ContentLoader;
import com.example.myapplication.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Used to make splash screen full screen */
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        /** Used to make splash screen full screen */

        setContentView(R.layout.activity_splash_screen);

        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //Intent is used to switch from one activity to another.
                Intent i=new Intent(SplashScreenActivity.this, HomePageActivity.class);

                //invoke the SecondActivity.
                startActivity(i);

                //the current activity will get finished.
                finish();
            }
        }, SPLASH_SCREEN_TIME_OUT);*/

        ContentLoader.loadActivity(this, new HomePageActivity());

    }
}

package com.havrtz.unfold.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.havrtz.unfold.helpers.ContentLoader
import com.havrtz.unfold.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Used to make splash screen full screen  */
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        /** Used to make splash screen full screen  */
        setContentView(R.layout.activity_splash_screen)

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
        ContentLoader.loadActivity(this, HomePageActivity())
    }

    companion object {
        private const val SPLASH_SCREEN_TIME_OUT = 2000
    }
}
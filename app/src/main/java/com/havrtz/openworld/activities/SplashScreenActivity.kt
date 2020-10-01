package com.havrtz.openworld.activities

import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.facebook.stetho.Stetho
import com.havrtz.openworld.BuildConfig
import com.havrtz.openworld.R
import com.havrtz.openworld.helpers.ContentLoader
import timber.log.Timber
import timber.log.Timber.DebugTree

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /** Used to make splash screen full screen  */
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)
        /** Used to make splash screen full screen  */
        setContentView(R.layout.activity_splash_screen)

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
            Stetho.initializeWithDefaults(this)
        }

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
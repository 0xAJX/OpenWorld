package com.havrtz.unfold.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.havrtz.unfold.R

class OptionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when {
            intent.getStringExtra("view") == "getting_started" -> {
                //setContentView(R.layout.activity_getting_started)
                setContentView(R.layout.coming_soon)
            }
            intent.getStringExtra("view") == "team" -> {
                setContentView(R.layout.activity_team)
            }
            intent.getStringExtra("view") == "contact_us" -> {
                setContentView(R.layout.activity_contact_us)
            }
            intent.getStringExtra("view") == "notification" -> {
                setContentView(R.layout.coming_soon)
            }
            else -> {
                setContentView(R.layout.error)
            }
        }
    }
}
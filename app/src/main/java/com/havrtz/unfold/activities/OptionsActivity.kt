package com.havrtz.unfold.activities

import android.content.ClipDescription
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
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

    fun openLinkedIn(view: View) {

        var mLinkedInurlString: String = "abhishek-jadhav-36197898"

        val url = "https://www.linkedin.com/in/$mLinkedInurlString"
        val linkedInAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        linkedInAppIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
        startActivity(linkedInAppIntent)
    }

    /** TODO: Only make it available on mail apps */
    fun openMail(view: View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = ClipDescription.MIMETYPE_TEXT_PLAIN
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("abhishek.jadhav64@live.com"))
        intent.putExtra(android.content.Intent.EXTRA_SUBJECT,"")
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "")
        startActivity(Intent.createChooser(intent,"Send Email"))
    }

    fun openGithub(view: View) {
        val uri = Uri.parse("https://github.com/i-am-0xbit") // missing 'http://' will cause crashed

        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)
    }

}
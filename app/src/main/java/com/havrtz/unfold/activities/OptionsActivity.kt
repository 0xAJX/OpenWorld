package com.havrtz.unfold.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.havrtz.unfold.R
import com.havrtz.unfold.helpers.AppPackage
import com.havrtz.unfold.util.Constants


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

        val appPackageName: String = "com.linkedin.android"
        val mLinkedInurlString: String = "abhishek-jadhav-36197898"

        if (AppPackage.isPackageInstalled(appPackageName, this.packageManager)) {
            val url = "https://www.linkedin.com/in/$mLinkedInurlString"
            val linkedInAppIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            linkedInAppIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET)
            startActivity(linkedInAppIntent)
        } else {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(Constants.googlePlayUrl + appPackageName)))
        }


    }

    /** TODO: Only make it available on mail apps */
    fun openMail(view: View) {

        val i = Intent(Intent.ACTION_SEND)
        i.type = "message/rfc822"
        i.putExtra(Intent.EXTRA_EMAIL, arrayOf("abhishek.jadhav64@live.com"))
        i.putExtra(Intent.EXTRA_SUBJECT, "")
        i.putExtra(Intent.EXTRA_TEXT, "")
        try {
            startActivity(Intent.createChooser(i, "Send mail..."))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(this, "There are no email clients installed.", Toast.LENGTH_SHORT).show()
        }
    }

    fun openGithub(view: View) {
        val appPackageName: String = "com.github.android"
        val uri = Uri.parse("https://github.com/i-am-0xbit") // missing 'http://' will cause crashed

        if (AppPackage.isPackageInstalled(appPackageName, this.packageManager)) {
            val intent = Intent(Intent.ACTION_VIEW, uri)
            startActivity(intent)
        } else {
            val url = "https://github.com/i-am-0xbit"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

    }

}
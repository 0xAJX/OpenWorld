package com.example.myapplication.Helpers

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.R

object ContentLoader {
    /**
     * Used to loads fragments
     */
    fun loadActivity(activity1: Activity?, activity2: Activity?): Boolean {
        ActivityLoader().execute(activity1, activity2)
        return true
    }

    fun loadFragment(fragment: Fragment?, activity: Activity): Boolean {
        if (fragment != null) {
            val fragmentActivity = activity as FragmentActivity
            fragmentActivity.supportFragmentManager.beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.fragment_container, fragment).commit()
            return true
        }
        return false
    }

    /** TODO Make this class jump between activities  */
    private class ActivityLoader : AsyncTask<Activity?, Void?, Activity>() {
        var i: Intent? = null
        protected override fun doInBackground(vararg activities: Activity): Activity {
            try {
                Thread.sleep(500)
            } catch (e: InterruptedException) {
                Thread.interrupted()
            }
            i = Intent(activities[0], activities[1].javaClass)
            i!!.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            return activities[0]
        }

        override fun onPostExecute(activity: Activity) {
            activity.startActivity(i)
        }

        override fun onPreExecute() {}
        protected override fun onProgressUpdate(vararg values: Void) {}
    }
    /** TODO Make this class jump between activities  */
}
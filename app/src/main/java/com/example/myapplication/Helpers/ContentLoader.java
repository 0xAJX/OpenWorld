package com.example.myapplication.Helpers;

import android.app.Activity;

import android.content.Intent;
import android.os.AsyncTask;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

public class ContentLoader {

    /**
     * Used to loads fragments
     * */


    public static boolean loadActivity(Activity activity1, Activity activity2)
    {
        new ActivityLoader().execute(activity1, activity2);
        return true;
    }

    public static boolean loadFragment(Fragment fragment, Activity activity)
    {
        if(fragment != null)
        {
            FragmentActivity fragmentActivity = (FragmentActivity) activity;
            fragmentActivity.getSupportFragmentManager().beginTransaction().setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE).replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }

    /** TODO Make this class jump between activities **/
    private static class ActivityLoader extends AsyncTask<Activity, Void, Activity> {

        Intent i;
        @Override
        protected Activity doInBackground(Activity... activities) {

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.interrupted();
            }

            i = new Intent(activities[0], activities[1].getClass());
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            return activities[0];
        }

        @Override
        protected void onPostExecute(Activity activity) {
            activity.startActivity(i);
        }

        @Override
        protected void onPreExecute() {}

        @Override
        protected void onProgressUpdate(Void... values) {}
    }
    /** TODO Make this class jump between activities **/

}


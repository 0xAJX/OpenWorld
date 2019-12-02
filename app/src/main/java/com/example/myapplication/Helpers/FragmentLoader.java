package com.example.myapplication.Helpers;

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.R;

public class FragmentLoader{
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
}


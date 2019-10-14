package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapplication.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

public class BottomNavigationDrawerFragment extends BottomSheetDialogFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_bottomsheet, null, false);

        NavigationView navigationView = view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return view;
    }

    private NavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new NavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()) {

                case R.id.nav1:
                    //loadFragment(new SelectTemplateFragment());
                    break;
                //mTextMessage.setText(R.string.title_notifications);
                //return true;
            }
            return true;
        }
    };
}

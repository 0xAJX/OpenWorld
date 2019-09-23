package com.example.myapplication.Activities;

import android.os.Bundle;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Fragments.AllStoriesFragment;
import com.example.myapplication.R;
import com.example.myapplication.Fragments.SelectTemplateFragment;
import com.example.myapplication.UTDatabaseHandler;

public class MainActivity extends AppCompatActivity {
    //private TextView mTextMessage;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_select_template:
                    fragment = new SelectTemplateFragment();
                    //fragment = new SelectTemplateFragment();
                    break;
                    //mTextMessage.setText(R.string.title_home);
                    //return true;
                case R.id.navigation_all_stories:
                    fragment = new AllStoriesFragment();
                    break;
                    //mTextMessage.setText(R.string.title_dashboard);
                    //return true;
                case R.id.navigation_notifications:
                    break;
                    //mTextMessage.setText(R.string.title_notifications);
                    //return true;
            }
            return loadFragment(fragment);
        }
    };

    private  boolean loadFragment(Fragment fragment)
    {
        if(fragment != null)
        {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        //mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        UTDatabaseHandler handler = new UTDatabaseHandler(this);




        handler.close();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.bringToFront();

        navView.setSelectedItemId(R.id.navigation_all_stories);
    }

}

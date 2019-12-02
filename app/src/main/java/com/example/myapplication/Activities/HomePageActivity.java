package com.example.myapplication.Activities;

import android.os.Bundle;

import com.example.myapplication.Fragments.NavigationDrawerBottomSheetFragment;
import com.example.myapplication.Helpers.FragmentLoader;
import com.google.android.material.bottomappbar.BottomAppBar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Fragments.AllStoriesFragment;
import com.example.myapplication.R;
import com.example.myapplication.Fragments.SelectTemplateFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomePageActivity extends AppCompatActivity {

    FloatingActionButton create;

    boolean isAllStory = true;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bottom_nav_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {

            case android.R.id.home:
                NavigationDrawerBottomSheetFragment navigationDrawerBottomSheetFragment = new NavigationDrawerBottomSheetFragment();
                navigationDrawerBottomSheetFragment.show(getSupportFragmentManager(), navigationDrawerBottomSheetFragment.getTag());
                break;

            case R.id.navigation_notifications:
                break;

        }
        return true;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        //BottomNavigationView navView = findViewById(R.id.nav_view);
        //mTextMessage = findViewById(R.id.message);
        //navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        BottomAppBar bottomAppBar = findViewById(R.id.bottombar);
        setSupportActionBar(bottomAppBar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.bringToFront();

        FragmentLoader.loadFragment(new AllStoriesFragment(), this);

        create = findViewById(R.id.fab);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isAllStory)
                {
                    FragmentLoader.loadFragment(new SelectTemplateFragment(), HomePageActivity.this);
                    create.setImageResource(R.drawable.ic_dashboard);
                    isAllStory = false;
                }
                else
                {
                    FragmentLoader.loadFragment(new AllStoriesFragment(), HomePageActivity.this);
                    create.setImageResource(R.drawable.ic_pencil);
                    isAllStory = true;
                }
            }
        });

    }

}

package com.example.myapplication.Activities;

import android.os.Bundle;

import com.example.myapplication.Fragments.NavigationDrawerBottomSheetFragment;
import com.google.android.material.bottomappbar.BottomAppBar;

import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import com.example.myapplication.Fragments.AllStoriesFragment;
import com.example.myapplication.R;
import com.example.myapplication.Fragments.SelectTemplateFragment;
import com.example.myapplication.Handlers.UTDatabaseHandler;
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
        setContentView(R.layout.activity_home_page);
        //BottomNavigationView navView = findViewById(R.id.nav_view);
        //mTextMessage = findViewById(R.id.message);
        //navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        UTDatabaseHandler handler = new UTDatabaseHandler(this);

        handler.close();

        BottomAppBar bottomAppBar = findViewById(R.id.bottombar);
        setSupportActionBar(bottomAppBar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.bringToFront();

        loadFragment(new AllStoriesFragment());

        create = findViewById(R.id.fab);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(isAllStory)
                {
                    loadFragment(new SelectTemplateFragment());
                    create.setImageResource(R.drawable.ic_dashboard_black_24dp);
                    isAllStory = false;
                }
                else
                {
                    loadFragment(new AllStoriesFragment());
                    create.setImageResource(R.drawable.ic_create_black_24dp);
                    isAllStory = true;
                }
            }
        });


    }

}

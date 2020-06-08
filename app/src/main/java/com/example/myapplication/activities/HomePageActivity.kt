package com.example.myapplication.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.myapplication.fragments.AllStoriesFragment
import com.example.myapplication.fragments.NavigationDrawerBottomSheetFragment
import com.example.myapplication.fragments.SelectTemplateFragment
import com.example.myapplication.helpers.ContentLoader
import com.example.myapplication.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class HomePageActivity : AppCompatActivity() {
    var create: FloatingActionButton? = null
    var isAllStory = true
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val navigationDrawerBottomSheetFragment = NavigationDrawerBottomSheetFragment()
                navigationDrawerBottomSheetFragment.show(supportFragmentManager, navigationDrawerBottomSheetFragment.getTag())
            }
            R.id.navigation_notifications -> {
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)
        //BottomNavigationView navView = findViewById(R.id.nav_view);
        //mTextMessage = findViewById(R.id.message);
        //navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottombar)
        setSupportActionBar(bottomAppBar)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.bringToFront()
        ContentLoader.loadFragment(AllStoriesFragment(), this)
        create = findViewById(R.id.fab)
        create.setOnClickListener(View.OnClickListener {
            isAllStory = if (isAllStory) {
                ContentLoader.loadFragment(SelectTemplateFragment(), this@HomePageActivity)
                create.setImageResource(R.drawable.ic_dashboard)
                false
            } else {
                ContentLoader.loadFragment(AllStoriesFragment(), this@HomePageActivity)
                create.setImageResource(R.drawable.ic_pencil)
                true
            }
        })
    }
}
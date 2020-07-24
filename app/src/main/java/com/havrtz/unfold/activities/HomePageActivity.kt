package com.havrtz.unfold.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.havrtz.unfold.fragments.AllStoriesFragment
import com.havrtz.unfold.fragments.NavigationDrawerBottomSheetFragment
import com.havrtz.unfold.fragments.SelectTemplateFragment
import com.havrtz.unfold.helpers.ContentLoader
import com.havrtz.unfold.R
import com.google.android.material.bottomappbar.BottomAppBar
import kotlinx.android.synthetic.main.activity_home_page.*

class HomePageActivity : AppCompatActivity() {

    var isAllStory = true
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.bottom_nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val navigationDrawerBottomSheetFragment = NavigationDrawerBottomSheetFragment()
                navigationDrawerBottomSheetFragment.show(supportFragmentManager, navigationDrawerBottomSheetFragment.tag)
            }
            R.id.navigation_notifications -> {
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_page)

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottombar)
        setSupportActionBar(bottomAppBar)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.bringToFront()
        ContentLoader.loadFragment(AllStoriesFragment(), this)

        fab.setOnClickListener(View.OnClickListener {
            isAllStory = if (isAllStory) {
                ContentLoader.loadFragment(SelectTemplateFragment(), this@HomePageActivity)
                fab?.setImageResource(R.drawable.ic_dashboard)
                false
            } else {
                ContentLoader.loadFragment(AllStoriesFragment(), this@HomePageActivity)
                fab?.setImageResource(R.drawable.ic_pencil)
                true
            }
        })
    }
}
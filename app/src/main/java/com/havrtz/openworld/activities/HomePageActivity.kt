package com.havrtz.openworld.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.havrtz.openworld.fragments.AllStoriesFragment
import com.havrtz.openworld.fragments.NavigationDrawerBottomSheetFragment
import com.havrtz.openworld.fragments.SelectTemplateFragment
import com.havrtz.openworld.helpers.ContentLoader
import com.havrtz.openworld.R
import com.google.android.material.bottomappbar.BottomAppBar
import com.havrtz.openworld.databinding.ActivityHomePageBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomePageActivity : AppCompatActivity() {

    var isAllStory = true
    private lateinit var binding: ActivityHomePageBinding

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
                var intent = Intent(applicationContext, OptionsActivity::class.java)
                intent.putExtra("view", "notification")
                startActivity(intent)
            }
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottombar)
        setSupportActionBar(bottomAppBar)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toolbar.bringToFront()
        ContentLoader.loadFragment(AllStoriesFragment(), this)

        binding.fab.setOnClickListener(View.OnClickListener {
            isAllStory = if (isAllStory) {
                ContentLoader.loadFragment(SelectTemplateFragment(), this@HomePageActivity)
                binding.fab.setImageResource(R.drawable.ic_dashboard)
                false
            } else {
                ContentLoader.loadFragment(AllStoriesFragment(), this@HomePageActivity)
                binding.fab.setImageResource(R.drawable.ic_pencil)
                true
            }
        })
    }
}
package com.havrtz.openworld.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
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

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        binding.fab.setOnClickListener(View.OnClickListener {
            isAllStory = if (isAllStory) {
                navController.navigate(R.id.action_allStoriesFragment_to_selectTemplateFragment2)
                binding.fab.setImageResource(R.drawable.ic_dashboard)
                false
            } else {
                navController.navigate(R.id.action_selectTemplateFragment_to_allStoriesFragment)
                binding.fab.setImageResource(R.drawable.ic_pencil)
                true
            }
        })
    }
}
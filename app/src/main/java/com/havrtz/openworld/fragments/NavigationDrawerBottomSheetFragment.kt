package com.havrtz.openworld.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.havrtz.openworld.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import com.havrtz.openworld.activities.OptionsActivity

class NavigationDrawerBottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.navigation_drawer_bottom_sheet_fragment, null, false)
        val navigationView: NavigationView = view.findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        return view
    }

    private val mOnNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener { item ->
        var intent = Intent(context, OptionsActivity::class.java)

        when (item.itemId) {
            R.id.getting_started -> {
                intent.putExtra("view", "getting_started")
            }
//            R.id.team -> {
//                intent.putExtra("view", "team")
//            }
            R.id.contact_us -> {
                intent.putExtra("view", "contact_us")
            }
        }

        startActivity(intent)

        true
    }
}
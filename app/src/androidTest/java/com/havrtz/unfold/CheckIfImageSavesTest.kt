package com.havrtz.unfold

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.havrtz.unfold.activities.HomePageActivity
import kotlinx.android.synthetic.main.activity_home_page.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.Robolectric
import androidx.test.espresso.action.ViewActions.click
import android.support.test.espresso.contrib.RecyclerViewActions

@RunWith(RobolectricTestRunner::class)
class CheckIfImageSavesTest {
    @Test
    fun clickingButton_shouldChangeMessage() {
        val homePageActivity: HomePageActivity = Robolectric.setupActivity(HomePageActivity::class.java)
        homePageActivity.fab.performClick()
        onView(withId(R.id.select_template_recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
    }
}
package com.havrtz.unfold.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.havrtz.unfold.R
import com.havrtz.unfold.adapters.AllStoriesAdapter
import com.havrtz.unfold.helpers.EqualSpacingItemDecoration
import com.havrtz.unfold.models.Story
import com.havrtz.unfold.viewmodels.StoryViewModel
import kotlinx.android.synthetic.main.all_stories_fragment.view.*


class AllStoriesFragment : Fragment() {
    lateinit var storyViewModel: StoryViewModel

    private val sColumnWidth: Double = 432.0 // assume cell width of 120dp

    private fun calculateSize(recyclerView: RecyclerView) {
        val spanCount = Math.floor(recyclerView.getWidth() / sColumnWidth)
        (recyclerView.layoutManager as GridLayoutManager).spanCount = spanCount.toInt()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lateinit var recyclerView: RecyclerView
        var view = inflater.inflate(R.layout.all_stories_fragment, null)
        val textView = view.nostorytext
        recyclerView = view.findViewById(R.id.allstoriesrecyclerview)

        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object : OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                // get width and height of the view

                recyclerView.layoutManager = GridLayoutManager(context, 2)

                val spanCount = Math.floor(recyclerView.getWidth() / sColumnWidth)
                (recyclerView.layoutManager as GridLayoutManager).spanCount = spanCount.toInt()
            }
        })

        recyclerView.addItemDecoration(EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.HORIZONTAL))
        recyclerView.setHasFixedSize(true)
        val allStoriesAdapter = context?.let { AllStoriesAdapter(it) }
        recyclerView.setAdapter(allStoriesAdapter)

        /** Get story view model and show data  */
        storyViewModel = ViewModelProvider(this).get(StoryViewModel::class.java)
        storyViewModel.allStories!!.observe(viewLifecycleOwner, Observer<List<Story?>?> { stories ->
            if (allStoriesAdapter != null) {
                allStoriesAdapter.setStories(stories as MutableList<Story>)
                if (stories.size == 0) {
                    textView.visibility = View.VISIBLE
                } else {
                    textView.visibility = View.INVISIBLE
                }
            }
        })
        /** Get story view model and show data  */

        return view
    }
}
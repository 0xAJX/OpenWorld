package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.AllStoriesAdapter
import com.example.myapplication.helpers.EqualSpacingItemDecoration
import com.example.myapplication.models.Story
import com.example.myapplication.R
import com.example.myapplication.viewmodels.StoryViewModel

class AllStoriesFragment : Fragment() {
    private var storyViewModel: StoryViewModel? = null
    private var recyclerView: RecyclerView? = null
    private var view: View? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        view = inflater.inflate(R.layout.all_stories_fragment, null)
        val textView = view.findViewById<TextView>(R.id.nostorytext)
        recyclerView = view.findViewById(R.id.allstoriesrecyclerview)
        recyclerView.setLayoutManager(GridLayoutManager(context, 2))
        recyclerView.setHasFixedSize(true)
        recyclerView.addItemDecoration(EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.HORIZONTAL))
        try {
            val allStoriesAdapter = AllStoriesAdapter(context!!)
            recyclerView.setAdapter(allStoriesAdapter)
            /** Get story view model and show data  */
            storyViewModel = ViewModelProviders.of(this).get(StoryViewModel::class.java)
            storyViewModel!!.allStories.observe(this, Observer<List<Story?>> { stories ->
                allStoriesAdapter.setStories(stories)
                if (stories.size == 0) {
                    textView.visibility = View.VISIBLE
                } else {
                    textView.visibility = View.INVISIBLE
                }
            })
            /** Get story view model and show data  */
        } catch (e: Exception) {
        }
        return view
    }
}
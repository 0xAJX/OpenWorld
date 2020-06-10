package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapplication.adapters.AllStoriesAdapter
import com.example.myapplication.helpers.EqualSpacingItemDecoration
import com.example.myapplication.models.Story
import com.example.myapplication.R
import com.example.myapplication.viewmodels.StoryViewModel
import kotlinx.android.synthetic.main.all_stories_fragment.*

class AllStoriesFragment : Fragment() {
    private var storyViewModel: StoryViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.all_stories_fragment, null)
        val textView = nostorytext
        if (allstoriesrecyclerview != null) {
            allstoriesrecyclerview.layoutManager = GridLayoutManager(context, 2)
            allstoriesrecyclerview.setHasFixedSize(true)
            allstoriesrecyclerview.addItemDecoration(EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.HORIZONTAL))
            try {
                val allStoriesAdapter = AllStoriesAdapter(requireContext())
                allstoriesrecyclerview.setAdapter(allStoriesAdapter)
                /** Get story view model and show data  */
                storyViewModel = ViewModelProvider(this).get(StoryViewModel::class.java)
                storyViewModel!!.allStories?.observe(viewLifecycleOwner, Observer<List<Story?>?> { stories ->
                    allStoriesAdapter.setStories((stories as List<Story>).toMutableList())
                    if (stories.size == 0) {
                        textView.visibility = View.VISIBLE
                    } else {
                        textView.visibility = View.INVISIBLE
                    }
                })
                /** Get story view model and show data  */
            } catch (e: Exception) {
            }
        }
        return view
    }
}
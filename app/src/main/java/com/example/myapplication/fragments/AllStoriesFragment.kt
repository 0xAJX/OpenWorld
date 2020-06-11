package com.example.myapplication.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.AllStoriesAdapter
import com.example.myapplication.helpers.EqualSpacingItemDecoration
import com.example.myapplication.models.Story
import com.example.myapplication.R

import com.example.myapplication.viewmodels.StoryViewModel
import kotlinx.android.synthetic.main.all_stories_fragment.view.*

class AllStoriesFragment : Fragment() {
    lateinit var storyViewModel: StoryViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.all_stories_fragment, null)
        val textView = view.nostorytext
        recyclerView = view.findViewById(R.id.allstoriesrecyclerview)

        recyclerView.layoutManager = GridLayoutManager(context, 2)
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
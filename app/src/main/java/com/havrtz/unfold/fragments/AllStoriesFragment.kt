package com.havrtz.unfold.fragments

import android.os.Bundle
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
import com.havrtz.unfold.helpers.ColumnSizeCalculator
import com.havrtz.unfold.helpers.EqualSpacingItemDecoration
import com.havrtz.unfold.models.Story
import com.havrtz.unfold.viewmodels.StoryViewModel
import kotlinx.android.synthetic.main.all_stories_fragment.view.*


class AllStoriesFragment : Fragment() {
    private lateinit var storyViewModel: StoryViewModel

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
                (recyclerView.layoutManager as GridLayoutManager).spanCount = ColumnSizeCalculator.calculateSize(recyclerView.width)
            }
        })

        recyclerView.addItemDecoration(EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.HORIZONTAL))
        recyclerView.setHasFixedSize(true)
        val allStoriesAdapter = context?.let { AllStoriesAdapter(it) }
        recyclerView.adapter = allStoriesAdapter

        /** Get story view model and show data  */
        storyViewModel = ViewModelProvider(this).get(StoryViewModel::class.java)

        storyViewModel.allStories.observe(viewLifecycleOwner, { stories ->
            if (allStoriesAdapter != null) {
                allStoriesAdapter.submitList(stories)

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
package com.havrtz.openworld.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver.OnGlobalLayoutListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.havrtz.openworld.R
import com.havrtz.openworld.adapters.AllStoriesAdapter
import com.havrtz.openworld.helpers.ColumnSizeCalculator
import com.havrtz.openworld.helpers.EqualSpacingItemDecoration
import com.havrtz.openworld.viewmodels.StoryViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.all_stories_fragment.view.*

@AndroidEntryPoint
class AllStoriesFragment : Fragment() {
    private val storyViewModel: StoryViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        lateinit var recyclerView: RecyclerView
        val view = inflater.inflate(R.layout.all_stories_fragment, null)
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
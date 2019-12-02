package com.example.myapplication.Fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Helpers.EqualSpacingItemDecoration;
import com.example.myapplication.Models.Story;
import com.example.myapplication.R;
import com.example.myapplication.Adapters.AllStoriesAdapter;
import com.example.myapplication.ViewModels.StoryViewModel;
import java.util.List;

public class AllStoriesFragment extends Fragment {

    private StoryViewModel storyViewModel;
    private RecyclerView recyclerView;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.all_stories_fragment, null);

        final TextView textView = view.findViewById(R.id.nostorytext);

        recyclerView = view.findViewById(R.id.allstoriesrecyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.HORIZONTAL));

        try {
            final AllStoriesAdapter allStoriesAdapter = new AllStoriesAdapter(getContext());
            recyclerView.setAdapter(allStoriesAdapter);

            /** Get story view model and show data */
            storyViewModel = ViewModelProviders.of(this).get(StoryViewModel.class);
            storyViewModel.getAllStories().observe(this, new Observer<List<Story>>() {
                @Override
                public void onChanged(List<Story> stories) {
                    allStoriesAdapter.setStories(stories);
                    if(stories.size() == 0) {
                        textView.setVisibility(View.VISIBLE);
                    } else {
                        textView.setVisibility(View.INVISIBLE);
                    }
                }
            });
            /** Get story view model and show data */

        } catch (Exception e) {

        }

        return view;
    }


}

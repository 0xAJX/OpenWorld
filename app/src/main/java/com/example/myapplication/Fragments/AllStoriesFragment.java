package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Models.UserTemplateItem;
import com.example.myapplication.R;
import com.example.myapplication.Adapters.AllStoriesAdapter;
import com.example.myapplication.Handlers.UTDatabaseHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllStoriesFragment extends Fragment {

    private AllStoriesAdapter allStoriesAdapter;
    private RecyclerView recyclerView;
    private List<UserTemplateItem> user_template_items;
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.all_stories_fragment, null);

        final TextView textView = view.findViewById(R.id.nostorytext);
        //Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        //toolbar.setTitle("My Stories");

        recyclerView = view.findViewById(R.id.allstoriesrecyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //bottomNavigationMenu = getActivity().findViewById(R.id.nav_view);

        user_template_items = new ArrayList<>();

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "/app/image/");

        File directory = new File(String.valueOf(wallpaperDirectory)); //path is the string specifying your directory path.
        File[] files = directory.listFiles();

        UTDatabaseHandler handler = new UTDatabaseHandler(getContext());
        user_template_items = handler.loadUserTemplates();

        if(user_template_items.size() != 0)
        {
            textView.setVisibility(View.INVISIBLE);
        }

        Collections.reverse(user_template_items);
        allStoriesAdapter = new AllStoriesAdapter(getContext(), user_template_items);
        recyclerView.setAdapter(allStoriesAdapter);
        allStoriesAdapter.notifyDataSetChanged();

        return view;
    }
}

package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.os.Environment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.Models.User_Template_Item;
import com.example.myapplication.R;
import com.example.myapplication.Adapters.AllStoriesAdapter;
import com.example.myapplication.UTDatabaseHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllStoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<User_Template_Item> user_template_items;
    private AllStoriesAdapter allStoriesAdapter;
    BottomNavigationView bottomNavigationMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.allstoriesfragment, null);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("My Stories");

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
            TextView textView = view.findViewById(R.id.nostorytext);
            textView.setVisibility(View.INVISIBLE);
        }

        Collections.reverse(user_template_items);

        allStoriesAdapter = new AllStoriesAdapter(getContext(), user_template_items);
        recyclerView.setAdapter(allStoriesAdapter);
        allStoriesAdapter.notifyDataSetChanged();

        return view;
    }

}

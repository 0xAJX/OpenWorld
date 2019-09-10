package com.example.myapplication.Fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.Adapters.AllStoriesAdapter;
import com.example.myapplication.Models.My_Story_List_Item;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AllStoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<My_Story_List_Item> myStoryListItems;
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

        bottomNavigationMenu = getActivity().findViewById(R.id.nav_view);

        myStoryListItems = new ArrayList<>();

        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + "/app/image/");

        File directory = new File(String.valueOf(wallpaperDirectory)); //path is the string specifying your directory path.
        File[] files = directory.listFiles();

        My_Story_List_Item listItems = new My_Story_List_Item("Hello World",BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems);

        My_Story_List_Item listItems1 = new My_Story_List_Item("Hello World",BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems1);

        My_Story_List_Item listItems2 = new My_Story_List_Item("Hello World" ,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems2);

        My_Story_List_Item listItems3 = new My_Story_List_Item("Hello World" ,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems3);

        My_Story_List_Item listItems4 = new My_Story_List_Item("Hello World" ,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems4);

        My_Story_List_Item listItems5 = new My_Story_List_Item("Hello World" ,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems5);

        Collections.reverse(myStoryListItems);

        allStoriesAdapter = new AllStoriesAdapter(getContext(), myStoryListItems);

        recyclerView.setAdapter(allStoriesAdapter);

        return view;
    }

}

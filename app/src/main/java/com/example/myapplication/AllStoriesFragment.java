package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AllStoriesFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<My_Story_List_Items> myStoryListItems;
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


        My_Story_List_Items listItems = new My_Story_List_Items("Hello World","Wassup world how yall doing!!!!" ,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems);

        My_Story_List_Items listItems1 = new My_Story_List_Items("Hello World","Wassup world how yall doing!!!!" ,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems1);

        My_Story_List_Items listItems2 = new My_Story_List_Items("Hello World","Wassup world how yall doing!!!!" ,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems2);

        My_Story_List_Items listItems3 = new My_Story_List_Items("Hello World","Wassup world how yall doing!!!!" ,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems3);

        My_Story_List_Items listItems4 = new My_Story_List_Items("Hello World","Wassup world how yall doing!!!!" ,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems4);

        My_Story_List_Items listItems5 = new My_Story_List_Items("Hello World","Wassup world how yall doing!!!!" ,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        myStoryListItems.add(listItems5);

        Collections.reverse(myStoryListItems);

        allStoriesAdapter = new AllStoriesAdapter(getContext(), myStoryListItems);

        recyclerView.setAdapter(allStoriesAdapter);

        return view;
    }

}

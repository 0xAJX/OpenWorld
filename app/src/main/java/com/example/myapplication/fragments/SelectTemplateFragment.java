package com.example.myapplication.fragments;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.adapters.SelectTemplateAdapter;
import com.example.myapplication.model.Template_Item;

import java.util.ArrayList;
import java.util.List;

public class SelectTemplateFragment extends Fragment {

    RecyclerView recyclerView;
    private List<Template_Item> selectTemplateItems;
    private SelectTemplateAdapter selectTemplateAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selecttemplatefragment, null);

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Create New Story");

        recyclerView = view.findViewById(R.id.select_template_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        selectTemplateItems = new ArrayList<>();

        Template_Item listItems = new Template_Item("1", 1,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        selectTemplateItems.add(listItems);

        Template_Item listItems1 = new Template_Item( "2", 2,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        selectTemplateItems.add(listItems1);

        Template_Item listItems2 = new Template_Item("3", 3,BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.ic_launcher_background));
        selectTemplateItems.add(listItems2);

        //Collections.reverse(myStoryListItems);

        selectTemplateAdapter = new SelectTemplateAdapter(getContext(), selectTemplateItems);

        recyclerView.setAdapter(selectTemplateAdapter);

        return view;
    }

}

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

import com.example.myapplication.Models.Template;
import com.example.myapplication.R;
import com.example.myapplication.Adapters.SelectTemplateAdapter;
import com.example.myapplication.Handlers.UTDatabaseHandler;
import com.example.myapplication.ViewModels.TemplateViewModel;

import java.util.List;

public class SelectTemplateFragment extends Fragment {

    UTDatabaseHandler handler;
    RecyclerView recyclerView;
    private SelectTemplateAdapter selectTemplateAdapter;
    TemplateViewModel templateViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_template_fragment, null);

        recyclerView = view.findViewById(R.id.select_template_recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerView.setHasFixedSize(true);

        selectTemplateAdapter = new SelectTemplateAdapter(getContext());
        recyclerView.setAdapter(selectTemplateAdapter);

        /** Get story view model and show data */
        templateViewModel = ViewModelProviders.of(getActivity()).get(TemplateViewModel.class);
        templateViewModel.getAllTemplatest().observe(this, new Observer<List<Template>>() {
            @Override
            public void onChanged(List<Template> templates) {
                selectTemplateAdapter.setTemplates(templates);
            }
        });
        /** Get story view model and show data */

        return view;
    }
}

package com.example.myapplication.Fragments;

import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.Adapters.SelectTemplateAdapter;
import com.example.myapplication.Models.Template_Item;
import com.example.myapplication.UTDatabaseHandler;

import java.util.ArrayList;
import java.util.List;

public class SelectTemplateFragment extends Fragment {

    RecyclerView recyclerView;
    private List<Template_Item> selectTemplateItems;
    private SelectTemplateAdapter selectTemplateAdapter;
    UTDatabaseHandler handler;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.selecttemplatefragment, null);

        handler = new UTDatabaseHandler(getContext());

        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setTitle("Create New Story");

        recyclerView = view.findViewById(R.id.select_template_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        selectTemplateItems = new ArrayList<>();

        selectTemplateItems = handler.getTemplates();

        Log.d("selecttemplateitems", Integer.toString(selectTemplateItems.size()));

        for(int i = 0; i < selectTemplateItems.size();i++)
        {
            Log.d("selecttemplateitems",selectTemplateItems.get(i).getId());
        }

        new getTemplates().execute();

        return view;
    }


    private class getTemplates extends AsyncTask<Void, Integer, List<Template_Item>> {
        /*protected Long doInBackground(URL... urls) {
            int count = urls.length;
            long totalSize = 0;
            for (int i = 0; i < count; i++) {
                totalSize += Downloader.downloadFile(urls[i]);
                publishProgress((int) ((i / (float) count) * 100));
                // Escape early if cancel() is called
                if (isCancelled()) break;
            }
            return totalSize;
        }*/

        protected void onProgressUpdate(Integer... progress) {

        }

        @Override
        protected List<Template_Item> doInBackground(Void... voids) {

            List<Template_Item> template_items = handler.getTemplates();


            return template_items;
        }

        protected void onPostExecute(List<Template_Item> result) {

            selectTemplateAdapter = new SelectTemplateAdapter(getContext(), selectTemplateItems);
            recyclerView.setAdapter(selectTemplateAdapter);

            handler.close();
        }
    }


}

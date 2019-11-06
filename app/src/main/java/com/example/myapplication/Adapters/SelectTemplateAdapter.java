package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;


import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Activities.UpsertPageActivity;
import com.example.myapplication.Models.Template;
import com.example.myapplication.R;

import java.util.ArrayList;
import java.util.List;

public class SelectTemplateAdapter extends RecyclerView.Adapter<SelectTemplateAdapter.ViewHolder> {

    private Context context;
    private List<Template> templates =  new ArrayList<>();

    public SelectTemplateAdapter(Context context)
    {
        this.context = context;
    }

    @NonNull
    @Override
    public SelectTemplateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTemplateAdapter.ViewHolder holder, int position) {

        Template item = templates.get(position);

        int template_image = context.getResources().getIdentifier(
                "template" + item.getId() + "_foreground",
                "mipmap",
                context.getPackageName());

        holder.templateImage.setImageResource(template_image);

    }

    public void setTemplates(List<Template> templates) {
        this.templates = templates;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView templateImage;

        public ViewHolder(final View itemView) {
            super(itemView);

            templateImage = itemView.findViewById(R.id.templateimage);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Template template = templates.get(getAdapterPosition());
                    int id = template.getId();
                    Intent i = new Intent(context, UpsertPageActivity.class);
                    i.putExtra("template_id", id);
                    context.startActivity(i);
                }
            });

        }
    }
}

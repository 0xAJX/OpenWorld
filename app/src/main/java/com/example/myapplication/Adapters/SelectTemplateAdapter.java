package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;


import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.myapplication.Activities.MainActivity2;
import com.example.myapplication.Models.Template_Item;
import com.example.myapplication.R;

import java.util.List;

public class SelectTemplateAdapter extends RecyclerView.Adapter<SelectTemplateAdapter.ViewHolder> {

    private Context context;
    private List<Template_Item> listItems;

    public SelectTemplateAdapter(Context context, List listItem)
    {
        this.context = context;
        listItems = listItem;
    }

    @NonNull
    @Override
    public SelectTemplateAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.template_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectTemplateAdapter.ViewHolder holder, int position) {

        Template_Item item = listItems.get(position);
        //holder.myStoryTitle.setText(item.getTitle());

        int template_image = context.getResources().getIdentifier(
                "template" + item.getId() + "_foreground",
                "mipmap",
                context.getPackageName());

        holder.templateImage.setImageResource(template_image);

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView templateImage;

        public ViewHolder(final View itemView) {
            super(itemView);

            templateImage = itemView.findViewById(R.id.templateimage);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Template_Item template_item = listItems.get(getAdapterPosition());
                    String id = template_item.getId();
                    Intent i = new Intent(context, MainActivity2.class);
                    i.putExtra("template_id", id);
                    context.startActivity(i);
                }
            });

            //title = itemView.findViewById(R.id.title);
            //description = itemView.findViewById(R.id.description);
            //information = itemView.findViewById(R.id.information);
            //location = itemView.findViewById(R.id.location);

        }
    }
}

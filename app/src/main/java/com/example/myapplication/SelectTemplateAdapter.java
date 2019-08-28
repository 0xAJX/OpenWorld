package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class SelectTemplateAdapter extends RecyclerView.Adapter<SelectTemplateAdapter.ViewHolder> {

    private Context context;
    private List<Select_Template_Item> listItems;

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

        Select_Template_Item item = listItems.get(position);
        //holder.myStoryTitle.setText(item.getTitle());
        holder.templateImage.setImageResource(R.drawable.ic_create_black_24dp);
        //holder.myStoryDescription.setText(item.getDescription());
        //holder.information.setText(item.getInformation());
        //holder.location.setText(item.getLocation());
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

                    Select_Template_Item select_template_item = listItems.get(getAdapterPosition());

                    String id = select_template_item.getId();

                    Fragment fragment = new NewStoryFragment();


                    MainActivity activity = (MainActivity)context;

                    activity.getIntent().putExtra("template_id", id);

                    FragmentManager fragmentManager = activity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container , fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();

                }
            });

            //title = itemView.findViewById(R.id.title);
            //description = itemView.findViewById(R.id.description);
            //information = itemView.findViewById(R.id.information);
            //location = itemView.findViewById(R.id.location);

        }
    }
}

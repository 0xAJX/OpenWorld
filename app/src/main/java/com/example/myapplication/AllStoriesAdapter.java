package com.example.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class AllStoriesAdapter extends RecyclerView.Adapter<AllStoriesAdapter.ViewHolder> {

    private Context context;
    private List<My_Story_List_Items> listItems;

    public AllStoriesAdapter(Context context, List listItem)
    {
        this.context = context;
        listItems = listItem;
    }

    @NonNull
    @Override
    public AllStoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_story_row,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllStoriesAdapter.ViewHolder holder, int position) {

        My_Story_List_Items item = listItems.get(position);
        holder.myStoryTitle.setText(item.getTitle());
        holder.myStoryImage.setImageResource(R.drawable.ic_create_black_24dp);
        holder.myStoryDescription.setText(item.getDescription());
        //holder.information.setText(item.getInformation());
        //holder.location.setText(item.getLocation());
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView myStoryImage;
        public TextView myStoryTitle;
        public TextView myStoryDescription;
        public TextView information;
        public TextView location;

        public ViewHolder(View itemView) {
            super(itemView);

            myStoryTitle = itemView.findViewById(R.id.mystorytitle);
            myStoryImage = itemView.findViewById(R.id.mystoryimage);
            myStoryDescription = itemView.findViewById(R.id.mystorydescription);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            //title = itemView.findViewById(R.id.title);
            //description = itemView.findViewById(R.id.description);
            //information = itemView.findViewById(R.id.information);
            //location = itemView.findViewById(R.id.location);

        }
    }
}

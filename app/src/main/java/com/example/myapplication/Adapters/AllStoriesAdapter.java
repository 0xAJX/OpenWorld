package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Activities.MainActivity2;
import com.example.myapplication.Fragments.ShareBottomSheetFragment;
import com.example.myapplication.Models.User_Template_Item;
import com.example.myapplication.R;
import com.example.myapplication.SaveAlertDialog;
import com.example.myapplication.UTDatabaseHandler;

import java.util.List;

public class AllStoriesAdapter extends RecyclerView.Adapter<AllStoriesAdapter.ViewHolder> {

    private Context context;
    private List<User_Template_Item> listItems;

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

        User_Template_Item item = listItems.get(position);
        holder.myStoryTitle.setText(item.getStory_title());
        holder.myStoryImage.setImageURI(Uri.parse(item.getUser_template_location()));

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView myStoryImage;
        public TextView myStoryTitle;
        public Button share;
        public Button delete;

        public ViewHolder(View itemView) {
            super(itemView);

            myStoryTitle = itemView.findViewById(R.id.mystorytitle);
            myStoryImage = itemView.findViewById(R.id.mystoryimage);
            share = itemView.findViewById(R.id.mystoryshare);
            delete = itemView.findViewById(R.id.mystorydelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    User_Template_Item items = listItems.get(getAdapterPosition());
                    Intent intent = new Intent(context, MainActivity2.class);
                    intent.putExtra("user_template_id", items.getUser_template_id());
                    intent.putExtra("story_title", items.getStory_title());
                    intent.putExtra("template_id", items.getTemplate_id());

                    context.startActivity(intent);
                }
            });

            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listItems.get(getAdapterPosition()).getUser_template_location();
                    ShareBottomSheetFragment shareBottomSheetFragment = new ShareBottomSheetFragment(listItems.get(getAdapterPosition()).getUser_template_location());
                    shareBottomSheetFragment.show(shareBottomSheetFragment.getFragmentManager(), shareBottomSheetFragment.getTag());
                }
            });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UTDatabaseHandler handler = new UTDatabaseHandler(context);
                    handler.deleteUserTemplate(listItems.get(getAdapterPosition()).getUser_template_id());
                    handler.close();
                    listItems.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());


                    //notifyDataSetChanged();

                }
            });


            //title = itemView.findViewById(R.id.title);
            //description = itemView.findViewById(R.id.description);
            //information = itemView.findViewById(R.id.information);
            //location = itemView.findViewById(R.id.location);

        }
    }
}

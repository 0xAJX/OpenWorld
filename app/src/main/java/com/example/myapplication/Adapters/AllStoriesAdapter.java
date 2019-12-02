package com.example.myapplication.Adapters;

import android.content.Context;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.Fragments.ShareBottomSheetFragment;
import com.example.myapplication.Models.Story;
import com.example.myapplication.R;
import com.example.myapplication.ViewModels.StoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class AllStoriesAdapter extends RecyclerView.Adapter<AllStoriesAdapter.ViewHolder> {

    private Context context;
    private List<Story> stories = new ArrayList<>();

    public AllStoriesAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public AllStoriesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_story_row, parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllStoriesAdapter.ViewHolder holder, int position) {

        Story story = stories.get(position);
        holder.myStoryTitle.setText(story.getTitle());
        holder.myStoryImage.setImageURI(Uri.parse(story.getImage_location()));

    }

    @Override
    public int getItemCount() {
        return stories.size();
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageButton delete;
        public ImageView myStoryImage;
        public TextView myStoryTitle;
        public ImageButton share;

        public ViewHolder(View itemView) {
            super(itemView);

            delete = itemView.findViewById(R.id.mystorydelete);
            myStoryTitle = itemView.findViewById(R.id.mystorytitle);
            myStoryImage = itemView.findViewById(R.id.mystoryimage);
            share = itemView.findViewById(R.id.mystoryshare);

            /** Update feature removed temporarily */

            /*itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    UserTemplateItem items = listItems.get(getAdapterPosition());
                    Intent intent = new Intent(context, UpsertPageActivity.class);
                    intent.putExtra("user_template_id", items.getUser_template_id());
                    intent.putExtra("story_title", items.getStory_title());
                    intent.putExtra("template_id", items.getTemplate_id());

                    //context.startActivity(intent);
                }
            });*/

            /** Update feature removed temporarily */

            /** Start shareBottomSheetFragment when share button is clicked */
            share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    stories.get(getAdapterPosition()).getImage_location();
                    ShareBottomSheetFragment shareBottomSheetFragment = new ShareBottomSheetFragment(stories.get(getAdapterPosition()).getImage_location());
                    shareBottomSheetFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), shareBottomSheetFragment.getTag());
                }
            });
            /** Start shareBottomSheetFragment when share button is clicked */

            /** Delete user story when delete is clicked */

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    StoryViewModel storyViewModel = ViewModelProviders.of((FragmentActivity) context).get(StoryViewModel.class);
                    storyViewModel.delete(stories.get(getAdapterPosition()));
                    stories.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                }
            });

            /** Delete user story when delete is clicked */
        }
    }
}

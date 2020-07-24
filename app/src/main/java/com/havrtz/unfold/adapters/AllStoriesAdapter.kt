package com.havrtz.unfold.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.havrtz.unfold.fragments.ShareBottomSheetFragment
import com.havrtz.unfold.models.Story
import com.havrtz.unfold.R
import com.havrtz.unfold.viewmodels.StoryViewModel
import java.util.*

class AllStoriesAdapter(private val context: Context) : RecyclerView.Adapter<AllStoriesAdapter.ViewHolder>() {
    private var stories: MutableList<Story> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.my_story_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val story = stories[position]
        holder.myStoryTitle.text = story.title
        holder.myStoryImage.setImageURI(Uri.parse(story.image_location))
    }

    override fun getItemCount(): Int {
        return stories.size
    }

    fun setStories(stories: MutableList <Story>) {
        this.stories = stories
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var delete: ImageButton
        var myStoryImage: ImageView
        var myStoryTitle: TextView
        var share: ImageButton

        init {
            delete = itemView.findViewById(R.id.mystorydelete)
            myStoryTitle = itemView.findViewById(R.id.mystorytitle)
            myStoryImage = itemView.findViewById(R.id.mystoryimage)
            share = itemView.findViewById(R.id.mystoryshare)
            /** Update feature removed temporarily  */

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
            /** Update feature removed temporarily  */
            /** Start shareBottomSheetFragment when share button is clicked  */
            share.setOnClickListener {
                stories[adapterPosition].image_location
                val shareBottomSheetFragment = ShareBottomSheetFragment(stories[adapterPosition].image_location)
                shareBottomSheetFragment.show((context as AppCompatActivity).supportFragmentManager, shareBottomSheetFragment.tag)
            }
            /** Start shareBottomSheetFragment when share button is clicked  */
            /** Delete user story when delete is clicked  */
            delete.setOnClickListener {
                val storyViewModel = ViewModelProvider(context as FragmentActivity).get(StoryViewModel::class.java)
                storyViewModel.delete(stories[adapterPosition])

                stories.removeAt(adapterPosition)

                notifyItemRemoved(adapterPosition)
            }
            /** Delete user story when delete is clicked  */
        }
    }

}
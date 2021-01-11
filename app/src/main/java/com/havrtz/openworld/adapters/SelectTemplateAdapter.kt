package com.havrtz.openworld.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.havrtz.openworld.activities.UpsertPageActivity
import com.havrtz.openworld.models.Template
import com.havrtz.openworld.R
import com.havrtz.openworld.fragments.SelectTemplateFragmentDirections
import java.util.*

class SelectTemplateAdapter(private val context: Context) : RecyclerView.Adapter<SelectTemplateAdapter.ViewHolder>() {
    private var templates: List<Template> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = templates[position]
        val templateImage = context.resources.getIdentifier(
                "template" + item.id + "_foreground",
                "mipmap",
                context.packageName)
        holder.templateImage.setImageResource(templateImage)
    }

    fun setTemplates(templates: List<Template>) {
        this.templates = templates
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return templates.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var templateImage: ImageView = itemView.findViewById(R.id.templateimage)

        init {
            itemView.setOnClickListener {

                val template = templates[adapterPosition]
                val action = SelectTemplateFragmentDirections.actionSelectTemplateFragmentToUpsertPageActivity(template.id)
                Navigation.findNavController(itemView).navigate(action)

            }
        }
    }

}
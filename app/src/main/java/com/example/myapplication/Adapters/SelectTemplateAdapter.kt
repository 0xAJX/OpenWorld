package com.example.myapplication.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Activities.UpsertPageActivity
import com.example.myapplication.Models.Template
import com.example.myapplication.R
import java.util.*

class SelectTemplateAdapter(private val context: Context) : RecyclerView.Adapter<SelectTemplateAdapter.ViewHolder>() {
    private var templates: List<Template> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.template_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = templates[position]
        val template_image = context.resources.getIdentifier(
                "template" + item.id + "_foreground",
                "mipmap",
                context.packageName)
        holder.templateImage.setImageResource(template_image)
    }

    fun setTemplates(templates: List<Template>) {
        this.templates = templates
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return templates.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var templateImage: ImageView

        init {
            templateImage = itemView.findViewById(R.id.templateimage)
            itemView.setOnClickListener {
                val template = templates[adapterPosition]
                val id = template.id
                val i = Intent(context, UpsertPageActivity::class.java)
                i.putExtra("template_id", id)
                context.startActivity(i)
            }
        }
    }

}
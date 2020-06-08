package com.example.myapplication.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapters.SelectTemplateAdapter
import com.example.myapplication.Models.Template
import com.example.myapplication.R
import com.example.myapplication.ViewModels.TemplateViewModel

class SelectTemplateFragment : Fragment() {
    private var recyclerView: RecyclerView? = null
    private var templateViewModel: TemplateViewModel? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.select_template_fragment, null)
        recyclerView = view.findViewById(R.id.select_template_recyclerview)
        recyclerView.setLayoutManager(GridLayoutManager(context, 2))
        recyclerView.setHasFixedSize(true)
        val selectTemplateAdapter = SelectTemplateAdapter(context!!)
        recyclerView.setAdapter(selectTemplateAdapter)
        /** Get story view model and show data  */
        templateViewModel = ViewModelProviders.of(activity!!).get(TemplateViewModel::class.java)
        templateViewModel!!.allTemplatest.observe(this, Observer<List<Template?>?> { templates -> selectTemplateAdapter.setTemplates(templates) })
        /** Get story view model and show data  */
        return view
    }
}
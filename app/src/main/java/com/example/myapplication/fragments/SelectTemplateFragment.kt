package com.example.myapplication.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapters.SelectTemplateAdapter
import com.example.myapplication.models.Template
import com.example.myapplication.R
import com.example.myapplication.viewmodels.TemplateViewModel

class SelectTemplateFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var templateViewModel: TemplateViewModel
    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.select_template_fragment, null)
        recyclerView = view.findViewById(R.id.select_template_recyclerview)
        recyclerView.setLayoutManager(GridLayoutManager(context, 2))
        recyclerView.setHasFixedSize(true)
        val selectTemplateAdapter = context?.let { SelectTemplateAdapter(it) }
        recyclerView.setAdapter(selectTemplateAdapter)
        /** Get story view model and show data  */
        templateViewModel = ViewModelProvider(this).get(TemplateViewModel::class.java)
        templateViewModel.alltemplates?.observe(viewLifecycleOwner, Observer<List<Template?>?> { templates ->
            if (selectTemplateAdapter != null) {
                selectTemplateAdapter.setTemplates(templates as List<Template>)
            }
        })
        /** Get story view model and show data  */
        return view
    }
}
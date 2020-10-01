package com.havrtz.openworld.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.havrtz.openworld.adapters.SelectTemplateAdapter
import com.havrtz.openworld.models.Template
import com.havrtz.openworld.R
import com.havrtz.openworld.helpers.ColumnSizeCalculator
import com.havrtz.openworld.helpers.EqualSpacingItemDecoration
import com.havrtz.openworld.viewmodels.TemplateViewModel

class SelectTemplateFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    private lateinit var templateViewModel: TemplateViewModel

    @SuppressLint("InflateParams")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.select_template_fragment, null)

        recyclerView = view.findViewById(R.id.select_template_recyclerview)

        recyclerView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // get width and height of the view
                recyclerView.layoutManager = GridLayoutManager(context, 2)
                (recyclerView.layoutManager as GridLayoutManager).spanCount = ColumnSizeCalculator.calculateSize(recyclerView.width)
            }
        })

        recyclerView.addItemDecoration(EqualSpacingItemDecoration(16, EqualSpacingItemDecoration.HORIZONTAL))
        recyclerView.setHasFixedSize(true)

        val selectTemplateAdapter = context?.let { SelectTemplateAdapter(it) }
        recyclerView.adapter = selectTemplateAdapter
        
        /** Get story view model and show data  */
        templateViewModel = ViewModelProvider(this).get(TemplateViewModel::class.java)
        templateViewModel.alltemplates?.observe(viewLifecycleOwner, Observer<List<Template?>?> { templates ->
            selectTemplateAdapter?.setTemplates(templates as List<Template>)
        })
        /** Get story view model and show data  */

        return view
    }
}
package com.lislal.diy760

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class LeftFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: LayoutsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_left, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val customContainer: FrameLayout = view.findViewById(R.id.customContainer)

        // Setup the toolbar
        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbar)
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        // Indicate that this fragment would like to influence the set of actions in the action bar.
        setHasOptionsMenu(true)

        // Setup RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Initialize the adapter with data and set up click listener for dynamic content loading
        adapter = LayoutsAdapter(LayoutDataProvider.layoutsList.toMutableList()) { layoutModel ->
        // Dynamically load the custom layout associated with the clicked item
            val customView = LayoutInflater.from(context).inflate(layoutModel.resourceId, customContainer, false)
            customContainer.removeAllViews()
            customContainer.addView(customView)
            // Here, you could initialize components of customView if necessary
        }
        recyclerView.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu_search, menu)

        // Setup the SearchView
        val searchItem = menu.findItem(R.id.action_search)
        val searchView = searchItem.actionView as SearchView

        // Configure the search info and add any event listeners...
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // This is where you could handle query submission, e.g., perform search
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Filter the adapter's dataset based on the user's search query
                adapter.filter(newText ?: "")
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }
}
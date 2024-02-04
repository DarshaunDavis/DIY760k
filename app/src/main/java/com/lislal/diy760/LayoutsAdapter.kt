package com.lislal.diy760

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LayoutsAdapter(private var layouts: MutableList<LayoutModel>, private val onItemClick: (LayoutModel) -> Unit) : RecyclerView.Adapter<LayoutsAdapter.LayoutViewHolder>() {

    // Keep a full list to revert back to the original list when needed
    private var fullLayoutsList: List<LayoutModel> = layouts.toList()

    class LayoutViewHolder(view: View, onItemClick: (Int) -> Unit) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.layoutTitle)

        init {
            view.setOnClickListener {
                onItemClick(adapterPosition)
            }
        }

        fun bind(layout: LayoutModel) {
            titleTextView.text = layout.title
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return LayoutViewHolder(view) { position ->
            onItemClick(layouts[position])
        }
    }

    override fun onBindViewHolder(holder: LayoutViewHolder, position: Int) {
        holder.bind(layouts[position])
    }

    override fun getItemCount() = layouts.size

    fun updateList(newList: List<LayoutModel>) {
        layouts.clear()
        layouts.addAll(newList)
        notifyDataSetChanged()
    }

    // Method to filter the list based on a search query
    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullLayoutsList
        } else {
            fullLayoutsList.filter { layoutModel ->
                layoutModel.title.contains(query, ignoreCase = true)
            }
        }
        updateList(filteredList)
    }
}
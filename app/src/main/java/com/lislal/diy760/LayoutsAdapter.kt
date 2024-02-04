package com.lislal.diy760

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class LayoutsAdapter(private var layouts: MutableList<LayoutModel>, private val onItemClick: (LayoutModel) -> Unit) : RecyclerView.Adapter<LayoutsAdapter.LayoutViewHolder>() {

    private var fullLayoutsList: List<LayoutModel> = layouts.toList()
    private var expandedPosition = -1 // Track the currently expanded item position

    inner class LayoutViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val titleTextView: TextView = view.findViewById(R.id.layoutTitle)
        private val descriptionTextView: TextView = view.findViewById(R.id.layoutDescription)

        fun bind(layout: LayoutModel, position: Int) {
            titleTextView.text = layout.title
            descriptionTextView.text = layout.description

            // Set visibility based on expanded position
            descriptionTextView.visibility = if (position == expandedPosition) View.VISIBLE else View.GONE

            itemView.setOnClickListener {
                // Efficient handling of expanded/collapsed items
                val previousExpandedPosition = expandedPosition
                if (expandedPosition == position) {
                    expandedPosition = -1 // Collapse the current item if it's already expanded
                } else {
                    expandedPosition = position // Expand the new item
                }

                // Avoid unnecessary calls if no item was previously expanded
                if (previousExpandedPosition != -1) {
                    notifyItemChanged(previousExpandedPosition)
                }
                notifyItemChanged(expandedPosition)

                onItemClick(layout)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LayoutViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item, parent, false)
        return LayoutViewHolder(view)
    }

    override fun onBindViewHolder(holder: LayoutViewHolder, position: Int) {
        holder.bind(layouts[position], position)
    }

    override fun getItemCount() = layouts.size

    fun updateList(newList: List<LayoutModel>) {
        layouts = newList.toMutableList()
        fullLayoutsList = newList.toList()
        notifyDataSetChanged() // Use with caution; consider more specific update methods when possible
        expandedPosition = -1 // Reset expanded position when the list is updated
    }

    fun filter(query: String) {
        val filteredList = if (query.isEmpty()) {
            fullLayoutsList
        } else {
            fullLayoutsList.filter {
                it.title.contains(query, ignoreCase = true)
            }
        }
        updateList(filteredList)
    }
}
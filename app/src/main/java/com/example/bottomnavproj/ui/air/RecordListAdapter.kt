package com.example.bottomnavproj.ui.air

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bottomnavproj.R
import com.example.bottomnavproj.data.model.Record

class RecordListAdapter(
    private val onItemClick: (Record) -> Unit,
    private val onDeleteClick: (Record) -> Unit
) : ListAdapter<Record, RecordListAdapter.AirViewHolder>(RecordDiffCallback) {

    companion object RecordDiffCallback : DiffUtil.ItemCallback<Record>() {
        override fun areItemsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem.title == newItem.title // Or a unique ID if you have one
        }

        override fun areContentsTheSame(oldItem: Record, newItem: Record): Boolean {
            return oldItem == newItem
        }
    }

    inner class AirViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title = itemView.findViewById<TextView>(R.id.title_text_view)
        private val distance = itemView.findViewById<TextView>(R.id.distance_text_view)
        private val date = itemView.findViewById<TextView>(R.id.date_text_view)
        private val deleteButton = itemView.findViewById<Button>(R.id.delete_button)

        fun bind(item: Record) {
            title.text = item.title
            distance.text = item.distance
            date.text = item.date

            itemView.setOnClickListener {
                onItemClick(item)
            }

            deleteButton.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AirViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_location_button, parent, false)
        return AirViewHolder(view)
    }

    override fun onBindViewHolder(holder: AirViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}


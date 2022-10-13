package com.ppigacko.instapostimagepicker.picker.item.media

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ppigacko.instapostimagepicker.R
import com.ppigacko.instapostimagepicker.ext.createView

class MediaAdapter(
    val selectedCallback: (MediaModel) -> Unit
) : RecyclerView.Adapter<MediaViewHolder>() {
    private val list = ArrayList<MediaModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val itemViewHolder = MediaViewHolder(parent.createView(R.layout.item_media))
        itemViewHolder.itemView.setOnClickListener {
            selectedCallback(list[itemViewHolder.adapterPosition])
        }
        return itemViewHolder
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    fun clear() {
        if (list.isEmpty()) {
            return
        }
        list.clear()
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addAll(items: List<MediaModel>) {
        if (items.isEmpty()) {
            return
        }

        list.addAll(items)
        notifyDataSetChanged()
    }
}
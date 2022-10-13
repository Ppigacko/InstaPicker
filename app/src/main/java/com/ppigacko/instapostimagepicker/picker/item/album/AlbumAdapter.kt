package com.ppigacko.instapostimagepicker.picker.item.album

import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ppigacko.instapostimagepicker.R
import com.ppigacko.instapostimagepicker.ext.createView

class AlbumAdapter(
    val selectedCallback: (AlbumModel) -> Unit
) : RecyclerView.Adapter<AlbumViewHolder>() {
    private val list = ArrayList<AlbumModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val itemViewHolder = AlbumViewHolder(parent.createView(R.layout.item_album))
        itemViewHolder.itemView.setOnClickListener {
            selectedCallback(list[itemViewHolder.adapterPosition])
        }
        return itemViewHolder
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
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
    fun addAll(items: List<AlbumModel>) {
        if (items.isEmpty()) {
            return
        }

        list.addAll(items)
        notifyDataSetChanged()
    }
}
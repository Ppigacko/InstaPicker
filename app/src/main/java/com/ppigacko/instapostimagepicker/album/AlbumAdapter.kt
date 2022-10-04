package com.ppigacko.instapostimagepicker.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ppigacko.instapostimagepicker.databinding.ItemAlbumBinding


class AlbumAdapter(
    private val onItemClickListener: (AlbumItem) -> Unit
) : ListAdapter<AlbumItem, AlbumViewHolder>(DIFF) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<AlbumItem>() {
            override fun areItemsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: AlbumItem, newItem: AlbumItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        return AlbumViewHolder(ItemAlbumBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }
}


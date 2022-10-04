package com.ppigacko.instapostimagepicker

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ppigacko.instapostimagepicker.databinding.ItemRecentPhotoBinding


class RecentPhotoAdapter(
    private val onItemClickListener: (Uri) -> Unit
) : ListAdapter<Uri, RecentPhotoViewHolder>(DIFF) {
    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Uri>() {
            override fun areItemsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Uri, newItem: Uri): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecentPhotoViewHolder {
        return RecentPhotoViewHolder(ItemRecentPhotoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: RecentPhotoViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }
}


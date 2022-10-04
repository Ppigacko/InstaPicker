package com.ppigacko.instapostimagepicker.photo

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.ppigacko.instapostimagepicker.databinding.ItemRecentPhotoBinding


class PhotoAdapter(
    private val onItemClickListener: (Uri) -> Unit
) : ListAdapter<Uri, PhotoViewHolder>(DIFF) {
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(ItemRecentPhotoBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        holder.bind(getItem(position), onItemClickListener)
    }
}


package com.ppigacko.instapostimagepicker

import android.net.Uri
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ppigacko.instapostimagepicker.databinding.ItemRecentPhotoBinding

class RecentPhotoViewHolder(
    private val binding: ItemRecentPhotoBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(imageUri: Uri, onItemClickListener: (Uri) -> Unit) {
        binding.image.load(imageUri)
        binding.root.setOnClickListener {
            onItemClickListener(imageUri)
        }
    }
}
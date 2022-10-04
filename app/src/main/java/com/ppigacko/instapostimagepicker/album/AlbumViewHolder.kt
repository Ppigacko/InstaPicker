package com.ppigacko.instapostimagepicker.album

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.ppigacko.instapostimagepicker.databinding.ItemAlbumBinding

class AlbumViewHolder(
    private val binding: ItemAlbumBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(albumItem: AlbumItem, onItemClickListener: (AlbumItem) -> Unit) {
        binding.imagePreview.load(albumItem.previewImage)
        binding.textDirectory.text = albumItem.directoryName
        binding.textPhotoCount.text = albumItem.photoCount.toString()
        binding.root.setOnClickListener {
            onItemClickListener(albumItem)
        }
    }
}


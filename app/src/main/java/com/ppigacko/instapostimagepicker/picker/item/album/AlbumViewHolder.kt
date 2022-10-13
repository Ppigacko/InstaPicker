package com.ppigacko.instapostimagepicker.picker.item.album

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ppigacko.instapostimagepicker.databinding.ItemAlbumBinding

class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemAlbumBinding? = DataBindingUtil.bind(itemView)

    fun onBind(itemModel: AlbumModel, position: Int) {
        binding?.run {
            if (album != itemModel) {
                album = itemModel
            }
            executePendingBindings()
        }
    }
}
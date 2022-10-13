package com.ppigacko.instapostimagepicker.picker.item.media

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ppigacko.instapostimagepicker.databinding.ItemMediaBinding

class MediaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemMediaBinding? = DataBindingUtil.bind(itemView)

    fun onBind(itemModel: MediaModel, position: Int) {
        binding?.run {
            if (media != itemModel) {
                media = itemModel
            }
            executePendingBindings()
        }
    }
}
package com.ppigacko.instapostimagepicker.picker.view

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.ppigacko.instapostimagepicker.R
import com.ppigacko.instapostimagepicker.databinding.DialogAlbumBinding
import com.ppigacko.instapostimagepicker.picker.item.album.AlbumAdapter
import com.ppigacko.instapostimagepicker.picker.item.album.AlbumModel

class AlbumListBottomSheet(
    context: Context,
    private val albumList: List<AlbumModel>,
    private val selectedCallback: (AlbumModel) -> Unit
) : BottomSheetDialog(context, R.style.BottomSheetStyle) {


    private val callback: (AlbumModel) -> Unit = { album ->
        selectedCallback(album)
        dismiss()
    }

    private val albumAdapter = AlbumAdapter(callback)
    private val binding by lazy {
        DialogAlbumBinding.inflate(layoutInflater).apply {
            rvAlbum.adapter = albumAdapter
        }
    }

    init {
        setContentView(binding.root)
        albumAdapter.clear()
        albumAdapter.addAll(albumList)
    }
}
package com.ppigacko.instapostimagepicker

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.gun0912.tedpermission.coroutine.TedPermission
import com.ppigacko.instapostimagepicker.album.AlbumAdapter
import com.ppigacko.instapostimagepicker.album.AlbumDivider
import com.ppigacko.instapostimagepicker.databinding.ActivityMainBinding
import com.ppigacko.instapostimagepicker.photo.PhotoAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel(PhotoProvider)

    private val photoAdapter by lazy {
        PhotoAdapter {
            binding.imageMain.load(it)
            binding.imageMain.scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }

    private val albumAdapter by lazy {
        AlbumAdapter {
            viewModel.selectAlbum(it.directoryName)
            binding.textTitle.setText(R.string.new_posts)
            binding.buttonAlbum.text = it.directoryName
            binding.layoutAlbum.isVisible = false
            binding.buttonCancel.isVisible = false
            binding.buttonNext.isVisible = true
            binding.buttonClose.isVisible = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.selectAlbum(PhotoProvider.ALL_PHOTO)

        lifecycleScope.launch {
            val permissionResult = TedPermission.create().setPermissions(READ_EXTERNAL_STORAGE).check()

            if (!permissionResult.isGranted) {
                finish()
            }
        }

        binding.recyclerPhotos.layoutManager = GridLayoutManager(this, 4)
        binding.recyclerPhotos.adapter = photoAdapter

        binding.recyclerAlbums.layoutManager = LinearLayoutManager(this)
        binding.recyclerAlbums.adapter = albumAdapter
        binding.recyclerAlbums.addItemDecoration(AlbumDivider(10f, 0f, Color.BLACK))

        viewModel.albums.onEach {
            albumAdapter.submitList(it)
        }.launchIn(lifecycleScope)

        viewModel.photos.onEach {
            photoAdapter.submitList(it)
            it.firstOrNull()?.let { binding.imageMain.load(it) }
            delay(100)
            binding.recyclerPhotos.smoothScrollToPosition(0)
        }.launchIn(lifecycleScope)

        binding.buttonAlbum.setOnClickListener {
            binding.textTitle.setText(R.string.select_album)
            binding.layoutAlbum.isVisible = true
            binding.buttonCancel.isVisible = true
            binding.buttonNext.isVisible = false
            binding.buttonClose.isVisible = false
        }

        binding.buttonCancel.setOnClickListener {
            binding.textTitle.setText(R.string.new_posts)
            binding.layoutAlbum.isVisible = false
            binding.buttonCancel.isVisible = false
            binding.buttonNext.isVisible = true
            binding.buttonClose.isVisible = true
        }

        binding.buttonCamera.setOnClickListener {
            lifecycleScope.launch {
                val permissionResult = TedPermission.create().setPermissions(CAMERA).check()
                if (permissionResult.isGranted) {
                    startActivity(Intent("android.media.action.IMAGE_CAPTURE"))
                }
            }
        }

        binding.imageCrop.setOnClickListener {
            if (binding.imageMain.scaleType == ImageView.ScaleType.CENTER) {
                binding.imageMain.scaleType = ImageView.ScaleType.CENTER_CROP
            } else {
                binding.imageMain.scaleType = ImageView.ScaleType.CENTER
            }
        }
    }
}
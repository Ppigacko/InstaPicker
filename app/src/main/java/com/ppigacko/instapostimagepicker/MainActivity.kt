package com.ppigacko.instapostimagepicker

import android.Manifest.permission.CAMERA
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.ViewTreeObserver
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.gun0912.tedpermission.coroutine.TedPermission
import com.ppigacko.instapostimagepicker.databinding.ActivityMainBinding
import kotlinx.coroutines.launch
import java.io.File


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val adapter by lazy {
        RecentPhotoAdapter {
            binding.imageMain.load(it)
            binding.imageMain.scaleType = ImageView.ScaleType.FIT_CENTER
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val permissionResult = TedPermission.create()
                .setPermissions(READ_EXTERNAL_STORAGE)
                .check()

            if (!permissionResult.isGranted) {
                finish()
            }
        }

        binding.recyclerPhotos.layoutManager = GridLayoutManager(this, 4)
        binding.recyclerPhotos.adapter = adapter

        adapter.submitList(PhotoProvider.getImagesPath(this@MainActivity).map { Uri.fromFile(File(it)) })

        binding.recyclerPhotos.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                binding.recyclerPhotos.findViewHolderForAdapterPosition(0)?.itemView?.performClick()
                binding.recyclerPhotos.viewTreeObserver.removeOnPreDrawListener(this)
                return true
            }
        })

        binding.buttonCamera.setOnClickListener {
            lifecycleScope.launch {
                val permissionResult = TedPermission.create()
                    .setPermissions(CAMERA)
                    .check()
                if (permissionResult.isGranted) {
                    startActivity(Intent("android.media.action.IMAGE_CAPTURE"))
                }
            }
        }

        binding.imageCrop.setOnClickListener {
            if (binding.imageMain.scaleType == ImageView.ScaleType.CENTER)
                binding.imageMain.scaleType = ImageView.ScaleType.CENTER_CROP
            else
                binding.imageMain.scaleType = ImageView.ScaleType.CENTER
        }
    }

}
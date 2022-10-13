package com.ppigacko.instapostimagepicker.picker.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Bundle
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS
import android.view.MotionEvent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.ppigacko.instapostimagepicker.databinding.ActivityPickerBinding
import com.ppigacko.instapostimagepicker.picker.item.media.MediaAdapter
import com.ppigacko.instapostimagepicker.util.setHeight
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs
import com.ppigacko.instapostimagepicker.picker.item.album.AlbumModel
import com.ppigacko.instapostimagepicker.picker.item.media.MediaModel
import presentation.picker.viewmodel.PickerViewModel

@AndroidEntryPoint
class PickerActivity : AppCompatActivity() {

    private val viewModel: PickerViewModel by viewModels()
    private val mediaAdapter = MediaAdapter(onImageClick())
    private val binding: ActivityPickerBinding by lazy {
        ActivityPickerBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@PickerActivity
            viewModel = this@PickerActivity.viewModel
        }
    }

    private var threshold = 200
    private val targetY by lazy { binding.albumName.bottom }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        initObserver()
        openMediaStore()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        binding.rvMedia.adapter = mediaAdapter
        binding.albumName.setOnClickListener {
            showAlbumSelectBottomSheet()
        }
        binding.albumName.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    if (targetY >= event.rawY) {
                        binding.ivImage.y =
                            event.rawY - binding.ivImage.height - binding.albumName.height
                        binding.albumName.y = event.rawY - binding.albumName.height
                        binding.rvMedia.y = event.rawY
                        binding.rvMedia.setHeight(targetY + (targetY - event.rawY.toInt()))
                    }
                }
                MotionEvent.ACTION_UP -> {
                    startSnapAnimate()
                }
            }
            false
        }
        binding.rvMedia.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_MOVE -> {
                    if (targetY >= event.rawY && (abs(binding.albumName.y - event.rawY)) <= threshold) {
                        binding.ivImage.y =
                            event.rawY - binding.ivImage.height - binding.albumName.height
                        binding.albumName.y = event.rawY - binding.albumName.height
                        binding.rvMedia.y = event.rawY
                        binding.rvMedia.setHeight(targetY + (targetY - event.rawY.toInt()))
                    }
                }
                MotionEvent.ACTION_UP -> {
                    if (abs(binding.albumName.y - event.rawY) <= threshold) {
                        startSnapAnimate()
                    }
                }
            }
            false
        }
        binding.rvMedia.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->

        }
    }

    private fun startSnapAnimate() {
        if (binding.ivImage.height / 2 > binding.albumName.y) {
            snapDown()
        } else {
            snapUp()
        }
    }

    private fun snapDown() {
        binding.ivImage.animate()
            .translationY(-binding.ivImage.height.toFloat())
            .setDuration(DURATION)
        binding.albumName.animate()
            .translationY(-binding.ivImage.height.toFloat())
            .setDuration(DURATION)
        binding.rvMedia.animate()
            .translationY(-binding.rvMedia.top.toFloat() + binding.albumName.height)
            .setDuration(DURATION)
    }

    private fun snapUp() {
        resetPosition()
    }

    private fun resetPosition() {
        binding.ivImage.animate()
            .translationY(0f)
            .setDuration(DURATION)
        binding.albumName.animate()
            .translationY(0f)
            .setDuration(DURATION)
        binding.rvMedia.animate()
            .translationY(binding.albumName.bottom.toFloat() - binding.rvMedia.top.toFloat())
            .setDuration(DURATION)
    }

    private fun showAlbumSelectBottomSheet() {
        AlbumListBottomSheet(
            context = this,
            albumList = viewModel.albumList,
            selectedCallback = onAlbumClick()
        ).show()
    }

    private fun initObserver() {
        viewModel.mediaList.observe(this) {
            mediaAdapter.clear()
            mediaAdapter.addAll(it)
        }
    }


    @SuppressLint("MissingSuperCall")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED) {
                    viewModel.loadAlbum()
                } else {
                    val showRationale =
                        ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                    if (!showRationale) {
                        goToSettings()
                    }
                }
                return
            }
        }
    }

    private fun onImageClick(): (MediaModel) -> Unit = { media ->
        resetPosition()
        viewModel.updateCurrentMedia(media)
    }

    private fun onAlbumClick(): (AlbumModel) -> Unit = { album ->
        resetPosition()
        viewModel.updateCurrentAlbum(album)
    }

    private fun haveStoragePermission() =
        ContextCompat.checkSelfPermission(
            this, Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PERMISSION_GRANTED

    private fun requestPermission() {
        if (!haveStoragePermission()) {
            val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
            ActivityCompat.requestPermissions(this, permissions, READ_EXTERNAL_STORAGE_REQUEST)
        }
    }

    private fun goToSettings() {
        Intent(ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName")).apply {
            addCategory(Intent.CATEGORY_DEFAULT)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }.also { intent ->
            startActivity(intent)
        }
    }

    private fun openMediaStore() {
        if (haveStoragePermission()) {
            viewModel.loadAlbum()
        } else {
            requestPermission()
        }
    }

    companion object {
        private const val DURATION = 500L
        private const val READ_EXTERNAL_STORAGE_REQUEST = 0x1045
    }

}
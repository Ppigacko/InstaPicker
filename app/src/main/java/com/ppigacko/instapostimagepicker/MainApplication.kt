package com.ppigacko.instapostimagepicker

import android.app.Application

object MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        PhotoProvider.init()
    }
}
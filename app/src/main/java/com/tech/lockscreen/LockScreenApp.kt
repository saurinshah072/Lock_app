package com.tech.lockscreen

import android.app.Application

class LockScreenApp: Application() {

    companion object {
        private lateinit var mInstant: LockScreenApp
        fun getInstance(): LockScreenApp {
            return mInstant
        }
    }

    override fun onCreate() {
        super.onCreate()
        mInstant = this
        AppPreferences.initialize(this)
    }

}
package com.tech.lockscreen

import android.app.KeyguardManager
import android.app.admin.DevicePolicyManager
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.itsxtt.patternlock.PatternLockView
import com.tech.lockscreen.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.deviceLockPattern.setOnClickListener {
            startPhonePinSecurity()
        }

        binding.customClockPattern.setOnClickListener {
            Intent(this, CustomLockScreenActivity::class.java).apply {
                startActivity(this)
            }
        }

    }

    private fun startPhonePinSecurity() {
        val km = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
        if (km.isKeyguardSecure) {
            val authIntent = km.createConfirmDeviceCredentialIntent(
                "Lock Screen",
                "This App is using Phone's default lock pattern",
            )
            startActivityForResult(authIntent, 100)
        }
    }

}
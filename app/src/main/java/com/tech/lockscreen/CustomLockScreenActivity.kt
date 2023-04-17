package com.tech.lockscreen

import android.app.KeyguardManager
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.itsxtt.patternlock.PatternLockView
import com.tech.lockscreen.databinding.ActivityCustomLockScreenBinding

class CustomLockScreenActivity : AppCompatActivity() {

    private val TAG = "CustomLockScreenActivit"
    private lateinit var binding: ActivityCustomLockScreenBinding
    private var pattern: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomLockScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (AppPreferences.getUserData(Params.IS_PATTERN_SAVE) == "1") {
            binding.titleTv.text = getString(R.string.verify_pattern)
        } else {
            binding.titleTv.text = getString(R.string.setup_new_pattern)
        }

        binding.patternLockView.setOnPatternListener(listener)

    }

    private var listener = object : PatternLockView.OnPatternListener {

        override fun onStarted() {
            super.onStarted()
        }

        override fun onProgress(ids: ArrayList<Int>) {
            super.onProgress(ids)
        }

        override fun onComplete(ids: ArrayList<Int>): Boolean {
            pattern = getPatternString(ids)
            if (AppPreferences.getUserData(Params.IS_PATTERN_SAVE) == "1") {
                if (isCorrectPattern(ids)) {
                    onBackPressed()
                    toast("Pattern Verified")
                } else {
                    toast("Wrong Pattern! Please try again")
                }
            } else {
                if (pattern.isNotEmpty()) {
                    if (pattern.length == 4) {
                        AppPreferences.setUserData(Params.IS_PATTERN_SAVE, "1")
                        AppPreferences.setUserData(Params.PATTERN, pattern.toString())
                        toast("Pattern Saved Successfully")
                        onBackPressed()
                    } else {
                        toast("Pattern should be at-least 4 dots")
                    }
                } else {
                    toast("Please draw pattern first!")
                }
            }
            return true
        }
    }

    private fun isCorrectPattern(pattern: ArrayList<Int>): Boolean {
        val patternString = getPatternString(pattern)
        return AppPreferences.getUserData(Params.PATTERN) == patternString
    }

    private fun getPatternString(ids: ArrayList<Int>): String {
        var result = ""
        for (id in ids) {
            result += id.toString()
        }
        return result
    }

    fun Context.toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
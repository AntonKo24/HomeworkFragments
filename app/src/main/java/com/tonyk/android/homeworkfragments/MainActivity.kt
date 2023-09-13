package com.tonyk.android.homeworkfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.tonyk.android.homeworkfragments.util.DeviceChecker

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(if (DeviceChecker.isTablet(this)) R.layout.activity_main_tablet else R.layout.activity_main)
    }
}



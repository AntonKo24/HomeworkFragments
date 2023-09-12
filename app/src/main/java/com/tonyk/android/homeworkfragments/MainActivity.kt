package com.tonyk.android.homeworkfragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(if (resources.configuration.screenWidthDp >= 600) R.layout.activity_main_tablet else R.layout.activity_main)
    }
}



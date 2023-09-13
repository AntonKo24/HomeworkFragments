package com.tonyk.android.homeworkfragments.util

import android.content.Context
import com.tonyk.android.homeworkfragments.R

object DeviceChecker {
    fun isTablet(context: Context): Boolean {
        return context.resources.getBoolean(R.bool.is_tablet)
    }
}
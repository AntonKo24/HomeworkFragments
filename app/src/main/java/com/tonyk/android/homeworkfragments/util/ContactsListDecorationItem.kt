package com.tonyk.android.homeworkfragments.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.tonyk.android.homeworkfragments.R

class ContactsListDecorationItem(context: Context) : RecyclerView.ItemDecoration() {

    private val logoDivider: Drawable? =
        ContextCompat.getDrawable(context, R.drawable.andersen_divider)

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft + 32
        val right = parent.width - parent.paddingRight - 32

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            val bottom = top + (logoDivider?.intrinsicHeight ?: 0)

            logoDivider?.setBounds(left, top, right, bottom)
            logoDivider?.draw(c)
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.set(0, 0, 0, logoDivider?.intrinsicHeight ?: 0)
    }
}
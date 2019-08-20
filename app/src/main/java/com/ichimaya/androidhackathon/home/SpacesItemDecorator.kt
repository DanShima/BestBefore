package com.ichimaya.androidhackathon.home

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Applies equal divider space between the grids
 */

class SpacesItemDecorator(space: Int) : RecyclerView.ItemDecoration() {
    private val offset = space / 3

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.apply {
            left = offset
            right = offset
            bottom = offset
            top = offset
        }
    }
}
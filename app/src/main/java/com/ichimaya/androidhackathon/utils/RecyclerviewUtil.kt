package com.ichimaya.androidhackathon.utils

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.ichimaya.androidhackathon.R


fun RecyclerView.showDividerBetweenListItems() {
    val context = this.context
    val drawable = ContextCompat.getDrawable(context, R.drawable.divider) ?: throw Exception("Missing drawable")
    DividerItemDecoration(context, DividerItemDecoration.VERTICAL).let {
        it.setDrawable(drawable)
        addItemDecoration(it)
    }
}
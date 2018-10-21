package com.ichimaya.androidhackathon.utils

import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import com.ichimaya.androidhackathon.R


fun RecyclerView.showDividerBetweenListItems() {
    val context = this.context
    val drawable = ContextCompat.getDrawable(context, R.drawable.divider) ?: throw Exception("Missing drawable")
    DividerItemDecoration(context, DividerItemDecoration.VERTICAL).let {
        it.setDrawable(drawable)
        addItemDecoration(it)
    }
}
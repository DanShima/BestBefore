package com.ichimaya.androidhackathon.detail

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.model.Category
import com.ichimaya.androidhackathon.food.model.Food
import kotlinx.android.synthetic.main.food_category_grid_item.view.*


/**
 * Adapter for the RecyclerView that displays food in categories
 */

typealias ClickListener = (Int) -> Unit

class DetailListAdapter(
    private val onClickListener: ClickListener,
    private val list: MutableList<Food>
) : RecyclerView.Adapter<DetailListAdapter.ViewHolder>() {
    private lateinit var context: Context

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        val categoryIcon: ImageView = itemView.category_icon
        val categoryName: TextView = itemView.category_name

        override fun onClick(view: View) {
            onClickListener(adapterPosition)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        return ViewHolder(
            inflater.inflate(R.layout.food_category_grid_item, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = list[position]

        viewHolder.apply {
            categoryIcon.setImageResource(item.icon)
            categoryName.text = item.title
        }

    }

    override fun getItemCount() = list.size

    fun getItem(position: Int): Food {
        return list[position]
    }

}

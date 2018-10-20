package com.ichimaya.androidhackathon.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.model.Food


/**
 * Adapter for the recyclerview that displays food in categories
 */

typealias ClickListener = (Int) -> Unit

class GridListAdapter(
    private val onClickListener: ClickListener
) : RecyclerView.Adapter<GridListAdapter.ViewHolder>() {
    private lateinit var context: Context
    private var items: List<Food> = mutableListOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

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
        val item = items[position]


        viewHolder.apply {

        }
    }

    override fun getItemCount() = items.size

    fun getItem(position: Int): Food {
        return items[position]
    }


    fun setList(list: List<Food>) {
        items = list
    }
}

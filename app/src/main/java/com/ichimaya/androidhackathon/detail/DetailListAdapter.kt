package com.ichimaya.androidhackathon.detail

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.model.Food
import kotlinx.android.synthetic.main.food_detail_list_item.view.*


/**
 * Adapter for the RecyclerView that displays food in categories
 */

typealias ClickListener = (Int) -> Unit
typealias CheckListener = (Boolean, Food) -> Unit

class DetailListAdapter(
    private val onClickListener: ClickListener,
    private val onCheckListener: CheckListener
) : RecyclerView.Adapter<DetailListAdapter.ViewHolder>() {
    private lateinit var context: Context

    var foods: List<Food> = listOf()

    fun updateFoods(foods: List<Food>) {
        this.foods = foods
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        val foodIcon: ImageView = itemView.logo_detail
        val foodTitle: TextView = itemView.title_detail
        val checkDone: CheckBox = itemView.checkbox_detail

        fun bind() {
            checkDone.setOnCheckedChangeListener(this)
            checkDone.isChecked = false
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            onClickListener(adapterPosition)
        }

        override fun onCheckedChanged(view: CompoundButton, checked: Boolean) {
            onCheckListener(checked, foods[adapterPosition])
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        return ViewHolder(
            inflater.inflate(R.layout.food_detail_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = foods[position]

        viewHolder.apply {
            foodIcon.setImageResource(R.drawable.ic_fruit)
            foodTitle.text = item.name
        }.bind()

    }

    override fun getItemCount() = foods.size

    fun getItem(position: Int): Food {
        return foods[position]
    }

}

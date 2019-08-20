package com.ichimaya.androidhackathon.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.model.*
import kotlinx.android.synthetic.main.food_detail_list_item.view.*
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter


/**
 * Adapter for the RecyclerView that displays food in categories
 */

typealias ClickListener = (Int) -> Unit

typealias CheckListener = (Boolean, Food) -> Unit

class DetailListAdapter(
        private val categoryTitle: String,
        private val onClickListener: ClickListener,
        private val onCheckListener: CheckListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private lateinit var context: Context

    var foods: List<Food> = listOf()

    fun updateFoods(foods: List<Food>) {
        this.foods = foods
        notifyDataSetChanged()
    }

    inner class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, CompoundButton.OnCheckedChangeListener {
        val foodIcon: ImageView = itemView.logo_detail
        val foodTitle: TextView = itemView.title_detail
        val expirationDate: TextView = itemView.expiration_date
        val checkDone: CheckBox = itemView.checkbox_detail
        val expirationWarning: TextView = itemView.expiration_warning

        fun bindListeners() {
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

    inner class SimpleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RecyclerView.ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        return if (viewType == TYPE_FOOD) {
            FoodViewHolder(
                    inflater.inflate(R.layout.food_detail_list_item, parent, false)
            )
        } else {
            SimpleViewHolder(
                    inflater.inflate(R.layout.food_detail_empty_state, parent, false)
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (foods.isEmpty()) {
            return TYPE_EMPTY_STATE
        }
        return TYPE_FOOD
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        if (viewType == TYPE_FOOD && viewHolder is FoodViewHolder) {
            val item = foods[position]

            viewHolder.apply {
                foodIcon.setImageResource(
                        when (categoryTypeFromTitle(categoryTitle)) {
                            CategoryType.FRUIT -> R.drawable.ic_fruit
                            CategoryType.VEGETABLES -> R.drawable.ic_veggie
                            CategoryType.FISH -> R.drawable.ic_seafood
                            CategoryType.MEAT -> R.drawable.ic_meat
                            CategoryType.DRINK -> R.drawable.ic_milk
                            CategoryType.BREAD -> R.drawable.ic_bread
                            CategoryType.DAIRY -> R.drawable.ic_cheese
                            CategoryType.MEAL -> R.drawable.ic_meal
                            CategoryType.UNKNOWN -> R.drawable.ic_unknown_food
                        })
                foodTitle.text = item.name
                when (item.expirationState()) {
                    ExpirationState.SOON -> {
                        expirationWarning.visibility = View.VISIBLE
                        expirationWarning.text = context.getString(R.string.expiredSoon)
                    }
                    ExpirationState.EXPIRED -> {
                        expirationWarning.visibility = View.VISIBLE
                        expirationWarning.text = context.getString(R.string.expired)
                    }
                    ExpirationState.NOT_EXPIRED -> expirationWarning.visibility = View.GONE
                }

                val expirationDateString = LocalDateTime.ofInstant(Instant.ofEpochMilli(item.expiryDate), ZoneId.systemDefault()).format(DateTimeFormatter.ISO_DATE)
                expirationDate.text = context.getString(R.string.expiry_date, expirationDateString)
            }.bindListeners()
        }
    }

    override fun getItemCount() = if (foods.isEmpty()) 1 else foods.size

    fun getItem(position: Int): Food {
        return foods[position]
    }

    companion object {
        const val TYPE_EMPTY_STATE = 1
        const val TYPE_FOOD = 2
    }
}



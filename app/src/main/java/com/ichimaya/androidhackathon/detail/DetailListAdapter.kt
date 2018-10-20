package com.ichimaya.androidhackathon.detail

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.model.ExpirationState
import com.ichimaya.androidhackathon.food.model.Food
import com.ichimaya.androidhackathon.food.model.expirationState
import kotlinx.android.synthetic.main.food_detail_list_item.view.*
import org.threeten.bp.Instant
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId
import org.threeten.bp.format.DateTimeFormatter


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
                        when (categoryTitle) { // TODO make an enum or sealed class for this
                            "Fruit" -> R.drawable.ic_fruit
                            "Vegetable" -> R.drawable.ic_veggie
                            "Fish" -> R.drawable.ic_seafood
                            "Meat" -> R.drawable.ic_meat
                            "Drink" -> R.drawable.ic_milk
                            "Bread" -> R.drawable.ic_bread
                            "Dairy" -> R.drawable.ic_cheese
                            "Meal" -> R.drawable.ic_meal
                            "Unknown" -> R.drawable.ic_unknown_food
                            else -> R.drawable.ic_unknown_food
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
                expirationDate.text = "Expires $expirationDateString"
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



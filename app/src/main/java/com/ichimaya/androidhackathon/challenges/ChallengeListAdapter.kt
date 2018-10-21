package com.ichimaya.androidhackathon.challenges

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.model.Food
import kotlinx.android.synthetic.main.challenge_list_item.view.*

/**
 * Adapter for the RecyclerView that displays food in categories
 */

typealias ClickListener = (Int, Challenge) -> Unit

class ChallengeListAdapter(
    private val onClickListener: ClickListener
) : RecyclerView.Adapter<ChallengeListAdapter.ViewHolder>() {
    private lateinit var context: Context

    var challenges: List<Challenge> = listOf()

    fun updateChallenges(challenges: List<Challenge>) {
        this.challenges = challenges
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val title: TextView = itemView.challenge_title
        val icon: ImageView = itemView.challenge_badge
        val description: TextView = itemView.challenge_subtext

        override fun onClick(view: View) {
            onClickListener(adapterPosition, getItem(adapterPosition))
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        return ViewHolder(
            inflater.inflate(R.layout.challenge_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = challenges[position]

        viewHolder.apply {
            icon.setImageResource(item.badge)
            title.text = item.title
            description.text = item.description
        }


    }

    override fun getItemCount() = challenges.size

    fun getItem(position: Int): Challenge {
        return challenges[position]
    }

}

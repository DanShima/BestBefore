package com.ichimaya.androidhackathon.badges

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.badges.model.Badge
import kotlinx.android.synthetic.main.badge_list_item.view.*


/**
 * Adapter for the RecyclerView that displays badges
 */

class BadgeListAdapter : RecyclerView.Adapter<BadgeListAdapter.ViewHolder>() {
    private lateinit var context: Context

    var badges: List<Badge> = listOf()

    fun updateBadges(badges: List<Badge>) {
        this.badges = badges
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val badgeIcon: ImageView = itemView.logo_badge
        val badgeTitle: TextView = itemView.title_badge
        val badgeDescription: TextView = itemView.description_badge
    }

    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): ViewHolder {
        context = parent.context
        val inflater = LayoutInflater.from(context)
        return ViewHolder(
                inflater.inflate(R.layout.badge_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = badges[position]

        viewHolder.apply {
            badgeIcon.setImageResource(android.R.drawable.star_big_on)
            badgeTitle.text = item.name
            badgeDescription.text = item.description
        }

    }

    override fun getItemCount() = badges.size

    fun getItem(position: Int): Badge {
        return badges[position]
    }

}

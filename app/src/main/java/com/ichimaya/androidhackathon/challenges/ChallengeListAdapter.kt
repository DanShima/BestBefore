package com.ichimaya.androidhackathon.challenges

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ichimaya.androidhackathon.R
import kotlinx.android.synthetic.main.challenge_list_item.view.*

/**
 * Adapter for the RecyclerView that displays a list of challenges
 */

typealias ClickListener = (Int) -> Unit

class ChallengeListAdapter(
    private val onClickListener: ClickListener
) : RecyclerView.Adapter<ChallengeListAdapter.ViewHolder>() {
    private lateinit var context: Context
    var challenges: List<Challenge> = listOf()
    var challengeStateMap: Map<Challenge, ChallengeViewModel.ChallengeState> = mapOf()

    fun updateChallenges(challenges: List<Challenge>, challengeStateMap: Map<Challenge, ChallengeViewModel.ChallengeState>) {
        this.challenges = challenges
        this.challengeStateMap = challengeStateMap
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }
        val title: TextView = itemView.challenge_title
        val icon: ImageView = itemView.challenge_badge
        val description: TextView = itemView.challenge_subtext
        val challengeState: TextView = itemView.challenge_state


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
            inflater.inflate(R.layout.challenge_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val item = challenges[position]
        val state = challengeStateMap[item]

        viewHolder.apply {
            icon.setImageResource(item.badge)
            title.text = item.title
            description.text = item.description
            challengeState.text = when (state) {
                ChallengeViewModel.ChallengeState.NOT_STARTED -> "Take the challenge!"
                ChallengeViewModel.ChallengeState.SUCCEEDED -> "You did it!"
                ChallengeViewModel.ChallengeState.STARTED -> "Challenge in progress"
                ChallengeViewModel.ChallengeState.FAILED -> "You failed :("
                null -> ""
            }
        }


    }

    override fun getItemCount() = challenges.size

    fun getItem(position: Int): Challenge {
        return challenges[position]
    }

}

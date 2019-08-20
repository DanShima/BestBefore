package com.ichimaya.androidhackathon.badges

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.ichimaya.androidhackathon.R
import kotlinx.android.synthetic.main.fragment_badges.*

/**
 * Fragment that is used to show user badges/achievements
 */
class BadgeFragment : Fragment() {

    private lateinit var badgeListAdapter: BadgeListAdapter
    private lateinit var badgeViewModel: BadgeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        badgeViewModel = ViewModelProviders.of(this).get(BadgeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_badges, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDetailRecyclerView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initDetailRecyclerView() {
        badgeListAdapter = BadgeListAdapter()
        
        badgeViewModel.observeBadges(activity!!).observeForever { badges -> badges?.let {
            // show empty view if there are no badges/achievements
            val emptyViewVisibility = if (badges.isEmpty()) View.VISIBLE else View.GONE
            empty_view_badge?.visibility = emptyViewVisibility

            badgeListAdapter.updateBadges(it) }
        }
        badge_recyclerview.apply {
            adapter = badgeListAdapter
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): BadgeFragment {
            return BadgeFragment()
        }
    }
}

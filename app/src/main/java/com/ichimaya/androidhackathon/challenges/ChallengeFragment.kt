package com.ichimaya.androidhackathon.challenges

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.utils.showDividerBetweenListItems
import kotlinx.android.synthetic.main.fragment_challenge.*
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * Shows a list of available challenges for fighting food waste.
 */
class ChallengeFragment : Fragment() {

    private lateinit var challengeListAdapter: ChallengeListAdapter
    private lateinit var challengeViewModel: ChallengeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        challengeViewModel = ViewModelProviders.of(this).get(ChallengeViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_challenge, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView() {
        challengeListAdapter = ChallengeListAdapter(this::onChallengeSelected, challengeViewModel.setupChallengeList())
        challenge_recyclerview.apply {
            adapter = challengeListAdapter
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
            showDividerBetweenListItems()
        }
    }

    private fun onChallengeSelected(position: Int) {
        Log.d("TestMe", "$position")
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("Start this challenge? \n You have ${challengeListAdapter.getItem(position).challengeLength} days to complete it.")
            .setTitle(challengeListAdapter.getItem(position).title)
            .setCancelable(true)
            .setPositiveButton("Start") { _, _ -> startChallenge(challengeListAdapter.getItem(position)) }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    private fun startChallenge(challenge: Challenge) {
        Log.d("TestMe", "START COUNTING HERE")
    }

    companion object {

        @JvmStatic
        fun newInstance(): ChallengeFragment {
            return ChallengeFragment().apply {
                arguments = Bundle().apply {
                    // Nothing to add yet
                }
            }
        }
    }
}
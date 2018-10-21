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
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initRecyclerView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initRecyclerView() {
        challengeListAdapter = ChallengeListAdapter(this::startChallenge)
        detail_recyclerview.apply {
            adapter = challengeListAdapter
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
            showDividerBetweenListItems()
        }
        challengeListAdapter.updateChallenges(challengeViewModel.setupChallengeList())
    }

    private fun startChallenge(position: Int) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Start this challenge")
            .setTitle("bla")
            .setCancelable(true)
            .setPositiveButton("Start") { _, _ -> TODO() }
            .setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
        val messageView = alert.findViewById<View>(android.R.id.message) as TextView
        messageView.gravity = Gravity.CENTER
        messageView.setTextIsSelectable(true)
        Log.d("TestMe", "$position")
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
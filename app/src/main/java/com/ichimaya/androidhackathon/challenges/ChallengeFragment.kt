package com.ichimaya.androidhackathon.challenges

import android.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.utils.showDividerBetweenListItems
import kotlinx.android.synthetic.main.fragment_challenge.*

/**
 * Shows a list of available challenges for fighting food waste.
 */
class ChallengeFragment : Fragment() {

    private var challengePrefs = "challengePrefs"
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var challengeListAdapter: ChallengeListAdapter
    private lateinit var challengeViewModel: ChallengeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = context!!.getSharedPreferences(challengePrefs, Context.MODE_PRIVATE)
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
        challengeListAdapter = ChallengeListAdapter(this::onChallengeSelected)
        challengeViewModel.observeChallenges(activity!!).observeForever {
            val keys = it?.keys
            challengeListAdapter.updateChallenges(keys?.toList() ?: listOf(), it ?: mapOf())
        }

        challenge_recyclerview.apply {
            adapter = challengeListAdapter
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
            showDividerBetweenListItems()
        }
    }


    /**
     * Show a dialog that allows user to start a challenge
     */
    private fun onChallengeSelected(position: Int) {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage(this.getString(R.string.dialog_start_challenge_message, challengeListAdapter.getItem(position).challengeLength))
            .setTitle(challengeListAdapter.getItem(position).title)
            .setCancelable(true)
            .setPositiveButton(this.getString(R.string.dialog_button_start)) { _, _ -> startChallenge(challengeListAdapter.getItem(position)) }
            .setNegativeButton(this.getString(R.string.dialog_button_cancel)) { dialog, _ -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }

    /**
     * Start counting the time left to complete a challenge
     */
    private fun startChallenge(challenge: Challenge) {
        val editor = sharedPreferences.edit()
        editor.putLong(challenge.title, System.currentTimeMillis())
        editor.apply()
        initRecyclerView() // this is horrible! we should observe the preference instead, but time is a factor right now :|

        Toast.makeText(context, this.getString(R.string.toast_challenge_started, challenge.title), Toast.LENGTH_LONG).show()
    }

    companion object {

        @JvmStatic
        fun newInstance(): ChallengeFragment = ChallengeFragment()
    }
}
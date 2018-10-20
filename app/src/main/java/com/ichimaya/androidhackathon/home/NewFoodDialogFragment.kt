package com.ichimaya.androidhackathon.home

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ichimaya.androidhackathon.R

/**
 * Created by Marika Driman on 2018-10-20.
 */

class NewFoodDialogFragment : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_dialog_new_food, container)

        this.dialog.setTitle("Add New Food Item")

        return rootView
    }
}

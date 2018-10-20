package com.ichimaya.androidhackathon.detail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.recyclerview.R.attr.layoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.home.GridListAdapter
import kotlinx.android.synthetic.main.food_detail_list_item.*
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * Fragment that is used to show the main categories in the Home screen.
 */
class DetailFragment : Fragment() {

    private lateinit var categoryAdapter: DetailListAdapter
    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailViewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initDetailRecyclerView()
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initDetailRecyclerView() {
        categoryAdapter = DetailListAdapter(this::openItem, detailViewModel.testtest())
        detail_recyclerview.apply {
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    private fun openItem(position: Int) {
        Log.d("TestBlabla", "Baka $position")
    }

    companion object {
        val TAG: String = DetailFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    // nothing to see here, move on.
                }
            }
        }
    }
}

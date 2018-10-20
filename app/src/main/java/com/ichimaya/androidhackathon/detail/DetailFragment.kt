package com.ichimaya.androidhackathon.detail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.home.GridListAdapter
import kotlinx.android.synthetic.main.fragment_categories.*

/**
 * Fragment that is used to show the main categories in the Home screen.
 */
class DetailFragment : Fragment() {

    private lateinit var categoryAdapter: GridListAdapter
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
        categoryAdapter = GridListAdapter(this::openItem, )
        category_recyclerview.apply {
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

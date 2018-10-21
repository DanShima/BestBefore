package com.ichimaya.androidhackathon.detail

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.model.Food
import kotlinx.android.synthetic.main.fragment_detail.*

/**
 * Fragment that is used to show foods for a specific category.
 */
class DetailFragment : Fragment() {

    private lateinit var detailListAdapter: DetailListAdapter
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var categoryTitle: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryTitle = arguments!!.getString(CATEGORY_TITLE)!! // should fail spectacularly if argument is not provided
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
        detailListAdapter = DetailListAdapter(categoryTitle, this::openItem, this::markItemAsConsumed)
        detailViewModel.observeFoods(activity!!, categoryTitle).observeForever { foods ->
            detailListAdapter.updateFoods(foods ?: listOf()) }
        detail_recyclerview.apply {
            adapter = detailListAdapter
            layoutManager = LinearLayoutManager(activity).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
            setHasFixedSize(true)
        }
    }

    private fun markItemAsConsumed(checked: Boolean, food: Food) {
        if (checked) {
            Toast.makeText(activity, "Awesomesauce! You consumed ${food.name}", Toast.LENGTH_LONG).apply {
                setGravity(Gravity.CENTER, 0, 0)
            }.show()
            detailViewModel.markAsConsumed(activity!!, food)
        }
    }

    private fun openItem(position: Int) {
        Log.d("TestBlabla", "Baka $position")
    }

    companion object {
        const val CATEGORY_TITLE = "category-title"

        @JvmStatic
        fun newInstance(categoryTitle: String): DetailFragment {
            return DetailFragment().apply {
                arguments = Bundle().apply {
                    putString(CATEGORY_TITLE, categoryTitle)
                }
            }
        }
    }
}

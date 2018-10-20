package com.ichimaya.androidhackathon.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ichimaya.androidhackathon.R
import kotlinx.android.synthetic.main.fragment_categories.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class CategoryFragment : Fragment() {

    private lateinit var categoryAdapter: GridListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        initCategoryRecyclerView()
        super.onActivityCreated(savedInstanceState)
    }



    private fun initCategoryRecyclerView() {
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_view_item_spacing)
        categoryAdapter = GridListAdapter(this::openCatgory)
        category_recyclerview.apply {
            layoutManager = GridLayoutManager(activity, 3)
            addItemDecoration(SpacesItemDecorator(spacingInPixels))
            adapter = categoryAdapter
        }
    }

    fun openCatgory(position: Int) {
        Log.d("Test", "Baka")
    }

    companion object {
        val TAG: String = CategoryFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): CategoryFragment {
            return CategoryFragment().apply {
                arguments = Bundle().apply {
                    // nothing for now
                }
            }
        }
    }



}

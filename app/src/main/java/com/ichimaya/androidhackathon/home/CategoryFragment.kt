package com.ichimaya.androidhackathon.home


import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ichimaya.androidhackathon.R
import com.ichimaya.androidhackathon.food.model.Category
import kotlinx.android.synthetic.main.fragment_categories.*


/**
 * Fragment that is used to show the main categories in the Home screen.
 */
class CategoryFragment : Fragment() {

    private lateinit var categoryAdapter: GridListAdapter
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var onCategoryClickListener: OnCategoryClickListener

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onCategoryClickListener = context as OnCategoryClickListener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initCategoryRecyclerView()
        fab_add.setOnClickListener {
            val newFoodDialogFragment = NewFoodDialogFragment()
            newFoodDialogFragment.setStyle(DialogFragment.STYLE_NORMAL, R.style.CustomDialog)
            newFoodDialogFragment.show(fragmentManager, "NewFoodDialogFragment_Tag")
        }
        super.onViewCreated(view, savedInstanceState)
    }

    private fun initCategoryRecyclerView() {
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.grid_view_item_spacing)
        categoryAdapter = GridListAdapter(this::openCategory, categoryViewModel.setupCategoryList())
        category_recyclerview.apply {
            layoutManager = GridLayoutManager(activity, 3)
            addItemDecoration(SpacesItemDecorator(spacingInPixels))
            adapter = categoryAdapter
        }
    }

    private fun openCategory(position: Int) {
        val selectedCategory = categoryAdapter.getItem(position)
        onCategoryClickListener.onCategoryClicked(position, selectedCategory)
    }

    interface OnCategoryClickListener {
        fun onCategoryClicked(position: Int, category: Category)
    }

    companion object {
        val TAG: String = CategoryFragment::class.java.simpleName

        @JvmStatic
        fun newInstance(): CategoryFragment {
            return CategoryFragment().apply {
                arguments = Bundle().apply {
                    // nothing to see here, move on.
                }
            }
        }
    }
}

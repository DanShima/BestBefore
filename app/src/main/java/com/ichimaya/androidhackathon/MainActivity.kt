package com.ichimaya.androidhackathon

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.ichimaya.androidhackathon.badges.BadgeFragment
import com.ichimaya.androidhackathon.challenges.ChallengeFragment
import com.ichimaya.androidhackathon.detail.DetailFragment
import com.ichimaya.androidhackathon.food.model.Category
import com.ichimaya.androidhackathon.home.CategoryFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), CategoryFragment.OnCategoryClickListener {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        // Show categories as default home view
        replaceFragmentInActivity(CategoryFragment.newInstance(), R.id.mainActivityFrameHolder)
        setupBottomNavigation()
    }

    private fun setupBottomNavigation() {
        navigationView.setOnNavigationItemSelectedListener { item ->
            when {
                item.itemId == R.id.home -> replaceFragmentInActivity(CategoryFragment.newInstance(), R.id.mainActivityFrameHolder)
                item.itemId == R.id.badges -> replaceFragmentInActivity(BadgeFragment.newInstance(), R.id.mainActivityFrameHolder)
                item.itemId == R.id.challenges -> replaceFragmentInActivity(ChallengeFragment.newInstance(), R.id.mainActivityFrameHolder)
            }
            true
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar_main_layout)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_camera) {
            val intent = Intent(this, ARActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCategoryClicked(position: Int, category: Category) {
        val detailFragment = DetailFragment.newInstance(category.title)
        replaceFragmentInActivity(detailFragment, R.id.mainActivityFrameHolder)
    }

    /**
     * Use this extension function to replace an existing fragment in the MainActivity
     */
    private fun AppCompatActivity.replaceFragmentInActivity(fragment: Fragment, frameId: Int, addToBackstack: Boolean = false) {
        supportFragmentManager.transact {
            if (addToBackstack) {
                this.addToBackStack(fragment::class.java.simpleName)
            }
            replace(frameId, fragment, fragment::class.java.name)
        }
    }

    private inline fun FragmentManager.transact(action: FragmentTransaction.() -> FragmentTransaction) {
        beginTransaction().action().commit()
    }
}

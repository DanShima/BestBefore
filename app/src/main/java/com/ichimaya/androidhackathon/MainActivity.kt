package com.ichimaya.androidhackathon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.ichimaya.androidhackathon.home.CategoryFragment
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.ActionBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    lateinit var toolbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupToolbar()
        navigationView.setOnNavigationItemSelectedListener { item ->
            when {
                item.itemId == R.id.home -> replaceFragmentInActivity(CategoryFragment.newInstance(), R.id.mainActivityFrameHolder)
                item.itemId == R.id.badges -> {
                    // TODO
                }
                item.itemId == R.id.challenges -> {
                    // TODO
                }
            }
            true
        }
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar_main_layout)
        supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    /**
     * Use this extension function to replace existing fragment in the MainActivity
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

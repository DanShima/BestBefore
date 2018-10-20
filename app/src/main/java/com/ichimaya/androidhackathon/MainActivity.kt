package com.ichimaya.androidhackathon

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import com.ichimaya.androidhackathon.home.CategoryFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val categoryFragment = CategoryFragment.newInstance()
        replaceFragmentInActivity(categoryFragment, R.id.mainActivityFrameHolder)
    }

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

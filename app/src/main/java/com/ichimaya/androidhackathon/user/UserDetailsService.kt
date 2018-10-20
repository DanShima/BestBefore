package com.ichimaya.androidhackathon.user

import android.content.Context
import android.preference.PreferenceManager
import java.util.*

class UserDetailsService {
    fun getUUID(context: Context): String {
        val preferenceManager = PreferenceManager.getDefaultSharedPreferences(context)
        return if (preferenceManager.contains(KEY_UUID)) {
            preferenceManager.getString(KEY_UUID, null)!!
        } else {
            val uuid = UUID.randomUUID().toString()
            preferenceManager.edit().putString(KEY_UUID, uuid).apply()
            uuid
        }
    }

    companion object {
        const val KEY_UUID = "UUID"
    }
}
package com.ichimaya.androidhackathon.badges

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import android.content.Context
import com.ichimaya.androidhackathon.badges.model.Badge
import com.ichimaya.androidhackathon.user.UserDetailsService

class BadgeViewModel : ViewModel() {
    fun observeBadges(context: Context): LiveData<List<Badge>> {
        val badgeRepository = BadgeRepository(UserDetailsService().getUUID(context))
        badgeRepository.updateBadges()
        return badgeRepository.observeBadges()
    }
}
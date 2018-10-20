package com.ichimaya.androidhackathon.badges

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.ichimaya.androidhackathon.badges.model.Badge


class BadgeRepository {

    var badges: MutableLiveData<List<Badge>> = MutableLiveData()

    fun observeBadges(): LiveData<List<Badge>> {
        if (badges.value == null) {
            FirebaseDatabase.getInstance()
                    .getReference("badges")
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                badges.postValue(dataSnapshot.children.mapNotNull {
                                    val food = dataSnapshot.child(it.key!!)
                                    Badge(
                                            id = food.child("id").getValue<String>(String::class.java) ?: return,
                                            name = food.child("name").getValue<String>(String::class.java) ?: return,
                                            achieveDate = food.child("achieveDate").getValue<Long>(Long::class.java) ?: 0L
                                    )
                                })
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // TODO
                        }
                    })
        }
        return badges
    }

    fun registerAchievement(badge: Badge) {
        FirebaseDatabase.getInstance()
                .getReference("badges")
                .updateChildren(mapOf(badge.id to badge))
    }

}
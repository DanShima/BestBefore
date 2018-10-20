package com.ichimaya.androidhackathon.notifications

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat.getSystemService
import com.ichimaya.androidhackathon.MainActivity
import com.ichimaya.androidhackathon.food.model.Food
import com.ichimaya.androidhackathon.notifications.NotificationPublisher.Companion.CHANNEL_ID
import com.ichimaya.androidhackathon.notifications.NotificationPublisher.Companion.TWO_HOURS


class NotificationHandler {
    /**
     * @param timeBefore How long before the expiry date the notification should trigger. Defaults to two hours.
     */
    fun scheduleNotification(context: Context,
                             food: Food,
                             timeBefore: Int = TWO_HOURS) {
        val name = "Best before"
        val channel = NotificationChannel(CHANNEL_ID, name, NotificationManager.IMPORTANCE_DEFAULT)

        val notificationManager: NotificationManager = getSystemService(context, NotificationManager::class.java)!!
        notificationManager.createNotificationChannel(channel)

        val pendingIntent = getPendingIntent(context, food)

        val notificationTime = food.expiryDate - timeBefore

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP, notificationTime, pendingIntent)
    }

    fun cancelNotification(context: Context, food: Food) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(getPendingIntent(context, food))
    }

    private fun getPendingIntent(context: Context, food: Food): PendingIntent? {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("${food.name} is about to expire!")
                .setContentText("Eat it before it goes bad.")
                .setSmallIcon(android.R.drawable.stat_notify_chat)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))

        val intent = Intent(context, MainActivity::class.java)
        val notificationId = food.id.hashCode()
        val activity = PendingIntent.getActivity(context, notificationId, intent, PendingIntent.FLAG_CANCEL_CURRENT)
        builder.setContentIntent(activity)

        val notification = builder.build()
        val notificationIntent = Intent(context, NotificationPublisher::class.java)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION_ID, notificationId)
        notificationIntent.putExtra(NotificationPublisher.NOTIFICATION, notification)
        return PendingIntent.getBroadcast(context, notificationId, notificationIntent, PendingIntent.FLAG_CANCEL_CURRENT)
    }
}

class NotificationPublisher : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        with(NotificationManagerCompat.from(context)) {
            val notification = intent.getParcelableExtra<Notification>(NOTIFICATION)
            notify(intent.getIntExtra(NOTIFICATION_ID, 0), notification)
        }
    }

    companion object {
        const val NOTIFICATION_ID = "notification-id"
        const val NOTIFICATION = "notification"
        const val CHANNEL_ID = "bestbefore"
        const val TWO_HOURS = 1000 * 60 * 60 * 2
    }

}
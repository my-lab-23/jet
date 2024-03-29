package com.example.bibbia

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import java.text.SimpleDateFormat
import java.util.*

class MyAlarm: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        try {
            val currentTime = Calendar.getInstance().time
            val simpleDateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
            val currentTimeFormatted = simpleDateFormat.format(currentTime)
            showNotification(context, currentTimeFormatted, "Ricordati di leggere un versetto.")
        } catch (ex: Exception) {
            Log.d("Receive Ex", "onReceive: ${ex.printStackTrace()}")
        }
    }
}

private fun showNotification(context: Context, title: String, desc: String) {
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    val channelId = "message_channel"
    val channelName = "message_name"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
    }

    val intent = Intent(context, MainActivity::class.java)
    val stackBuilder = TaskStackBuilder.create(context)
    stackBuilder.addNextIntentWithParentStack(intent)
    val pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)

    val builder = NotificationCompat.Builder(context, channelId)
        .setContentTitle(title)
        .setContentText(desc)
        .setSmallIcon(R.drawable.ic_outline_notifications_active_24)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)

    manager.notify(1, builder.build())
}

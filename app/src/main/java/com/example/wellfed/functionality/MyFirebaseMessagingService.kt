package com.example.wellfed.functionality

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.wellfed.R
import com.example.wellfed.UI.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val channelId = "notification_channel"
const val channelName = "com.example.wellfed"
class MyFirebaseMessagingService : FirebaseMessagingService() {


    override fun onMessageReceived(message: RemoteMessage) {

        if(message.notification != null) {
            generateNotification(message.notification!!.title!!, message.notification!!.body!!)
        }
    }

    @SuppressLint("RemoteViewLayout")
    fun getRemoteView(title  :String, description : String) : RemoteViews {
        val remoteView = RemoteViews("com.example.wellfed", R.layout.notification)
        remoteView.setTextViewText(R.id.notificationTitle, title)
        remoteView.setTextViewText(R.id.notificationDescription, description)
        remoteView.setImageViewResource(R.id.notificationLogo, R.mipmap.ic_launcher)

        return remoteView
    }

    fun generateNotification(title: String, description: String) {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoteView(title, description))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }

        notificationManager.notify(0, builder.build())

    }


}
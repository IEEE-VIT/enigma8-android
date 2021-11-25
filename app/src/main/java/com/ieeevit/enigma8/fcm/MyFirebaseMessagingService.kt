package com.ieeevit.enigma8.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.nfc.Tag
import android.os.Build
import android.provider.Settings.Global.getString
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.google.android.play.core.internal.m
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.ieeevit.enigma8.MainActivity
import com.ieeevit.enigma8.R
import com.ieeevit.enigma8.view.rooms.RoomsActvity
import java.util.Calendar.getInstance

class MyFirebaseMessagingService:FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)


        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty())
        {
            val title = remoteMessage.data["title"]
            val body = remoteMessage.data["body"]
            sendNotification(body.toString())
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

        }
        else
        {
            val title = remoteMessage.notification!!.title
            val body = remoteMessage.notification!!.body
            sendNotification(body.toString())

        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message com.ieeevit.enigma8.model.notification.Notification Body: ${it.body}")
        }


    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")


        sendRegistrationToServer(token)
    }






    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }



    private fun sendRegistrationToServer(token: String?) {

        Log.d(TAG, "sendRegistrationTokenToServer($token)")
    }


    private fun sendNotification(messageBody: String) {
        val intent = Intent(this, RoomsActvity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)

        val channelId = getString(R.string.default_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
                .setSmallIcon(R.drawable.enigma_logo)
                .setContentTitle("Enigma8")
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }




    companion object {

        private const val TAG = "MyFirebaseMsgService"
    }


}
package com.example.chaty.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.chaty.R
import com.example.chaty.firebase.MyFirebase.mAuth
import com.example.chaty.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlin.random.Random

private const val CHANNEL_ID = "my-channel"
private const val TAG = "mostafa"

class FirebaseService : FirebaseMessagingService() {

    companion object {
        var sharedPref: SharedPreferences? = null
        var token: String?
            get() {
                return sharedPref?.getString("token", "")
            }
            set(value) {
                sharedPref?.edit()?.putString("token", value)?.apply()
            }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if (mAuth.currentUser != null) {
            message.data["title"]?.let {
                val info = it.split("-")
                val title=info[0]
                if (mAuth.currentUser!!.uid != info[1]) {
                    val intent = Intent(this, MainActivity::class.java)
                        .apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) }
                    val notificationManager =
                        getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                    val notificationID = Random.nextInt()

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        createNotificationChannel(notificationManager)
                    }

                    val pendingIntent =
                        PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)
                    val notification = NotificationCompat.Builder(this, CHANNEL_ID)
                        .setContentTitle(title)
                        .setContentText(message.data["message"])
                        .setSmallIcon(R.drawable.icon_message)
                        .setColor(resources.getColor(R.color.primary, null))
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build()
                    notificationManager.notify(notificationID, notification)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channelName = "channelName"
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "My channel description"
                enableLights(true)
                lightColor = Color.GREEN
            }
        notificationManager.createNotificationChannel(channel)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        saveIntoSharedRef(token);
    }

    private fun saveIntoSharedRef(_token: String) {
        token = _token
    }
}
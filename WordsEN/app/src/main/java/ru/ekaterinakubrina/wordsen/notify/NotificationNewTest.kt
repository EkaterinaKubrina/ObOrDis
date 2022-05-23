package ru.ekaterinakubrina.wordsen.notify

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.view.SplashActivity

class NotificationNewTest {

    companion object {
        private val NOTIFICATION_ID = 103
        private val CHANNEL_ID = "channelID"

        fun showNotification(context: Context) {
            val intent1 = Intent(context, SplashActivity::class.java)
            intent1.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.dog)
                .setContentTitle("Еженедельный тест стал доступен!")
                .setContentText("Зайди в свои тесты и проверь, как хорошо ты помнишь слова прошедшей недели")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)

            val notificationManager = NotificationManagerCompat.from(context)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val notificationChannel =
                    NotificationChannel(
                        CHANNEL_ID,
                        CHANNEL_ID,
                        NotificationManager.IMPORTANCE_DEFAULT
                    )
                notificationChannel.enableLights(true)
                notificationManager.createNotificationChannel(notificationChannel)
            }

            with(NotificationManagerCompat.from(context)) {
                notify(NOTIFICATION_ID, builder.build())
            }

        }
    }
}
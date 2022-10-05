package ru.ekaterinakubrina.oneworday.notify

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import ru.ekaterinakubrina.oneworday.R
import ru.ekaterinakubrina.oneworday.mvp.model.Repository
import ru.ekaterinakubrina.oneworday.mvp.view.SplashActivity
import java.text.SimpleDateFormat
import java.util.*

class NotificationRememberWordReceiver : BroadcastReceiver() {
    private val NOTIFICATION_ID = 102
    private val CHANNEL_ID = "channelID"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent?) {
        FirebaseApp.initializeApp(context)
        if (FirebaseAuth.getInstance().currentUser != null) {
            val repository: Repository = Repository.getRepository(context)
            val idUser: String = intent?.getSerializableExtra("ID_USER") as String

            val intent1 = Intent(context, SplashActivity::class.java)
            intent1.apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0)

            val currentDate = SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt()
            val word = repository.getWordByDate(idUser, currentDate)

            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.dog)
                .setContentTitle("Слово дня")
                .setContentText("Еще помнишь как переводится слово " + word.word + "?")
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
                notify(NOTIFICATION_ID, builder.build()) // посылаем уведомление
            }
        }

    }


}
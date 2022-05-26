package ru.ekaterinakubrina.wordsen.notify

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
import ru.ekaterinakubrina.wordsen.R
import ru.ekaterinakubrina.wordsen.daoimpl.DictionaryDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.UserDaoImpl
import ru.ekaterinakubrina.wordsen.daoimpl.WordDaoImpl
import ru.ekaterinakubrina.wordsen.model.DictionaryModel
import ru.ekaterinakubrina.wordsen.model.UsersModel
import ru.ekaterinakubrina.wordsen.model.WordsModel
import ru.ekaterinakubrina.wordsen.view.SplashActivity
import java.text.SimpleDateFormat
import java.util.*


class NotificationNewWordReceiver : BroadcastReceiver() {
    private val NOTIFICATION_ID = 101
    private val CHANNEL_ID = "channelID"

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent?) {
        FirebaseApp.initializeApp(context)
        if (FirebaseAuth.getInstance().currentUser != null) {
            val wordsModel = WordsModel(WordDaoImpl(context))
            val usersModel = UsersModel(UserDaoImpl(context))
            val dictionaryModel = DictionaryModel(DictionaryDaoImpl(context), wordsModel)
            val idUser: String = intent?.getSerializableExtra("ID_USER") as String
            val levelUser: Int = intent.getSerializableExtra("LEVEL_USER") as Int

            val intent1 = Intent(context, SplashActivity::class.java)
            intent1.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0)

            val lastDate = dictionaryModel.getLastDateAddedWord(idUser)
            if (lastDate != SimpleDateFormat("yyyyMMdd", Locale.ENGLISH).format(Date()).toInt()) {
                var word = dictionaryModel.getWord(idUser, levelUser)
                var builder1: NotificationCompat.Builder? = null
                if (word.wordId == 0) {
                    usersModel.setLevelLocalAndFirebase(idUser, levelUser + 1)
                    word = dictionaryModel.getWord(idUser, levelUser + 1)
                    builder1 = NotificationCompat.Builder(context, CHANNEL_ID)
                        .setSmallIcon(R.drawable.dog)
                        .setContentTitle("Новый уровень")
                        .setContentText("Поздравляем, ты переходишь на следующий уровень! Теперь твои слова станут сложнее и интернеснее")
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)
                }
                dictionaryModel.addWordToUser(idUser, word)

                if (dictionaryModel.getCountNewWord(idUser)!! - 1 == 7) {
                    NotificationNewTest.showNotification(context)
                }

                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.dog)
                    .setContentTitle("Новое слово дня:")
                    .setContentText(word.word + " — " + word.translate)
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

                if (builder1 != null) {
                    with(NotificationManagerCompat.from(context)) {
                        notify(NOTIFICATION_ID, builder1.build()) // посылаем уведомление
                    }
                }
                with(NotificationManagerCompat.from(context)) {
                    notify(NOTIFICATION_ID, builder.build())
                }
            } else {
                val word = dictionaryModel.getWordByDate(
                    idUser, SimpleDateFormat(
                        "yyyyMMdd",
                        Locale.ENGLISH
                    ).format(Date()).toInt()
                )

                val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.dog)
                    .setContentTitle("Слово дня:")
                    .setContentText(word.word + " — " + word.translate)
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

}

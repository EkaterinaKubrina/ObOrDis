package ru.ekaterinakubrina.wordsen.notify

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import java.util.*


class NotifyService : Service() {
    private var alarmIntent: PendingIntent? = null
    private var alarmIntent1: PendingIntent? = null
    private var alarmManager: AlarmManager? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val startFlag: Boolean = intent?.getSerializableExtra("START_FLAG") as Boolean
        val idUser: String = intent.getSerializableExtra("ID_USER") as String
        val level: Int = intent.getSerializableExtra("LEVEL_USER") as Int
        if (startFlag) {
            startAlarm(idUser, level)
        } else {
            stopAlarms(idUser, level)
        }
        return START_NOT_STICKY
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun startAlarm(userId: String, level: Int) {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager?

        val intent = Intent(this, NotificationNewWordReceiver::class.java)
        intent.putExtra("ID_USER", userId)
        intent.putExtra("LEVEL_USER", level)
        alarmIntent = PendingIntent.getBroadcast(this, 1, intent, 0)

        val intent1 = Intent(this, NotificationRememberWordReceiver::class.java)
        intent1.putExtra("ID_USER", userId)
        alarmIntent1 = PendingIntent.getBroadcast(this, 2, intent1, 0)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, 10)
        val timeToStart = calendar.timeInMillis

        alarmManager?.setRepeating(
            AlarmManager.RTC, timeToStart,
            AlarmManager.INTERVAL_DAY,
            alarmIntent
        )

        calendar.set(Calendar.HOUR_OF_DAY, 21)

        val timeToStart1 = calendar.timeInMillis
        alarmManager?.setRepeating(
            AlarmManager.RTC, timeToStart1,
            AlarmManager.INTERVAL_DAY,
            alarmIntent1
        )
    }

    private fun stopAlarms(userId: String, level: Int) {
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager?

        val intent = Intent(this, NotificationNewWordReceiver::class.java)
        intent.putExtra("ID_USER", userId)
        intent.putExtra("LEVEL_USER", level)
        alarmIntent = PendingIntent.getBroadcast(this, 1, intent, 0)

        val intent1 = Intent(this, NotificationRememberWordReceiver::class.java)
        intent1.putExtra("ID_USER", userId)
        alarmIntent1 = PendingIntent.getBroadcast(this, 2, intent1, 0)
        if (alarmManager != null) {
            alarmManager!!.cancel(alarmIntent)
            alarmManager!!.cancel(alarmIntent1)
        }
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}
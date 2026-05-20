package com.example.alarmapp

import android.Manifest
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 🔔 Notification Permission (Android 13+)
        if (Build.VERSION.SDK_INT >= 33) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED) {

                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                    1
                )
            }
        }

        val timePicker = findViewById<TimePicker>(R.id.timePicker)
        val btnOn = findViewById<Button>(R.id.btnOn)
        val btnOff = findViewById<Button>(R.id.btnOff)
        val btn10sec = findViewById<Button>(R.id.btn10sec)
        val tvStatus = findViewById<TextView>(R.id.tvStatus)
        val tvTime = findViewById<TextView>(R.id.tvTime)

        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager

        // 🔥 FIXED TIME ALARM
        btnOn.setOnClickListener {

            val hour = timePicker.hour
            val minute = timePicker.minute

            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)

            if (calendar.timeInMillis <= System.currentTimeMillis()) {
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            val intent = Intent(this, MyReceiver::class.java)

            pendingIntent = PendingIntent.getBroadcast(
                this,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent
            )

            // 🔥 Show time on top
            tvTime.text = String.format("%02d:%02d", hour, minute)

            tvStatus.text = "Alarm set for selected time"
        }

        // 🔥 OFF BUTTON
        btnOff.setOnClickListener {
            alarmManager.cancel(pendingIntent)
            tvStatus.text = "Alarm OFF"
        }

        // 🔥 10 SECOND ALARM
        btn10sec.setOnClickListener {

            val intent = Intent(this, MyReceiver::class.java)

            val pendingIntent10 = PendingIntent.getBroadcast(
                this,
                1,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
            )

            val time = System.currentTimeMillis() + 10000

            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                time,
                pendingIntent10
            )

            tvStatus.text = "Alarm in 10 seconds"
        }
    }
}
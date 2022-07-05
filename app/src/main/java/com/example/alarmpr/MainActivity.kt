package com.example.alarmpr

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import androidx.databinding.DataBindingUtil
import com.example.alarmpr.databinding.ActivityMainBinding
import java.text.DateFormat
import java.util.*

class MainActivity : AppCompatActivity(),  TimePickerDialog.OnTimeSetListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.timeBtn.setOnClickListener {

            var timePicker = TimePickerFragment()
            timePicker.show(supportFragmentManager, "Time Picker")

        }
        binding.alarmCancelBtn.setOnClickListener{
            cancleAlarm()

        }

    }

    override fun onTimeSet(p0: TimePicker?, hourofDay: Int, minute: Int) {
        var c = Calendar.getInstance()

        c.set(Calendar.HOUR_OF_DAY, hourofDay)
        c.set(Calendar.MINUTE, minute)
        c.set(Calendar.SECOND, 0)

        updateTimeText(c)

        startAlarm(c)
    }
    private fun updateTimeText(c: Calendar){
        var curTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)

        binding.timeText.append("알람 시간 : ")
        binding.timeText.append(curTime)
    }
    private fun startAlarm(c: Calendar){
        var alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(this, AlertReceiver::class.java)

        var curTime = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)
        intent.putExtra("time",curTime)


        var pendingIntent = PendingIntent.getBroadcast(this,1,intent,0)
        if(c.before(Calendar.getInstance())){
            c.add(Calendar.DATE,1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)
    }
    private  fun cancleAlarm(){
        var alarmManager: AlarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        var intent = Intent(this, AlertReceiver::class.java)
        var pendingIntent = PendingIntent.getBroadcast(this,1,intent,0)
        alarmManager.cancel(pendingIntent)
        binding.timeText.text = "알람 취소"
    }
}
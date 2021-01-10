package com.example.wakeup;

import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.IBinder;

import androidx.annotation.Nullable;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service {
    private Integer alarmHour;
    private Integer alarmMinute;
    private Ringtone ringtone;
    private Timer t = new Timer();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        alarmHour = intent.getIntExtra("alarmHour", 0);
        alarmMinute = intent.getIntExtra("alarmMinute", 0);

        ringtone = RingtoneManager.getRingtone(getApplicationContext(), RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (Calendar.getInstance().getTime().getHours() == alarmHour &&
                        Calendar.getInstance().getTime().getMinutes() == alarmMinute) {
                    ringtone.play();
                } else {
                    ringtone.stop();
                }

            }
        }, 0, 2000);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        ringtone.stop();
        t.cancel();
        super.onDestroy();
    }
}



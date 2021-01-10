package com.example.wakeup;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;


public class MyService extends Service {
    public Integer alarmHour;
    public Integer alarmMinute;
    private Ringtone ringtone;
    private Timer t = new Timer();

    private static final String CHANNEL_ID = "MyNotificationChannelID";

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

        try {
            Intent notificationIntent = new Intent(this, MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("My Alarm clock")
                    .setContentText("Wake up at " + alarmHour.toString() + ":" + alarmMinute.toString())
                    .setSmallIcon(R.drawable.littleguy2)
                    .setContentIntent(pendingIntent)
                    .build();



            startForeground(1, notification);

            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "My Alarm clock Sevice", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);

        } catch (Exception e) {
            e.printStackTrace();
        }

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



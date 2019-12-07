package com.hosnydev.quran.notification;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.hosnydev.quran.R;
import com.hosnydev.quran.data.QuranList;
import com.hosnydev.quran.data.QuranListName;
import com.hosnydev.quran.ui.DisplayNotificationActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class Alarm extends BroadcastReceiver {

    private boolean state;
    private int time;
    private int positionName;
    private int srcFile;

    @Override
    public void onReceive(Context context, Intent intent) {

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, context.getPackageName());
        wl.acquire(60 * 1000);

        getShared(context);

        if (state) {
            push(context);
        }
        wl.release();

    }


    private void push(Context context) {

        //get List of quran name
        List<String> listQuranName = new ArrayList<>();
        listQuranName = new QuranListName(context, listQuranName).getList();

        //get list for contains quran
        List<String> listQuran = new ArrayList<>();
        QuranList quranList = new QuranList(context, listQuran);
        listQuran = quranList.getList(quranList.getFile(0));

        if (positionName >= listQuran.size()) {

            srcFile++;

            positionName = 0;
            saveShared(context, positionName, srcFile);
        }

        listQuran.clear();
        listQuranName.clear();

        listQuranName = new QuranListName(context, listQuranName).getList();
        listQuran = quranList.getList(quranList.getFile(0));

        pushNotification(context, listQuran.get(positionName), listQuranName.get(srcFile));

        positionName++;
        saveShared(context, positionName, srcFile);


    }

    private void saveShared(Context context, int po, int src) {
        context.getSharedPreferences("notification", MODE_PRIVATE)
                .edit()
                .putInt("srcFile", src)
                .putInt("positionName", po)
                .apply();
    }

    private void getShared(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("notification", MODE_PRIVATE);
        state = preferences.getBoolean("state", false);
        time = preferences.getInt("time", 0);
        positionName = preferences.getInt("positionName", 0);
        srcFile = preferences.getInt("srcFile", 0);

    }


    public void setAlarm(Context context) {

        int TIME;
        if (time == 0) {
            //15 m
            TIME = 15 * 60 * 1000;
        } else if (time == 1) {
            // 30 m
            TIME = 30 * 60 * 1000;
        } else if (time == 2) {
            // 1 h
            TIME = 60 * 60 * 1000;
        } else {
            // 2 h
            TIME = 120 * 60 * 1000;
        }

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), TIME, pi); // Millisec * Second * Minute
        // am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60 * 1000, pi); // Millisec * Second * Minute
    }

    public void cancelAlarm(Context context) {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }

    private void pushNotification(Context context, final String msg, String title) {

        int m = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String CHANNEL_ID = "my_channel_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = "my_channel";
            String Description = "This is my channel";
            int importance = NotificationManager.IMPORTANCE_HIGH;

            NotificationChannel mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            mChannel.setDescription(Description);
            mChannel.enableLights(true);
            mChannel.setLightColor(context.getColor(R.color.colorPrimaryDark));
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            mChannel.setShowBadge(false);
            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            builder.setSmallIcon(R.drawable.ic_notification)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_notification))
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                    .setColor(context.getResources().getColor(R.color.colorPrimaryDark))
                    .setCategory(NotificationCompat.CATEGORY_RECOMMENDATION)
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(NotificationCompat.PRIORITY_MAX);
        } else {
            builder.setSmallIcon(R.drawable.ic_notification);
            builder.setStyle(new NotificationCompat.BigTextStyle()
                    .bigText(msg));
        }
        builder.setContentTitle(title)
                .setContentText(msg)
                .setLights(Color.GREEN, 3000, 300)
                .setVibrate(new long[]{100, 200, 300, 400, 500, 600})
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Intent resultIntent = new Intent(context, DisplayNotificationActivity.class);
        resultIntent.putExtra("msg", msg);
        resultIntent.putExtra("title", title);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(DisplayNotificationActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(m, PendingIntent.FLAG_ONE_SHOT);

        builder.setContentIntent(resultPendingIntent);
        builder.setAutoCancel(true);

        notificationManager.notify(m, builder.build());
    }


}
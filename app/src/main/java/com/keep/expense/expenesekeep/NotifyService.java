package com.keep.expense.expenesekeep;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

/**
 * Created by admin on 10/14/2015.
 */
public class NotifyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

    }
    public void onStart(Context context,Intent intent, int startId)
    {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification notification = new NotificationCompat.Builder(context)
                .setContentTitle("Random title")
                .setContentText("Random text")
                .setSmallIcon(R.drawable.coins)
                .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, DailyAlram.class), 0))
                .build();

        notificationManager.notify(0, notification);
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
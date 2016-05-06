package com.keep.expense.expenesekeep;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by admin on 10/14/2015.
 */
public class RebootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED"))
        {
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Vibrator vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
            vibrator.vibrate(2000);
            // Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

            Notification notification = new NotificationCompat.Builder(context)
                    .setContentTitle("Expense Keep")
                    .setContentText("Add your expenses for today.Keep enjoying!!!")
                    .setSmallIcon(R.drawable.coins)
                    .setContentIntent(PendingIntent.getActivity(context, 0, new Intent(context, CategoryList.class), 0))
                    .build();

            notificationManager.notify(0, notification);
            Toast.makeText(context, "Alram is ringing", Toast.LENGTH_LONG).show();
        }}
}

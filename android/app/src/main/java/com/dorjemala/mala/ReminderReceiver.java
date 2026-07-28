package com.dorjemala.mala;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import java.util.Locale;

public class ReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL = "dorjemala_days";

    @Override
    public void onReceive(Context ctx, Intent intent) {
        boolean uk = Locale.getDefault().getLanguage().startsWith("uk");
        String name = SpecialDays.todayName(uk);
        if (name == null) return;

        NotificationManager nm =
                (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        if (nm == null) return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel ch = new NotificationChannel(CHANNEL,
                    uk ? "Особливі дні" : "Special days",
                    NotificationManager.IMPORTANCE_DEFAULT);
            nm.createNotificationChannel(ch);
        }

        Intent open = new Intent(ctx, MainActivity.class);
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) flags |= PendingIntent.FLAG_IMMUTABLE;
        PendingIntent pi = PendingIntent.getActivity(ctx, 0, open, flags);

        Notification.Builder b = (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                ? new Notification.Builder(ctx, CHANNEL)
                : new Notification.Builder(ctx);

        b.setSmallIcon(android.R.drawable.ic_dialog_info)
         .setContentTitle(uk ? "Сьогодні особливий день" : "Today is a special day")
         .setContentText(name)
         .setStyle(new Notification.BigTextStyle().bigText(name + "\n\n" +
                 (uk ? "Добрий день для практики." : "A good day for practice.")))
         .setAutoCancel(true)
         .setContentIntent(pi);

        nm.notify(101, b.build());
    }
}

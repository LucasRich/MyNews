package com.lucas.mynews.Utils;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.lucas.mynews.Controllers.Activities.DisplayNotification;
import com.lucas.mynews.R;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (SharedPref.read(SharedPref.notificationAllow, false) == true){

            SharedPref.init(context);
            Intent intentNotif = new Intent(context, DisplayNotification.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intentNotif, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "default")
                    .setSmallIcon(R.drawable.ic_nytimes_icon)
                    .setContentTitle("New articles !")
                    .setContentText(SharedPref.read(SharedPref.nbArticles, 0) + " new articles wait you")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            notificationManager.notify(1, builder.build());
        }
        else {
            System.out.println("Notification denied");
        }
    }
}

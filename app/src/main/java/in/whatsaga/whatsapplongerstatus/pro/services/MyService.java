package in.whatsaga.whatsapplongerstatus.pro.services;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;
import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import in.whatsaga.whatsapplongerstatus.pro.R;
import in.whatsaga.whatsapplongerstatus.pro.ui.activity.MainActivity;

public class MyService extends Service {


    private static final String CRACK_NOTIFICATION = "crack_notification";
    private static final CharSequence CRACK_NOTIFICATION_NAME = "crack_notification_name";

    public static Intent start(Context context) {
        Intent starter = new Intent(context, MyService.class);
        return starter;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
        Notification notification;
        Intent intent1 = new Intent(this, MainActivity.class);

        intent1.setFlags(FLAG_ACTIVITY_NEW_TASK);
        intent1.setFlags(FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, PendingIntent.FLAG_IMMUTABLE);

        notification = new NotificationCompat.Builder(getBaseContext(), CRACK_NOTIFICATION)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle("Retrieve WA Deleted Message & Media")
                .setContentText("Running in the background")
                .setContentIntent(pendingIntent)
                .setDefaults(NotificationCompat.DEFAULT_SOUND)
                .build();

        startForeground(1, notification);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onDestroy() {
        stopForeground(STOP_FOREGROUND_LEGACY);
        stopSelf();

        super.onDestroy();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(CRACK_NOTIFICATION, CRACK_NOTIFICATION_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(notificationChannel);
        }

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


}
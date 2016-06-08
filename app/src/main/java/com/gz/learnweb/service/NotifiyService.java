package com.gz.learnweb.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Vibrator;

import com.ibm.gz.learnweb.R;
import com.gz.learnweb.activity.LoginActivity;

public class NotifiyService extends Service {
    private static final int NOTIFICATION_FLAG = 1;
    private NotificationManager manager;
    PendingIntent pendingIntent;
    public NotifiyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(this,LoginActivity.class);
        pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        noti();
        stopSelf();
    }


    private void noti(){
        Notification notification=new Notification.Builder(this)
                .setSmallIcon(R.drawable.launcher)
                .setContentTitle("你有新的推荐课程")
                .setContentText("世界第一台计算机")
//                .setContentText("傅毓勤博士讲座")
                .setContentIntent(pendingIntent).build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        manager.notify(NOTIFICATION_FLAG, notification);
        Vibrator vibrator=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(1000);
    }
}

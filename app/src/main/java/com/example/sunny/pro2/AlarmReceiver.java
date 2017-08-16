package com.example.sunny.pro2;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bData = intent.getExtras();


            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

// 設定小圖示、大圖示、狀態列文字、時間、內容標題、內容訊息和內容額外資訊
            builder.setSmallIcon(R.mipmap.icon)
                    .setWhen(System.currentTimeMillis())
                    .setContentTitle("測一測")
                    .setContentText("下班記得準時打卡哦～～不然會收到可怕的異常單喲")
                    .setContentInfo("3");


            Notification notification = builder.build();
            // notification.flags =Notification.FLAG_ONGOING_EVENT;

            notificationManager.notify(0, notification);

    }
}

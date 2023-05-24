package com.example.app_doc_truyen_nhom2.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.app_doc_truyen_nhom2.HienThiChuong;
import com.example.app_doc_truyen_nhom2.R;

public class Notification {

    public void pushNotificationChuongMoi(Context context, String text) {


    }

    public static void createNotificationChanel(Context context, String TenTruyen, String text, long idtruyen, long thutuchuong) {
        String notifyID = "dangdoc";
        String notifyName = "reading";
        String description = "hienchuongdangdoc";
        NotificationChannel channel = null;

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channel = new NotificationChannel(notifyID, notifyName, NotificationManager.IMPORTANCE_DEFAULT);

            channel.setDescription(description);
            notificationManager.createNotificationChannel(channel);

        }
        Intent intent = new Intent(context, HienThiChuong.class);
        Bundle bundle23 = new Bundle();
        bundle23.putLong("IDtruyen", idtruyen);
        bundle23.putLong("thutuchuong", thutuchuong);
        intent.putExtras(bundle23);
        PendingIntent pendingIntent=PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, notifyID)
                .setSmallIcon(R.drawable.icontruyenmoi)
                .setContentTitle(TenTruyen)
.setContentIntent(pendingIntent)

                .setContentText("Bạn Đang Đọc Chương "+text+" - Bấm Để Đọc Tiếp")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mBuilder.setChannelId(notifyID);
        }
        notificationManager.notify(1, mBuilder.build());
    }


}

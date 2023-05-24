package com.example.app_doc_truyen_nhom2.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.example.app_doc_truyen_nhom2.Database.Truyen;
import com.example.app_doc_truyen_nhom2.Notification.Notification;

public class MyServiceReading extends Service {
private Truyen truyen;


    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
long thutuchuong=intent.getLongExtra("thutuchuong", 0);
        long idtruyen=intent.getLongExtra("idtruyen",0);
        String tentruyen=intent.getStringExtra("tentruyen");
        Notification.createNotificationChanel(this,tentruyen, ""+thutuchuong,idtruyen,thutuchuong);


return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
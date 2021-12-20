package com.example.edulockapp.services;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import com.example.edulockapp.broadcast.RestartServiceWhenStoped;

public class BackgroundManager {

    private static final int period = 2*10000; //15 minutes
    private static  final int ALARM_ID= 159;

    private static  BackgroundManager instance;
    private Context context;

    public static BackgroundManager getInstance() {

        if(instance == null)
            instance = new BackgroundManager();
        return instance;
    }

    public BackgroundManager init(Context c){
        context = c;
        return this;
    }

    public boolean isServiceRunning(Class<?> serviceClass){

        ActivityManager manager = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo serviceInfo : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(serviceInfo.service.getClassName())) {
                return true;
            }

        }
        return false;
    }

    public void startService(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            if(!isServiceRunning(ServiceAppLockJObIntent.class)){
                Intent intent = new Intent(context, ServiceAppLockJObIntent.class);
                ServiceAppLockJObIntent.enqueueWork(context, intent);
            }

        } else {
            if(!isServiceRunning(ServiceApplock.class)){
                context.startService(new Intent(context, ServiceApplock.class));
            }
        }
    }

    public void stopService(Class<?> serviceClass){
        if(isServiceRunning(serviceClass)){
            context.stopService(new Intent(context, serviceClass));
        }
    }

    public void startAlarmManager(){
        Intent intent = new Intent(context, RestartServiceWhenStoped.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+period, pendingIntent);
    }

    public void stopAlarm(){
        Intent intent = new Intent(context, RestartServiceWhenStoped.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0);
        AlarmManager manager = (AlarmManager)context.getSystemService(context.ALARM_SERVICE);
        manager.cancel((pendingIntent));
    }
}

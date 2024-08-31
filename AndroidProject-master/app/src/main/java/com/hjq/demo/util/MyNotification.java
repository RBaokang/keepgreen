package com.hjq.demo.util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.widget.Toast;

import com.hjq.demo.R;
import com.hjq.demo.ui.activity.TimerActivity;

/**
 * android 8.0 以后的版本，在创建通知栏的时候，加了一个channelId
 * 为了兼容android所有版本，最好在代码里做一下适配
 */
public final class MyNotification {

    MyNotification(){}

    public static void TestNotification(Context context){
        Toast.makeText(context, "通知测试", Toast.LENGTH_SHORT).show();
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 使用NotificationChannel 类构建一个通知渠道
            NotificationChannel notificationChannel = new NotificationChannel("1", "test",
                    NotificationManager.IMPORTANCE_HIGH);
            //通知的一些行为样式配置。
            notificationChannel.enableLights(true); //是否在launcher icon右上角展示提示点
            notificationChannel.setLightColor(Color.RED); //提示点颜色

            notificationManager.createNotificationChannel(notificationChannel);

            Notification notification = new Notification.Builder(context,"1")
                    .setContentTitle("测试标题")//标题
                    .setContentText("这里是测试内容哦")//内容
                    .setSubText("——测试小文字")//内容下面的一小段文字
                    .setTicker("测试")//收到信息后状态栏显示的文字信息
                    .setWhen(System.currentTimeMillis())//设置通知时间
                    .setSmallIcon(R.mipmap.ic_launcher_round)//设置小图标
                    .build();
            notificationManager.notify(1, notification);
        }else {
            Notification notification = new Notification.Builder(context)
                    .setContentTitle("test标题")//标题
                    .setContentText("这里是测试内容哦")//内容
                    .setSubText("——测试小文字")//内容下面的一小段文字
                    .setTicker("测试")//收到信息后状态栏显示的文字信息
                    .setWhen(System.currentTimeMillis())//设置通知时间
                    .setSmallIcon(R.mipmap.ic_launcher_round)//设置小图标
                    .build();
            notificationManager.notify(1, notification);
        }
    }

    /**
     * 专用于TimerActivity的通知函数
     * 发送一个无声通知
     * 支持通知点击跳转
     * @param context
     * @param Title
     * @param ContentText
     * @param SubText
     * @param Ticker
     */
    public static void SendNotification(Context context,String Title,String ContentText,String SubText,String Ticker){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent=new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setComponent(new ComponentName(context, TimerActivity.class));//用ComponentName得到class对象
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);// 关键的一步，设置启动模式，两种情况
        PendingIntent pendingIntent;
        //PendingIntent 是用于构建通知跳转路径的
        // Android12以后，构建时必须指定标识 PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_IMMUTABLE，否则通知将构建失败抛出异常
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        }else {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 使用NotificationChannel 类构建一个通知渠道
            NotificationChannel notificationChannel = new NotificationChannel("2", Title,
                    NotificationManager.IMPORTANCE_LOW);
            notificationChannel.setSound(null,null);//设置静音
            //通知的一些行为样式配置。
            notificationChannel.enableLights(true); //是否在launcher icon右上角展示提示点
            notificationChannel.setLightColor(Color.RED); //提示点颜色

            notificationManager.createNotificationChannel(notificationChannel);

            Notification notification = new Notification.Builder(context,"2")
                    .setContentTitle(Title)//标题
                    .setContentText(ContentText)//内容
                    .setSubText(SubText)//内容下面的一小段文字
                    .setTicker(Ticker)//收到信息后状态栏显示的文字信息
                    .setContentIntent(pendingIntent)//跳转页面
                    .setWhen(System.currentTimeMillis())//设置通知时间
                    .setSmallIcon(R.mipmap.ic_launcher_round)//设置小图标
                    .build();
            notificationManager.notify(1, notification);
        }else {
            Notification notification = new Notification.Builder(context)
                    .setContentTitle(Title)//标题
                    .setContentText(ContentText)//内容
                    .setSubText(SubText)//内容下面的一小段文字
                    .setTicker(Ticker)//收到信息后状态栏显示的文字信息
                    .setSound(null)//设置静音
                    .setContentIntent(pendingIntent)//跳转页面
                    .setWhen(System.currentTimeMillis())//设置通知时间
                    .setSmallIcon(R.mipmap.ic_launcher_round)//设置小图标
                    .build();
            notificationManager.notify(1, notification);
        }
    }

    public static void OngoingNotification(Context context,String Title,String ContentText,String SubText,String Ticker){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent=new Intent(context, TimerActivity.class);
        PendingIntent pendingIntent;
        //PendingIntent 是用于构建通知跳转路径的
        // Android12以后，构建时必须指定标识 PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_IMMUTABLE，否则通知将构建失败抛出异常
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        }else {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 使用NotificationChannel 类构建一个通知渠道
            NotificationChannel notificationChannel = new NotificationChannel("2", Title,
                    NotificationManager.IMPORTANCE_HIGH);
            //通知的一些行为样式配置。
            notificationChannel.enableLights(false); //是否在launcher icon右上角展示提示点
            notificationChannel.setLightColor(Color.RED); //提示点颜色

            notificationManager.createNotificationChannel(notificationChannel);

            Notification notification = new Notification.Builder(context,"2")
                    .setContentTitle(Title)//标题
                    .setContentText(ContentText)//内容
                    .setSubText(SubText)//内容下面的一小段文字
                    .setTicker(Ticker)//收到信息后状态栏显示的文字信息
                    .setOngoing(true)//常驻
                    .setContentIntent(pendingIntent)//跳转页面
                    //.setWhen(System.currentTimeMillis())//设置通知时间
                    .setSmallIcon(R.mipmap.ic_launcher_round)//设置小图标
                    .build();
            notificationManager.notify(1, notification);
        }else {
            Notification notification = new Notification.Builder(context)
                    .setContentTitle(Title)//标题
                    .setContentText(ContentText)//内容
                    .setSubText(SubText)//内容下面的一小段文字
                    .setTicker(Ticker)//收到信息后状态栏显示的文字信息
                    .setOngoing(true)//常驻
                    .setContentIntent(pendingIntent)//跳转页面
                    //.setWhen(System.currentTimeMillis())//设置通知时间
                    .setSmallIcon(R.mipmap.ic_launcher_round)//设置小图标
                    .build();
            notificationManager.notify(1, notification);
        }
    }

    public static void playVibrate(Context context,long[] vibrationPattern) {
        Vibrator vibrator = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vibrator.vibrate(vibrationPattern, -1);
    }

    public static void playRing(Context context) {
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Ringtone rt = RingtoneManager.getRingtone(context, uri);
        rt.play();
    }
}

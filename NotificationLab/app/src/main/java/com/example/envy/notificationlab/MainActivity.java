package com.example.envy.notificationlab;

import android.app.*;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    public static int WIFI_NOTIFICATION = 100;
    public static int NO_WIFI_NOTIFICATION = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            launchConnectionAvailableNotification();
        } else {
            launchConnectionNotAvailableNotification();
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
        builder.setSmallIcon(android.R.drawable.ic_menu_call);
        builder.setContentTitle("Call to Will");
        builder.setContentText("Missed call from Will");

        String phoneNumber = "123456789";
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+Uri.encode(phoneNumber.trim())));
        PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this,(int)System.currentTimeMillis(),intent,0);
        builder.addAction(android.R.drawable.ic_menu_call,"Call",pIntent);

        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        notificationManager.notify(10, notification);
    }

    public void launchConnectionAvailableNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);
        mBuilder.setSmallIcon(android.R.drawable.btn_radio);
        mBuilder.setContentTitle("Connection is On");
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, (int)System.currentTimeMillis(),intent,0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.wifi)));
        Notification wifiNotification = mBuilder.build();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(WIFI_NOTIFICATION,wifiNotification);


    }

    public void launchConnectionNotAvailableNotification(){
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);
        mBuilder.setSmallIcon(android.R.drawable.btn_radio);
        mBuilder.setContentTitle("Connection is Off");
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, (int)System.currentTimeMillis(),intent,0);
        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(BitmapFactory.decodeResource(getResources(), R.drawable.no_wifi)));
        mBuilder.setOngoing(true);

        Notification wifiNotification = mBuilder.build();
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NO_WIFI_NOTIFICATION, wifiNotification);
    }


}

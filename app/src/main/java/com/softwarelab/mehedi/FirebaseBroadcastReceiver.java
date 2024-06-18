package com.softwarelab.mehedi;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;
import org.antlr.runtime.debug.DebugEventListener;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class FirebaseBroadcastReceiver extends WakefulBroadcastReceiver {

    JSONObject data;
    String f179id;
    String message;
    String title;

    public void onReceive(Context context, Intent intent) {
        try {
            Bundle extras = intent.getExtras();
            if (extras != null) {
                String jsonString = extras.getString("message");
                if (jsonString != null) {
                    this.data = new JSONObject(jsonString);
                    this.message = this.data.optString("title");
                    this.title = this.data.optString("body");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("BroadcastReceiver::", "BroadcastReceiver");
        sendNotification(this.title, this.message, context);
    }

    private int getNotificationIcon(NotificationCompat.Builder builder) {
        if (Build.VERSION.SDK_INT >= 21) {
            builder.setColor(32768);
        }
        return R.mipmap.ic_launcher;
    }

    private void sendNotification(String str, String str2, Context context) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, DebugEventListener.PROTOCOL_VERSION);
        Intent intent = new Intent(context, Start.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(603979776);
        PendingIntent pendingIntent = null;
        if (Build.VERSION.SDK_INT >= 31) {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        }

        String imageUrl = this.data.optString("image", null);

        NotificationCompat.BigPictureStyle bigPictureStyle = new NotificationCompat.BigPictureStyle();
        if (imageUrl != null) {
            try {
                InputStream in = new URL(imageUrl).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(in);
                bigPictureStyle.bigPicture(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        bigPictureStyle.setBigContentTitle(str2);
        bigPictureStyle.setSummaryText(str);

        builder.setContentIntent(pendingIntent);
        builder.setSmallIcon(R.drawable.notif);
        builder.setContentTitle(str);
        builder.setContentText(str2);
        builder.setPriority(2);
        builder.setAutoCancel(true);

        builder.setStyle(bigPictureStyle);

        @SuppressLint("WrongConstant") NotificationManager notificationManager = (NotificationManager) context.getSystemService("notification");
        int time = (int) ((new Date().getTime() / 1000) % 2147483647L);
        if (Build.VERSION.SDK_INT >= 26) {
            notificationManager.createNotificationChannel(new NotificationChannel(DebugEventListener.PROTOCOL_VERSION, DebugEventListener.PROTOCOL_VERSION, NotificationManager.IMPORTANCE_DEFAULT));
        }
        notificationManager.notify(time, builder.build());
    }
}

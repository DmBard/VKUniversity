package com.dmbaryshev.vkschool.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.dmbaryshev.vkschool.R;
import com.dmbaryshev.vkschool.model.view_model.AudioVM;

/**
 * Created by Dmitry on 24.02.2016.
 */
public class NotificationHelper {

    public static Notification getNotification(Context context,
                                               AudioVM audioVM,
                                               boolean isPlaying) {

        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                                                  R.layout.notification_audio);
        remoteViews.setTextViewText(R.id.tv_title, audioVM.artist + " - " + audioVM.title);

        remoteViews.setImageViewResource(R.id.iv_play_pause,
                                         !isPlaying ? R.mipmap.ic_play_notification
                                                   : R.mipmap.ic_pause);
        remoteViews.setOnClickPendingIntent(R.id.iv_play_pause,
                                            getService(context,
                                                       isPlaying ? AudioService.ACTION_PAUSE
                                                                 : AudioService.ACTION_PLAY));

        remoteViews.setOnClickPendingIntent(R.id.iv_stop,
                                            getService(context, AudioService.ACTION_STOP));
        remoteViews.setOnClickPendingIntent(R.id.iv_previous,
                                            getService(context, AudioService.ACTION_PREVIOUS));
        remoteViews.setOnClickPendingIntent(R.id.iv_next,
                                            getService(context, AudioService.ACTION_NEXT));
        remoteViews.setOnClickPendingIntent(R.id.iv_stop,
                                            getService(context, AudioService.ACTION_STOP));

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_play_notification)
                                                                                    .setContent(
                                                                                            remoteViews);

        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_ONGOING_EVENT;

        return notification;
    }

    public static PendingIntent getService(Context context, String action) {
        return PendingIntent.getService(context,
                                        0,
                                        AudioService.getIntent(context, action, null, 0),
                                        0);
    }
}

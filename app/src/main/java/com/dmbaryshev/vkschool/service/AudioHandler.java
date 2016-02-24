package com.dmbaryshev.vkschool.service;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class AudioHandler extends Handler {
    public static final int MSG_PLAY     = 10;
    public static final int MSG_STOP     = 11;
    public static final int MSG_PAUSE    = 12;
    public static final int MSG_NEXT     = 13;
    public static final int MSG_PREVIOUS = 14;

    private AudioHandlerCallback mHandlerCallback;

    public AudioHandler(Looper looper, AudioHandlerCallback handlerCallback) {
        super(looper);
        mHandlerCallback = handlerCallback;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_PLAY:
                mHandlerCallback.onPlay();
                break;
            case MSG_PAUSE:
                mHandlerCallback.onPause();
                break;
            case MSG_STOP:
                mHandlerCallback.onStop();
                break;
            case MSG_NEXT:
                mHandlerCallback.onNext();
                break;
            case MSG_PREVIOUS:
                mHandlerCallback.onPrevious();
                break;

            default:
                super.handleMessage(msg);
        }
    }

    public void stop() {
        removeCallbacksAndMessages(null);
        getLooper().quit();
        mHandlerCallback = null;
    }

    public interface AudioHandlerCallback {

        void onPlay();

        void onStop();

        void onPause();

        void onNext();

        void onPrevious();
    }
}

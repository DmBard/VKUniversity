package com.dmbaryshev.vkschool.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.support.annotation.NonNull;

import com.dmbaryshev.vkschool.model.network.ResponseAnswer;
import com.dmbaryshev.vkschool.model.repository.AudioRepo;
import com.dmbaryshev.vkschool.model.view_model.AudioVM;
import com.dmbaryshev.vkschool.utils.DLog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

public class AudioService extends Service implements MediaPlayer.OnPreparedListener,
                                                     MediaPlayer.OnCompletionListener,
                                                     AudioHandler.AudioHandlerCallback {
    private static final String TAG                  = DLog.makeLogTag(AudioService.class);
    public static final  int    NOTIFICATION_ID      = 20;
    private static final int    TRACKS_COUNT         = 30;
    public static final  String ACTION_PLAY          = "com.dmbaryshev.vkschool.service.action.PLAY";
    public static final  String ACTION_STOP          = "com.dmbaryshev.vkschool.service.action.STOP";
    public static final  String ACTION_PAUSE         = "com.dmbaryshev.vkschool.service.action.PAUSE";
    public static final  String ACTION_NEXT          = "com.dmbaryshev.vkschool.service.action.NEXT";
    public static final  String ACTION_PREVIOUS      = "com.dmbaryshev.vkschool.service.action.PREVIOUS";
    private static final String EXTRA_AUDIO_LIST     = "com.dmbaryshev.vkschool.service.extra.AUDIO_LIST";
    private static final String EXTRA_AUDIO_POSITION = "com.dmbaryshev.vkschool.service.extra.AUDIO_POSITION";
    private AudioHandler  mAudioHandler;
    private Messenger     mMessenger;
    private MediaPlayer   mMediaPlayer;
    private List<AudioVM> mAudioVMs;
    private int           mCurrentPosition;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    private AudioVM mCurrentAudioVM;
    private boolean mIsPause  = false;
    private boolean isLoading = false;

    public AudioService() {
    }

    public static void execute(Context context,
                               String action,
                               ArrayList<AudioVM> audioVMs,
                               int position) {
        Intent intent = getIntent(context, action, audioVMs, position);
        context.startService(intent);
    }

    @NonNull
    public static Intent getIntent(Context context,
                                   String action,
                                   ArrayList<AudioVM> audioVMs,
                                   int position) {
        Intent intent = new Intent(context, AudioService.class);
        intent.setAction(action);
        intent.putParcelableArrayListExtra(EXTRA_AUDIO_LIST, audioVMs);
        intent.putExtra(EXTRA_AUDIO_POSITION, position);
        return intent;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HandlerThread thread = new HandlerThread("AudioService",
                                                 android.os.Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();

        mAudioHandler = new AudioHandler(thread.getLooper(), this);

        mMessenger = new Messenger(new ResponseHandler(thread.getLooper()));
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Message msg = mAudioHandler.obtainMessage();
        if (intent != null && intent.getAction() != null) {
            final String action = intent.getAction();
            if (action == null) { return START_NOT_STICKY; }
            switch (action) {
                case ACTION_PLAY:
                    msg.what = AudioHandler.MSG_PLAY;
                    ArrayList<AudioVM> listExtra = intent.getParcelableArrayListExtra(
                            EXTRA_AUDIO_LIST);
                    if (listExtra != null) {
                        mAudioVMs = listExtra;
                    }
                    int intExtra = intent.getIntExtra(EXTRA_AUDIO_POSITION, 0);
                    if (intExtra != 0) {
                        mCurrentPosition = intExtra;
                    }
                    break;
                case ACTION_PAUSE:
                    msg.what = AudioHandler.MSG_PAUSE;
                    break;
                case ACTION_PREVIOUS:
                    msg.what = AudioHandler.MSG_PREVIOUS;
                    break;
                case ACTION_STOP:
                    msg.what = AudioHandler.MSG_STOP;
                    break;
                case ACTION_NEXT:
                    msg.what = AudioHandler.MSG_NEXT;
                    break;
            }
            msg.replyTo = mMessenger;
        }
        mAudioHandler.sendMessage(msg);
        return START_STICKY_COMPATIBILITY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCompositeSubscription.clear();
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        mAudioHandler.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        onNext();
    }

    private static final class ResponseHandler extends Handler {

        private static final int MSG_LOST = 2000;

        public ResponseHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_LOST:
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }

    @Override
    public void onPlay() {
        if (mMediaPlayer == null) {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        if (!mIsPause) {
            try {
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.stop();
                }
                mMediaPlayer.reset();
                mCurrentAudioVM = mAudioVMs.get(mCurrentPosition);
                mMediaPlayer.setDataSource(mCurrentAudioVM.url);
                mMediaPlayer.prepare();
                mMediaPlayer.setOnCompletionListener(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        mMediaPlayer.start();
        startForeground(NOTIFICATION_ID,
                        NotificationHelper.getNotification(this, mCurrentAudioVM, true));
        mIsPause = false;
    }

    @Override
    public void onStop() {
        stopSelf();
    }

    @Override
    public void onPause() {
        if (mMediaPlayer != null && isPlaying()) {
            mMediaPlayer.pause();
            mIsPause = true;
            startForeground(NOTIFICATION_ID,
                            NotificationHelper.getNotification(this, mCurrentAudioVM, false));
        }
    }

    @Override
    public void onNext() {
        if (isLoading) {return;}
        mCurrentPosition++;
        mIsPause = false;
        if (mCurrentPosition >= mAudioVMs.size() - 1) {
            loadMore();
            return;
        }
        onPlay();
    }

    public void loadMore() {
        isLoading = true;
        Observable<ResponseAnswer<AudioVM>> observable = new AudioRepo().getAudio(TRACKS_COUNT,
                                                                                  mAudioVMs.size() + TRACKS_COUNT);
        Subscription subscription = observable.observeOn(Schedulers.io())
                                              .subscribe(audioVMResponseAnswer->{
                                                  isLoading = false;
                                                  mAudioVMs.addAll(audioVMResponseAnswer.getAnswer());
                                                  onPlay();
                                              }, throwable->{
                                                  DLog.e(TAG, throwable.getMessage(), throwable);
                                                  isLoading = false;
                                              });
        mCompositeSubscription.add(subscription);
    }

    @Override
    public void onPrevious() {
        if (mCurrentPosition == 0) {
            return;
        }
        mIsPause = false;
        mCurrentPosition--;
        onPlay();
    }

    private boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }
}

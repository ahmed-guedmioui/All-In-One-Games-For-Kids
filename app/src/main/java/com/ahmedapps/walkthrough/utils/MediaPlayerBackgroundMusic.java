package com.ahmedapps.walkthrough.utils;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.annotation.Nullable;

import com.ahmedapps.walkthrough.R;

public class MediaPlayerBackgroundMusic extends Service {

    private MediaPlayer mediaPlayer;
    private final IBinder binder = new MediaPlayerBinder();

    public class MediaPlayerBinder extends Binder {
        MediaPlayerBackgroundMusic getService() {
            return MediaPlayerBackgroundMusic.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.background_sound);

        try {
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void play() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void stop() {
        mediaPlayer.stop();
        mediaPlayer.release();
    }
}


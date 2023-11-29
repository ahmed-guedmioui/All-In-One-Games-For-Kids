package com.ahmedapps.walkthrough.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class MediaPlayerManager {

    private final Context context;
    private MediaPlayerBackgroundMusic mediaPlayerService;
    private boolean bound = false;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            MediaPlayerBackgroundMusic.MediaPlayerBinder binder = (MediaPlayerBackgroundMusic.MediaPlayerBinder) iBinder;
            mediaPlayerService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            bound = false;
        }
    };

    public MediaPlayerManager(Context context) {
        this.context = context;
    }

    public void start() {
        Intent intent = new Intent(context, MediaPlayerBackgroundMusic.class);
        context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    public void stop() {
        if (bound) {
            context.unbindService(connection);
            bound = false;
        }
    }

    public void play() {
        if (!Config.IS_PLAY_MUSIC)
            return;

        if (bound) {
            mediaPlayerService.play();
        }
    }

    public void pause() {
        if (!Config.IS_PLAY_MUSIC)
            return;

        if (bound) {
            mediaPlayerService.pause();
        }
    }

    public void stopMediaPlayer() {
        Config.IS_PLAY_MUSIC = false;

        if (bound) {
            mediaPlayerService.pause();
        }
    }

    public void playMediaPlayer() {
        Config.IS_PLAY_MUSIC = true;

        if (bound) {
            mediaPlayerService.play();
        }
    }


}

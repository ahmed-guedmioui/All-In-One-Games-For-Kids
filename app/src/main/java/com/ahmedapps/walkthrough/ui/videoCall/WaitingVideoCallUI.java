package com.ahmedapps.walkthrough.ui.videoCall;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmedapps.walkthrough.R;
import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

public class WaitingVideoCallUI extends AppCompatActivity {

    MediaPlayer call_song;
    ImageView Accept_video;
    ImageView Refuse_Video;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_waiting_video_call);

        Config.mediaPlayerManager.pause();
        playRing();

        Animation fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);
        ImageView img_caller = findViewById(R.id.img_caller);

        new Handler().postDelayed(() -> {
            img_caller.startAnimation(fall_less_animation);
            img_caller.setVisibility(View.VISIBLE);
        }, 500);

        Accept_video = findViewById(R.id.video_accept);
        Refuse_Video = findViewById(R.id.video_refuse);


        ViewsAndDialogs viewsAndDialogs = new ViewsAndDialogs(this, this);

        viewsAndDialogs.clickAnimation(Accept_video, view -> {
            call_song.stop();
            call_song.release();
            call_song = null;
            startActivity(new Intent(WaitingVideoCallUI.this, AcceptVideoCallUI.class));
            finish();
        });

        viewsAndDialogs.clickAnimation(Refuse_Video, view -> {
            call_song.stop();
            call_song.release();
            call_song = null;
            finish();
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        call_song.stop();
        call_song.release();
        call_song = null;
    }


    public void playRing(){

        call_song = MediaPlayer.create(getBaseContext(), R.raw.ring_video);
        call_song.setOnCompletionListener(mediaPlayer -> {
            playRing();
        });
        try {
            call_song.prepare();
        }catch (Exception e){
            e.printStackTrace();
        }
        call_song.start();

    }

}


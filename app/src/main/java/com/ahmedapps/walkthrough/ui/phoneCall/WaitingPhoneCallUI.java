package com.ahmedapps.walkthrough.ui.phoneCall;

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

public class WaitingPhoneCallUI extends AppCompatActivity {

    MediaPlayer call_song;
    ImageView accept_call;
    ImageView refuse_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_waiting_phone_call);

        ViewsAndDialogs viewsAndDialogs = new ViewsAndDialogs(this, this);

        Config.mediaPlayerManager.pause();
        playRing();

        Animation fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);
        ImageView img_caller = findViewById(R.id.img_caller);

        new Handler().postDelayed(() -> {
            img_caller.startAnimation(fall_less_animation);
            img_caller.setVisibility(View.VISIBLE);
        }, 500);

        accept_call = findViewById(R.id.accept_call);
        refuse_call = findViewById(R.id.refuse_call);

        viewsAndDialogs.clickAnimation(accept_call, view -> {
            startActivity(new Intent(getApplicationContext(), AcceptPhoneCallUI.class));
            onBackPressed();
        });

        viewsAndDialogs.clickAnimation(refuse_call, view -> {
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

    public void playRing() {

        call_song = MediaPlayer.create(getBaseContext(), R.raw.ring);
        call_song.setOnCompletionListener(mediaPlayer -> {
            playRing();
        });
        try {
            call_song.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        call_song.start();

    }


}


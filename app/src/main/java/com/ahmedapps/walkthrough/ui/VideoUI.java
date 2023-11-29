package com.ahmedapps.walkthrough.ui;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmedapps.walkthrough.R;
import com.ahmedapps.walkthrough.utils.Config;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

public class VideoUI extends AppCompatActivity {

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_video);

        Config.mediaPlayerManager.pause();

        

        Bundle data = getIntent().getExtras();

        if (data != null) {

            int position = data.getInt("position");

            YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player_view);
            getLifecycle().addObserver(youTubePlayerView);

            youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = Config.DATA.videos.get(position).getYoutube_video_id();
                    youTubePlayer.loadVideo(videoId, 0);
                }
            });
        }

        Animation fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);
        TextView text = findViewById(R.id.text);

        new Handler().postDelayed(() -> {
            text.startAnimation(fall_less_animation);
            text.setVisibility(View.VISIBLE);
        }, 300);
    }

}
package com.ahmedapps.walkthrough.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedapps.walkthrough.R;
import com.ahmedapps.walkthrough.adapters.VideosAdapter;


import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

public class VideosListUI extends AppCompatActivity {

    private Animation fall_less_animation;
    private int position;
    

    private int clicksShowCount;
    private int clicks = 0;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_videos_list);

        fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);

        clicksShowCount = Config.DATA.clicks;

        
        recyclerView();
        initialize();

        ViewsAndDialogs viewsAndDialogs = new ViewsAndDialogs(this, this);
        viewsAndDialogs.menuDialog(null);
    }

    private void initialize() {
        TextView text = findViewById(R.id.text);

        new Handler().postDelayed(() -> {
            text.startAnimation(fall_less_animation);
            text.setVisibility(View.VISIBLE);
        }, 500);
    }

    private void navigate() {
        Intent intent = new Intent(this, VideoUI.class);
        intent.putExtra("position", position);
        startActivity(intent);

        
    }

    private void recyclerView() {

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        VideosAdapter videosAdapter = new VideosAdapter(this, this);
        videosAdapter.setClickListener((view, position) -> {
            this.position = position;
            clicks++;
            checkToperformAction();
        });
        recyclerView.setAdapter(videosAdapter);
    }


    private void checkToperformAction() {
        if (clicksShowCount == 0)
            navigate();
        else if (clicksShowCount == 1)
            navigate();
        else if (clicksShowCount == clicks) {
            navigate();
            clicksShowCount = clicksShowCount + Config.DATA.clicks;
        } else
            navigate();

    }

   

    @Override
    protected void onPause() {
        super.onPause();
        Config.mediaPlayerManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        Config.mediaPlayerManager.play();

        
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        
        clicksShowCount = Config.DATA.clicks;
        finish();
    }
}

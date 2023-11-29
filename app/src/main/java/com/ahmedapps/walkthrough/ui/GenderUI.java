package com.ahmedapps.walkthrough.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.ahmedapps.walkthrough.utils.MediaPlayerManager;
import com.bumptech.glide.Glide;
import com.ahmedapps.walkthrough.BuildConfig;
import com.ahmedapps.walkthrough.R;

import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

public class GenderUI extends AppCompatActivity {

    
    
    Animation fade_in_anime, fade_out_anime, fall_anime, fall_less_animation;

    private ViewsAndDialogs viewsAndDialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_gender);


        Config.mediaPlayerManager = new MediaPlayerManager(this);
        Config.mediaPlayerManager.start();

        viewsAndDialogs = new ViewsAndDialogs(this, this);

        fade_in_anime = AnimationUtils.loadAnimation(this, R.anim.fade_in_anime);
        fade_out_anime = AnimationUtils.loadAnimation(this, R.anim.fade_out_gender_anime);
        fall_anime = AnimationUtils.loadAnimation(this, R.anim.fall_anime);
        fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);


        

        initialize();


    }

    private void initialize() {

        TextView textGender = findViewById(R.id.textGender);
        ImageView bg = findViewById(R.id.bg);

        new Handler().postDelayed(() -> {
            textGender.startAnimation(fall_less_animation);
            textGender.setVisibility(View.VISIBLE);

            bg.startAnimation(fall_less_animation);
            bg.setVisibility(View.VISIBLE);
        }, 500);

        RelativeLayout maleBTNHolder = findViewById(R.id.maleBTNHolder);
        RelativeLayout femaleBTNHolder = findViewById(R.id.femaleBTNHolder);

        new Handler().postDelayed(() -> {
            maleBTNHolder.startAnimation(fade_in_anime);
            maleBTNHolder.setVisibility(View.VISIBLE);

            femaleBTNHolder.startAnimation(fade_in_anime);
            femaleBTNHolder.setVisibility(View.VISIBLE);
        }, 1000);


        CardView maleBTN = findViewById(R.id.maleBTN);
        CardView femaleBTN = findViewById(R.id.femaleBTN);

        Animation gender_click_anime = AnimationUtils.loadAnimation(this, R.anim.gender_click_anime);

        maleBTN.setOnClickListener(v -> {
            maleBTN.setClickable(false);
            maleBTNHolder.startAnimation(gender_click_anime);
            showBoy();

            new Handler().postDelayed(() -> {
                maleBTN.setClickable(true);
            }, 1000);
        });

        femaleBTN.setOnClickListener(v -> {
            femaleBTN.setClickable(false);
            femaleBTNHolder.startAnimation(gender_click_anime);
            showGirl();

            new Handler().postDelayed(() -> {
                femaleBTN.setClickable(true);
            }, 1000);
        });

    }

    private void showBoy() {

        RelativeLayout boyView = findViewById(R.id.boyView);
        TextView welcome_boy = findViewById(R.id.welcome_boy);
        ImageView gender_bg_boy = findViewById(R.id.bg_boy);
        LinearLayout boy = findViewById(R.id.boy);
        CardView continueBTN = findViewById(R.id.continueBTN_boy);


        new Handler().postDelayed(() -> {

            boyView.startAnimation(fade_in_anime);
            boyView.setVisibility(View.VISIBLE);

        }, 100);

        new Handler().postDelayed(() -> {

            gender_bg_boy.startAnimation(fade_in_anime);
            gender_bg_boy.setVisibility(View.VISIBLE);
        }, 700);

        new Handler().postDelayed(() -> {
            welcome_boy.startAnimation(fall_anime);
            welcome_boy.setVisibility(View.VISIBLE);
        }, 1000);

        new Handler().postDelayed(() -> {
            boy.startAnimation(fade_in_anime);
            boy.setVisibility(View.VISIBLE);
        }, 1500);

        new Handler().postDelayed(() -> {
            continueBTN.startAnimation(fade_in_anime);
            continueBTN.setVisibility(View.VISIBLE);
        }, 2500);


        viewsAndDialogs.clickAnimationBig(continueBTN, view -> {
            boy.startAnimation(fade_out_anime);
            boy.setVisibility(View.INVISIBLE);


            startActivity(new Intent(GenderUI.this, MainUI.class));


            new Handler().postDelayed(() -> {
                boy.setVisibility(View.VISIBLE);
            }, 1000);
        });
    }

    private void showGirl() {
        RelativeLayout girlView = findViewById(R.id.girlView);
        TextView welcome_girl = findViewById(R.id.welcome_girl);
        ImageView gender_bg_girl = findViewById(R.id.bg_girl);
        LinearLayout girl = findViewById(R.id.girl);
        CardView continueBTN = findViewById(R.id.continueBTN_girl);

        new Handler().postDelayed(() -> {

            girlView.startAnimation(fade_in_anime);
            girlView.setVisibility(View.VISIBLE);

        }, 100);

        new Handler().postDelayed(() -> {

            gender_bg_girl.startAnimation(fade_in_anime);
            gender_bg_girl.setVisibility(View.VISIBLE);

        }, 700);

        new Handler().postDelayed(() -> {
            welcome_girl.startAnimation(fall_anime);
            welcome_girl.setVisibility(View.VISIBLE);
        }, 1000);

        new Handler().postDelayed(() -> {
            girl.startAnimation(fade_in_anime);
            girl.setVisibility(View.VISIBLE);
        }, 1500);

        new Handler().postDelayed(() -> {
            continueBTN.startAnimation(fade_in_anime);
            continueBTN.setVisibility(View.VISIBLE);
        }, 2500);

        viewsAndDialogs.clickAnimationBig(continueBTN, view -> {
            girl.startAnimation(fade_out_anime);
            girl.setVisibility(View.INVISIBLE);

            startActivity(new Intent(GenderUI.this, MainUI.class));


            new Handler().postDelayed(() -> {
                girl.setVisibility(View.VISIBLE);
            }, 1000);
            
        });
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
}














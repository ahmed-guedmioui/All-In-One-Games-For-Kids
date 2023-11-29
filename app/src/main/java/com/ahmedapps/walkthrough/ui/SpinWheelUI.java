package com.ahmedapps.walkthrough.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ahmedapps.walkthrough.R;


import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.Shared;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

import java.util.Random;

public class SpinWheelUI extends AppCompatActivity {

    private final String[] sectors =
            {"0", "10", "20", "35", "50", "65", "70", "85", "100", "200", "300", "500"};

    private final int[] sectorsIndexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private final int[] sectorDegrees = new int[sectorsIndexes.length];
    private final Random random = new Random();
    private int degree;
    private boolean isSpinning;


    private ImageView wheel;
    private LinearLayout gotCoinsToAnimate;
    private TextView gotCoinsToAnimateTXT, coinsTXT;
    private CardView spinWheelBTN;

    private Dialog watchToGetSpinsDialog;


    private ViewsAndDialogs viewsAndDialogs;

    private MediaPlayer spinSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_spin_wheel);

        viewsAndDialogs = new ViewsAndDialogs(this, this);

        spinSoundEffect();
        getDegreeForSectors();


        initialize();

        wheel = findViewById(R.id.wheel);
        gotCoinsToAnimate = findViewById(R.id.gotCoinsToAnimate);
        spinWheelBTN = findViewById(R.id.spinWheelBTN);

        setSpinDetailsTXT();


        RelativeLayout coinsBTN = findViewById(R.id.coinsBTN);
        viewsAndDialogs.clickAnimation(coinsBTN, view -> {
            viewsAndDialogs.coinsDialog();
        });

        gotCoinsToAnimateTXT = findViewById(R.id.gotCoinsToAnimateTXT);
        coinsTXT = findViewById(R.id.coins);
        int coins = Config.getCoins(this);
        coinsTXT.setText("" + coins);

        viewsAndDialogs.clickAnimation(spinWheelBTN, view -> {
            if (!isSpinning) {
                spin();
                isSpinning = true;
                spinWheelBTN.setVisibility(View.GONE);
            }
        });

        wheel.setOnClickListener(v -> {
            if (!isSpinning) {
                viewsAndDialogs.buttonClickSoundEffect();
                spin();
                isSpinning = true;
                spinWheelBTN.setVisibility(View.GONE);
            }
        });

    }

    private void initialize() {
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in_anime);
        Animation fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);

        ImageView Top = findViewById(R.id.top);
        ScrollView parentLayout = findViewById(R.id.parentLayout);
        TextView text = findViewById(R.id.text);

        new Handler().postDelayed(() -> {
            Top.startAnimation(fade_in);
            Top.setVisibility(View.VISIBLE);

            parentLayout.startAnimation(fade_in);
            parentLayout.setVisibility(View.VISIBLE);
        }, 500);

        new Handler().postDelayed(() -> {

            text.startAnimation(fall_less_animation);
            text.setVisibility(View.VISIBLE);

        }, 800);

    }

    public void spinSoundEffect() {
        spinSound = MediaPlayer.create(this, R.raw.spinning_wheel);

        spinSound.setOnCompletionListener(mediaPlayer -> {
            spinSound.pause();
        });
        try {
            spinSound.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setSpinDetailsTXT() {
       
    }

    private void spin() {

        spinSound.start();

        degree = random.nextInt(6);

        RotateAnimation rotateAnimation = new RotateAnimation(0, (360 * sectorsIndexes.length) + sectorDegrees[degree],
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(5000);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                int coinsIndex = sectorsIndexes[degree];

                String coins = sectors[(coinsIndex - 1)];
                gotCoins(coins);
                isSpinning = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        wheel.startAnimation(rotateAnimation);

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.spin_wheel_anime);
        ConstraintLayout toAnimate = findViewById(R.id.toAnimate);
        toAnimate.startAnimation(anim);
    }

    private void gotCoins(String coinsToAdd) {

        setSpinDetailsTXT();

        spinWheelBTN.setVisibility(View.VISIBLE);


        int coinsToAddINT = Integer.parseInt(coinsToAdd);

        if (coinsToAddINT == 0) {
            gotCoinsToAnimateTXT.setText("" + coinsToAdd);
            viewsAndDialogs.gotCoinsAnimate(gotCoinsToAnimate);
            return;
        }

        Config.addCoins(this, coinsToAddINT);
        coinsTXT.setText("" + Config.getCoins(this));
        gotCoinsToAnimateTXT.setText("+" + coinsToAdd);

        gotCoinsAnimate();
        viewsAndDialogs.coinsRainAnime();
    }

    private void gotCoinsAnimate() {

        Animation anim = AnimationUtils.loadAnimation(this, R.anim.got_coins_text_anime);
        gotCoinsToAnimate.startAnimation(anim);

        gotCoinsToAnimate.setVisibility(View.VISIBLE);
        hideGotCoinsToAnimate();
    }

    private void hideGotCoinsToAnimate() {
        Animation fade_out = AnimationUtils.loadAnimation(this, R.anim.fade_out_anime);

        new Handler().postDelayed(() -> {
            gotCoinsToAnimate.startAnimation(fade_out);
            gotCoinsToAnimate.setVisibility(View.GONE);
        }, 3500);

    }

    private void getDegreeForSectors() {
        int sectorDegree = 360 / sectorsIndexes.length;

        for (int i = 0; i < sectorsIndexes.length; i++) {
            sectorDegrees[i] = (i + 1) * sectorDegree;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        spinSound.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        spinSound.release();
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
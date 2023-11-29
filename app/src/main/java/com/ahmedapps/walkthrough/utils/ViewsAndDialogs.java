package com.ahmedapps.walkthrough.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;
import com.ahmedapps.walkthrough.R;

public class ViewsAndDialogs {

    private final Context context;
    private final Activity activity;

    public ViewsAndDialogs(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void menuDialog(View view) {

        final Dialog menuDialog = new Dialog(context);
        menuDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        menuDialog.setCancelable(true);
        menuDialog.setContentView(R.layout.dialog_menu);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(menuDialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;

        menuDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        menuDialog.getWindow().setAttributes(layoutParams);

        ImageView drawerButton;

        if (view == null)
            drawerButton = activity.findViewById(R.id.drawerButton);
        else
            drawerButton = view.findViewById(R.id.drawerButton);

        clickAnimationSmall(drawerButton, v -> {
            menuDialog.show();
        });

        CardView soundController = menuDialog.findViewById(R.id.soundController);
        TextView soundText = menuDialog.findViewById(R.id.soundText);

        if (!Config.IS_PLAY_MUSIC) {
            soundText.setText(activity.getText(R.string.musicOff));
            soundController.setAlpha(0.7f);
        }
        else {
            soundText.setText(activity.getText(R.string.musicOn));
            soundController.setAlpha(1f);
        }

        clickAnimationBig(soundController, v -> {
            if (Config.IS_PLAY_MUSIC) {
                soundText.setText(activity.getText(R.string.musicOff));
                soundController.setAlpha(0.7f);
                Config.mediaPlayerManager.stopMediaPlayer();
            } else {
                soundText.setText(activity.getText(R.string.musicOn));
                soundController.setAlpha(1f);
                Config.mediaPlayerManager.playMediaPlayer();
            }
        });

    }

    public void coinsDialog() {

        Dialog coinsDialog = new Dialog(context);
        coinsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        coinsDialog.setCancelable(true);
        coinsDialog.setContentView(R.layout.dialog_coins);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(coinsDialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;

        coinsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        coinsDialog.getWindow().setAttributes(layoutParams);
        coinsDialog.setCancelable(true);

        TextView userCoins = coinsDialog.findViewById(R.id.user_coins);

        int coins = Config.getCoins(context);
        userCoins.setText("" + coins);

        ImageView close = coinsDialog.findViewById(R.id.close);
        clickAnimation(close, view -> {
            new Handler().postDelayed(() -> {
                coinsDialog.dismiss();
            }, 50);
        });
        coinsDialog.show();

    }

    public void coinsRainAnimeMainUI() {
        LottieAnimationView gotCoins = activity.findViewById(R.id.gotCoinAnimation);

        new Handler().postDelayed(() -> {
            coinsSoundEffect();
        }, 500);

        new Handler().postDelayed(() -> {
            gotCoins.setVisibility(View.VISIBLE);
        }, 1000);

        new Handler().postDelayed(() -> {
            gotCoins.setVisibility(View.GONE);
        }, 2000);
    }

    public void coinsRainAnime() {
        LottieAnimationView gotCoins = activity.findViewById(R.id.gotCoinAnimation);

        coinsSoundEffect();
        new Handler().postDelayed(() -> {
            gotCoins.setVisibility(View.VISIBLE);
        }, 200);

        new Handler().postDelayed(() -> {
            gotCoins.setVisibility(View.GONE);
        }, 1200);
    }


    public void gotCoinsAnimate(LinearLayout gotCoinsToAnimate) {

        Animation anim = AnimationUtils.loadAnimation(context, R.anim.got_coins_text_anime);
        gotCoinsToAnimate.startAnimation(anim);

        gotCoinsToAnimate.setVisibility(View.VISIBLE);


        Animation fade_out = AnimationUtils.loadAnimation(context, R.anim.fade_out_anime);

        new Handler().postDelayed(() -> {
            gotCoinsToAnimate.startAnimation(fade_out);
            gotCoinsToAnimate.setVisibility(View.GONE);
        }, 4000);
    }

    public void coinsSoundEffect() {
        MediaPlayer coinsSound = MediaPlayer.create(context, R.raw.coins_sound);

        coinsSound.setOnCompletionListener(mediaPlayer -> {
            coinsSound.stop();
            coinsSound.release();
        });
        try {
            coinsSound.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        coinsSound.start();
    }

    private final static int ANIMATION_DURATION = 100;

    public void buttonClickSoundEffect() {
        MediaPlayer clickSound = MediaPlayer.create(context, R.raw.button_click);

        clickSound.setOnCompletionListener(mediaPlayer -> {
            clickSound.stop();
            clickSound.release();
        });
        try {
            clickSound.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        clickSound.start();
    }


    public void clickAnimation(View view, ViewClickListener listener) {

        view.setOnClickListener(v -> {

            buttonClickSoundEffect();

            v.setScaleX(1.07f);
            v.setScaleY(1.07f);

            new Handler().postDelayed(() -> {

                v.setScaleX(1.0f);
                v.setScaleY(1.0f);
                if (listener != null) {
                    listener.onViewClick(v);
                }

            }, ANIMATION_DURATION);

        });
    }

    public void clickAnimationSmall(View view, ViewClickListener listener) {

        view.setOnClickListener(v -> {

            buttonClickSoundEffect();

            v.setScaleX(1.2f);
            v.setScaleY(1.2f);

            new Handler().postDelayed(() -> {

                v.setScaleX(1.0f);
                v.setScaleY(1.0f);
                if (listener != null) {
                    listener.onViewClick(v);
                }

            }, ANIMATION_DURATION);

        });
    }

    public void clickAnimationBig(View view, ViewClickListener listener) {

        view.setOnClickListener(v -> {

            buttonClickSoundEffect();

            v.setScaleX(1.03f);
            v.setScaleY(1.03f);

            new Handler().postDelayed(() -> {

                v.setScaleX(1.0f);
                v.setScaleY(1.0f);
                if (listener != null) {
                    listener.onViewClick(v);
                }

            }, ANIMATION_DURATION);

        });
    }

    public interface ViewClickListener {
        void onViewClick(View view);
    }

    public void clickAnimation(View animateView, View view, ViewClickListener listener) {

        view.setOnClickListener(v -> {

            buttonClickSoundEffect();

            animateView.setScaleX(1.07f);
            animateView.setScaleY(1.07f);

            new Handler().postDelayed(() -> {

                animateView.setScaleX(1.0f);
                animateView.setScaleY(1.0f);
                if (listener != null) {
                    listener.onViewClick(v);
                }

            }, ANIMATION_DURATION);

        });
    }

    public void clickAnimationGender(View animateView, View view, ViewClickListener listener) {

        view.setOnClickListener(v -> {

            buttonClickSoundEffect();

            animateView.setScaleX(1.1f);
            animateView.setScaleY(1.1f);


            if (listener != null) {
                listener.onViewClick(v);
            }

            new Handler().postDelayed(() -> {

                animateView.setScaleX(1.0f);
                animateView.setScaleY(1.0f);
            }, 1000);

        });
    }

    public void clickAnimationBig(View animateView, View view, ViewClickListener listener) {

        view.setOnClickListener(v -> {

            buttonClickSoundEffect();

            animateView.setScaleX(1.03f);
            animateView.setScaleY(1.03f);

            new Handler().postDelayed(() -> {

                animateView.setScaleX(1.0f);
                animateView.setScaleY(1.0f);
                if (listener != null) {
                    listener.onViewClick(v);
                }

            }, ANIMATION_DURATION);

        });
    }
}

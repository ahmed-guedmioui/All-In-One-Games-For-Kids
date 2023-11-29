package com.ahmedapps.walkthrough.ui;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import com.ahmedapps.walkthrough.BuildConfig;
import com.ahmedapps.walkthrough.R;



import com.ahmedapps.walkthrough.ui.puzzle.PuzzleListUI;
import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

public class MainUI extends AppCompatActivity {

    private Dialog rateExitDialog;
    private int dialogAction = 0;
    
    
    

    private ViewsAndDialogs viewsAndDialogs;

    private int clicksShowCount;
    private int clicks = 0;

    private int action;

    private TextView coinsTXT;

    CardView startBtn, spinBtn, quizBtn, puzzleBtn, videosBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_main);


        viewsAndDialogs = new ViewsAndDialogs(this, this);

        initialize();

        if (!Config.DATA.show_quiz) {
            quizBtn.setVisibility(View.GONE);
        }
        if (!Config.DATA.show_spin) {
            spinBtn.setVisibility(View.GONE);
        }
        if (!Config.DATA.show_puzzle) {
            puzzleBtn.setVisibility(View.GONE);
        }

        if (!Config.DATA.show_videos_btn) {
            videosBtn.setVisibility(View.GONE);
        }

        if (!Config.DATA.show_tips && !Config.DATA.show_fake_call) {
            startBtn.setVisibility(View.GONE);
        }

    }

    private void initialize() {

        clicksShowCount = Config.DATA.clicks;
        
        

        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in_anime);
        Animation fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);

        ScrollView parentLayout = findViewById(R.id.parentLayout);
        TextView text = findViewById(R.id.text);

        new Handler().postDelayed(() -> {
            parentLayout.startAnimation(fade_in);
            parentLayout.setVisibility(View.VISIBLE);

            findViewById(R.id.loading_activity).setVisibility(View.GONE);
        }, 3000);

        new Handler().postDelayed(() -> {

            text.startAnimation(fall_less_animation);
            text.setVisibility(View.VISIBLE);

        }, 3500);

        if (Config.DATA.rating_exit_popup)
            rateExitDialog();

        coinsTXT = findViewById(R.id.coins);
        coinsTXT.setText("" + Config.getCoins(this));

        startBtn = findViewById(R.id.start_btn);
        videosBtn = findViewById(R.id.videos_btn);

        quizBtn = findViewById(R.id.quiz_btn);
        spinBtn = findViewById(R.id.spin_btn);
        puzzleBtn = findViewById(R.id.puzzle_btn);

        RelativeLayout addCoinsBTN = findViewById(R.id.addCoinsBTN);

        viewsAndDialogs.clickAnimation(addCoinsBTN, view -> {
            dialogAction = 0;
            coinsDialog();
        });

        initializeButtons();
        viewsAndDialogs.menuDialog(null);
    }


    private void startBtnClicked() {

        if (!Config.DATA.fore_user_to_collect_coins_to_start) {
            action = 0;
            clicks++;
            checkToperformAction();

            return;
        }

        if (Config.getCoins(MainUI.this) < Config.TO_START_COINS) {
            dialogAction = 1;
            coinsDialog();

            return;
        }

        action = 0;
        clicks++;
        checkToperformAction();

    }

    private void videosBtnClicked() {

        if (!Config.DATA.fore_user_to_collect_coins_to_start) {
            action = 1;
            clicks++;
            checkToperformAction();

            return;
        }

        if (Config.getCoins(MainUI.this) < Config.TO_OPEN_VIDEOS_COINS) {
            dialogAction = 2;
            coinsDialog();
            return;
        }

        action = 1;
        clicks++;
        checkToperformAction();

    }

    private void initializeButtons() {

        viewsAndDialogs.clickAnimationBig(startBtn, view -> {
            startBtnClicked();
        });

        viewsAndDialogs.clickAnimationBig(videosBtn, view -> {
            videosBtnClicked();
        });

        viewsAndDialogs.clickAnimation(quizBtn, view -> {
            action = 2;
            clicks++;
            checkToperformAction();
        });


        viewsAndDialogs.clickAnimation(spinBtn, view -> {
            action = 3;
            clicks++;
            checkToperformAction();
        });


        viewsAndDialogs.clickAnimation(puzzleBtn, view -> {
            action = 4;
            clicks++;
            checkToperformAction();
        });

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


    private void navigate() {
        Intent intent;

        if (action == 1)
            intent = new Intent(MainUI.this, VideosListUI.class);
        else if (action == 2)
            intent = new Intent(MainUI.this, QuizUI.class);
        else if (action == 3)
            intent = new Intent(MainUI.this, SpinWheelUI.class);
        else if (action == 4)
            intent = new Intent(MainUI.this, PuzzleListUI.class);
        else
            intent = new Intent(MainUI.this, FragmentsHolderUI.class);

        startActivity(intent);
        
    }

    private void coinsDialog() {

        Dialog coinsDialog = new Dialog(this);
        coinsDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        coinsDialog.setCancelable(true);

        if (dialogAction == 0)
            coinsDialog.setContentView(R.layout.dialog_watch_to_get_coins);
        else
            coinsDialog.setContentView(R.layout.dialog_not_enough_coins);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(coinsDialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;

        coinsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        coinsDialog.getWindow().setAttributes(layoutParams);

        TextView watchAdToGetCoinsTXT = coinsDialog.findViewById(R.id.watch_ad_to_get_coins_txt);
        String watchAdToGetCoinsTXT_String = "WATCH AN AD AND GET " + Config.WATCH_AD_COINS + " COINS";

        TextView userCoins = coinsDialog.findViewById(R.id.user_coins);
        CardView watchAdForCoinsBTN = coinsDialog.findViewById(R.id.watchAdForCoinsBTN);
        ProgressBar progressBarCoins = coinsDialog.findViewById(R.id.progressCoins);

        ImageView close = coinsDialog.findViewById(R.id.close);
        viewsAndDialogs.clickAnimation(close, view -> {
            new Handler().postDelayed(() -> {
                isComplete = false;
                coinsDialog.dismiss();
            }, 50);
        });

        coinsDialog.setOnDismissListener(d -> {
            setTexts(watchAdToGetCoinsTXT, watchAdToGetCoinsTXT_String, "" + Config.WATCH_AD_COINS);
            watchAdForCoinsBTN.setVisibility(View.VISIBLE);
            progressBarCoins.setVisibility(View.GONE);
        });

        viewsAndDialogs.clickAnimation(watchAdForCoinsBTN, view -> {
            watchAdForCoinsBTN.setVisibility(View.GONE);
            progressBarCoins.setVisibility(View.VISIBLE);
            new Handler().postDelayed(() -> {
                showRewarded(coinsDialog, watchAdForCoinsBTN, watchAdToGetCoinsTXT);
                progressBarCoins.setVisibility(View.GONE);
            }, 2600);
        });


        userCoins.setText("" + Config.getCoins(MainUI.this));

        setTexts(watchAdToGetCoinsTXT, watchAdToGetCoinsTXT_String, "" + Config.WATCH_AD_COINS);

        if (dialogAction == 1 || dialogAction == 2) {
            TextView t1 = coinsDialog.findViewById(R.id.text1);
            String t1_String = "YOU NEED " + Config.TO_START_COINS + " COINS TO CONTINUE TO THE GAME.";
            String t1_String_2 = "YOU NEED " + Config.TO_OPEN_VIDEOS_COINS + " COINS TO WATCH THESE FUN VIDEOS.";

            TextView t2 = coinsDialog.findViewById(R.id.text2);
            String t2_String = "Play quiz game to win up to " + Config.TOTAL_QUIZ_COINS + " coins with every quiz.";

            TextView t3 = coinsDialog.findViewById(R.id.text3);
            String t3_String = "Play jigsaw puzzle game to win " + Config.PUZZLE_COINS + " coins with every puzzle.";

            TextView t4 = coinsDialog.findViewById(R.id.text4);
            String t4_String = "Spin the lucky-wheel to win up to " + Config.SPIN_WHEEL_COINS + " coins!";

            if (dialogAction == 1)
                setTexts(t1, t1_String, "" + Config.TO_START_COINS);
            else
                setTexts(t1, t1_String_2, "" + Config.TO_OPEN_VIDEOS_COINS);

            setTexts(t2, t2_String, "" + Config.TOTAL_QUIZ_COINS);
            setTexts(t3, t3_String, "" + Config.PUZZLE_COINS);
            setTexts(t4, t4_String, "" + Config.SPIN_WHEEL_COINS);
        }

        coinsDialog.show();
    }

    private void setTexts(TextView textView, String text, String textToColor) {
        SpannableString spannableString = new SpannableString(text);

        int start = text.indexOf(textToColor);
        int end = start + textToColor.length();
        int color = ContextCompat.getColor(this, R.color.gold);

        try {
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            textView.setText(spannableString);
        } catch (Exception e) {
            textView.setText(text);
        }

    }

    private boolean isComplete = false;

    private void showRewarded(Dialog coinsDialog, CardView button, TextView textRewarded) {

      
    }

    private void addCoins(int coinsToAdd) {
        Config.addCoins(this, coinsToAdd);
        coinsTXT.setText("" + Config.getCoins(this));
    }
    

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Config.mediaPlayerManager.stop();
    }

    @Override
    public void onBackPressed() {
        if (Config.DATA.rating_exit_popup)
            rateExitDialog.show();
        else
            super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Config.mediaPlayerManager.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();

        viewsAndDialogs.menuDialog(null);

        Config.mediaPlayerManager.play();

        coinsTXT.setText("" + Config.getCoins(this));
        
    }

    private void rateExitDialog() {

        rateExitDialog = new Dialog(this);
        rateExitDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        rateExitDialog.setCancelable(true);
        rateExitDialog.setContentView(R.layout.dialog_rate_app);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(rateExitDialog.getWindow().getAttributes());
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;

        rateExitDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rateExitDialog.getWindow().setAttributes(layoutParams);

        rateExitDialog.findViewById(R.id.close).setOnClickListener((v) -> {
            rateExitDialog.dismiss();
        });

        rateExitDialog.setOnDismissListener(dialog -> {
            super.onBackPressed();
        });

        RatingBar ratingBar = rateExitDialog.findViewById(R.id.ratingBar);

        ratingBar.setRating(4);
        ratingBar.setOnRatingBarChangeListener((ratingBar1, rating, fromUser) -> {
            if (rating >= 3) {
                rate();
                rateExitDialog.dismiss();
            } else {
                Toast.makeText(MainUI.this, "Thanks for rating our app", Toast.LENGTH_SHORT).show();
                rateExitDialog.dismiss();
            }
        });

    }

    private void rate() {
        try {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        } catch (ActivityNotFoundException e) {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, BuildConfig.APPLICATION_ID)));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        else
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        intent.addFlags(flags);
        return intent;
    }

}
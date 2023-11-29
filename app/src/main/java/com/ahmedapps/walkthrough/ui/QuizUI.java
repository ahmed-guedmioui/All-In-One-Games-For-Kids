package com.ahmedapps.walkthrough.ui;

import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.R;


import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

public class QuizUI extends AppCompatActivity {

    TextView question, scoreTxt, scoreTxtDetail,
            answer1TXT, answer2TXT, answer3TXT,
            currentQ, totalQ;

    CardView showScore, playAgain, next;
    RelativeLayout answer1, answer2, answer3, showScoreHolder, playAgainHolder, nextHolder,
            answer1Holder, answer2Holder, answer3Holder;

    ProgressBar questionsProgress;
    ProgressBar scoreProgress;

    
    int which;

    

    int currentQuestion = 0;
    int totalQuestion = Config.DATA.quizes.size();

    int indexToperformAction;

    int score = 0;
    int originalScore;

    private TextView coinsTXT;

    Animation fade_in, fall_less_animation;
    private ViewsAndDialogs viewsAndDialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_quiz);

        viewsAndDialogs = new ViewsAndDialogs(this, this);

        fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in_anime);
        fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);

        RelativeLayout coinsBTN = findViewById(R.id.coinsBTN);
        viewsAndDialogs.clickAnimation(coinsBTN, view -> {
            viewsAndDialogs.coinsDialog();
        });

        coinsTXT = findViewById(R.id.coins);
        int coins = Config.getCoins(this);
        coinsTXT.setText("" + coins);

        scoreTxt = findViewById(R.id.scoreTxt);
        scoreTxtDetail = findViewById(R.id.scoreTxtDetail);
        question = findViewById(R.id.question);

        questionsProgress = findViewById(R.id.questionsProgress);
        currentQ = findViewById(R.id.currentQ);
        totalQ = findViewById(R.id.totalQ);

        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);

        answer1TXT = findViewById(R.id.answer1TXT);
        answer2TXT = findViewById(R.id.answer2TXT);
        answer3TXT = findViewById(R.id.answer3TXT);

        showScore = findViewById(R.id.showScore);
        playAgain = findViewById(R.id.playAgain);
        next = findViewById(R.id.next);

        showScoreHolder = findViewById(R.id.showScoreHolder);
        playAgainHolder = findViewById(R.id.playAgainHolder);
        nextHolder = findViewById(R.id.nextHolder);

        answer1Holder = findViewById(R.id.answer1Holder);
        answer2Holder = findViewById(R.id.answer2Holder);
        answer3Holder = findViewById(R.id.answer3Holder);

        onClick();

        questionsProgress.setMax(totalQuestion);

        totalQ.setText("" + totalQuestion);

        indexToperformAction = totalQuestion / 2;

        initialize();
        
        
        loadQuestion();
        
    }

    private void initialize() {

        ImageView Top = findViewById(R.id.top);
        RelativeLayout coinsBTN = findViewById(R.id.coinsBTN);
        TextView text = findViewById(R.id.text);
        NestedScrollView target = findViewById(R.id.target);

        new Handler().postDelayed(() -> {

            coinsBTN.startAnimation(fall_less_animation);
            coinsBTN.setVisibility(View.VISIBLE);

            Top.startAnimation(fall_less_animation);
            Top.setVisibility(View.VISIBLE);

            text.startAnimation(fall_less_animation);
            text.setVisibility(View.VISIBLE);

        }, 500);


        new Handler().postDelayed(() -> {
            target.startAnimation(fade_in);
            target.setVisibility(View.VISIBLE);
        }, 1000);

    }


    private void onClick() {

        viewsAndDialogs.clickAnimationBig(answer1Holder, answer1, view -> {

            answer1.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer2.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer3.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));

            answer1.setBackground(getResources().getDrawable(R.drawable.quiz_selected_answer_bg));
            score = originalScore;

            if (Config.DATA.quizes.get(currentQuestion).getCorrect() == 1)
                score = originalScore + 1;
        });

        viewsAndDialogs.clickAnimationBig(answer2Holder, answer2, view -> {

            answer1.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer2.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer3.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));

            answer2.setBackground(getResources().getDrawable(R.drawable.quiz_selected_answer_bg));
            score = originalScore;

            if (Config.DATA.quizes.get(currentQuestion).getCorrect() == 2)
                score = originalScore + 1;
        });

        viewsAndDialogs.clickAnimationBig(answer3Holder, answer3, view -> {

            answer1.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer2.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer3.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));

            answer3.setBackground(getResources().getDrawable(R.drawable.quiz_selected_answer_bg));
            score = originalScore;

            if (Config.DATA.quizes.get(currentQuestion).getCorrect() == 3)
                score = originalScore + 1;
        });


        viewsAndDialogs.clickAnimation(nextHolder, next, view -> {

            answer1.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer2.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer3.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));

            currentQuestion++;
            if (currentQuestion == indexToperformAction - 1) {
                which = 1;

                if (currentQuestion != totalQuestion) {
                    nextAnime();
                    loadQuestion();
                }

            } else {

                if (currentQuestion != totalQuestion) {
                    nextAnime();
                    loadQuestion();

                }
            }

            if (currentQuestion == totalQuestion - 1)
                showScoreHolder.setVisibility(View.VISIBLE);
        });

        viewsAndDialogs.clickAnimationBig(showScoreHolder, showScore, view -> {

            answer1.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer2.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer3.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            showScoreHolder.setVisibility(View.GONE);
            showScore();
            questionsProgress.setProgress(totalQuestion);
            which = 2;
            
        });

        viewsAndDialogs.clickAnimationBig(playAgainHolder, playAgain, view ->

        {

            answer1.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer2.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));
            answer3.setBackground(getResources().getDrawable(R.drawable.quiz_question_and_answer_bg));

            playAgainHolder.setVisibility(View.GONE);
            nextHolder.setVisibility(View.VISIBLE);
            currentQuestion = 0;
            score = 0;
            originalScore = 0;
            questionsProgress.setProgress(0);
            scoreTxt.setVisibility(View.GONE);
            findViewById(R.id.questionHolder).setVisibility(View.VISIBLE);
            scoreProgress.setVisibility(View.GONE);
            scoreTxtDetail.setVisibility(View.GONE);
            currentQ.setVisibility(View.VISIBLE);
            totalQ.setVisibility(View.VISIBLE);
            questionsProgress.setVisibility(View.VISIBLE);
            scoreProgress.setProgress(0);
            scoreTxtDetail.setText("");
            playAgainAnime();
            loadQuestion();
        });

    }

    private void showScore() {

        scoreProgress = findViewById(R.id.scoreProgressBar);
        scoreProgress.setMax(100);

        scoreProgress.setVisibility(View.VISIBLE);
        scoreTxtDetail.setVisibility(View.VISIBLE);

        new Handler().postDelayed(() -> {

            if (score != 0)
                fillProgress();

            scoreTxt.setText(score + " / " + totalQuestion);

        }, 500);

        if (score == 0) {
            scoreTxt.startAnimation(fade_in);
            scoreTxt.setVisibility(View.VISIBLE);

            playAgainHolder.startAnimation(fade_in);
            playAgainHolder.setVisibility(View.VISIBLE);
        } else {
            new Handler().postDelayed(() -> {
                scoreTxt.startAnimation(fade_in);
                scoreTxt.setVisibility(View.VISIBLE);

                playAgainHolder.startAnimation(fade_in);
                playAgainHolder.setVisibility(View.VISIBLE);
            }, 1200);
        }

        new Handler().postDelayed(() -> {
            addQuizCoins();
        }, 1500);


        findViewById(R.id.questionHolder).setVisibility(View.GONE);
        questionsProgress.setVisibility(View.GONE);

        answer1Holder.setVisibility(View.GONE);
        answer2Holder.setVisibility(View.GONE);
        answer3Holder.setVisibility(View.GONE);

        currentQ.setVisibility(View.GONE);
        totalQ.setVisibility(View.GONE);

        showScoreHolder.setVisibility(View.GONE);
        nextHolder.setVisibility(View.GONE);
    }

    private void fillProgress() {

        int oneScoreProgress = 100 / totalQuestion;
        int progressTotalScore = oneScoreProgress * score;

        if (score == totalQuestion)
            progressTotalScore = 100;

        Handler handler = new Handler();
        int finalProgressTotalScore = progressTotalScore;
        new Thread(() -> {
            int a = scoreProgress.getProgress();
            while (a < finalProgressTotalScore) {
                a += 1;
                int finalA = a;
                handler.post(() -> {
                    scoreProgress.setProgress(finalA);
                });
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();

    }

    private void playAgainAnime() {
        findViewById(R.id.target).setVisibility(View.GONE);

        Transition transition = new Slide(Gravity.START);
        transition.setDuration(50);
        transition.addTarget(R.id.target);

        CoordinatorLayout parent = findViewById(R.id.rootLayout);

        TransitionManager.beginDelayedTransition(parent, transition);

        findViewById(R.id.target).setVisibility(View.VISIBLE);

    }

    private void nextAnime() {
        findViewById(R.id.target).setVisibility(View.GONE);

        Transition transition = new Slide(Gravity.END);
        transition.setDuration(100);
        transition.addTarget(R.id.target);

        CoordinatorLayout parent = findViewById(R.id.rootLayout);

        TransitionManager.beginDelayedTransition(parent, transition);

        findViewById(R.id.target).setVisibility(View.VISIBLE);

    }

    private void loadQuestion() {

        questionsProgress.setProgress(currentQuestion);

        if (currentQuestion != totalQuestion)
            nextHolder.setVisibility(View.VISIBLE);

        if (currentQuestion == totalQuestion - 1)
            nextHolder.setVisibility(View.GONE);

        currentQ.setText("" + (currentQuestion + 1));

        originalScore = score;

        answer1Holder.setVisibility(View.VISIBLE);
        answer2Holder.setVisibility(View.VISIBLE);
        answer3Holder.setVisibility(View.VISIBLE);

        question.setText(Config.DATA.quizes.get(currentQuestion).getQuestion());

        answer1TXT.setText(Config.DATA.quizes.get(currentQuestion).getAnswer_1());
        answer2TXT.setText(Config.DATA.quizes.get(currentQuestion).getAnswer_2());
        answer3TXT.setText(Config.DATA.quizes.get(currentQuestion).getAnswer_3());
    }

    

    

    

   

    @Override
    public void onBackPressed() {
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

        Config.mediaPlayerManager.play();

        
    }

    private void addQuizCoins() {

        int percentage = (score * 100) / totalQuestion;

        if (percentage == 100) {
            Config.addCoins(this, Config.TOTAL_QUIZ_COINS);

            viewsAndDialogs.coinsRainAnime();
            int coins = Config.getCoins(this);
            coinsTXT.setText("" + coins);

            setScoreTxtDetail("" + Config.TOTAL_QUIZ_COINS);

            return;
        }

        if (percentage >= 90) {
            Config.addCoins(this, Config.ANSWERED_90_PERCENT_QUIZ_COINS);

            viewsAndDialogs.coinsRainAnime();
            int coins = Config.getCoins(this);
            coinsTXT.setText("" + coins);

            setScoreTxtDetail("" + Config.ANSWERED_90_PERCENT_QUIZ_COINS);

            return;
        }

        if (percentage >= 70) {
            Config.addCoins(this, Config.ANSWERED_70_PERCENT_QUIZ_COINS);

            viewsAndDialogs.coinsRainAnime();
            int coins = Config.getCoins(this);
            coinsTXT.setText("" + coins);

            setScoreTxtDetail("" + Config.ANSWERED_70_PERCENT_QUIZ_COINS);

            return;
        }

        if (percentage >= 50) {
            Config.addCoins(this, Config.ANSWERED_50_PERCENT_QUIZ_COINS);

            viewsAndDialogs.coinsRainAnime();
            int coins = Config.getCoins(this);
            coinsTXT.setText("" + coins);

            setScoreTxtDetail("" + Config.ANSWERED_50_PERCENT_QUIZ_COINS);

            return;
        }

        if (percentage >= 10) {
            Config.addCoins(this, Config.ANSWERED_10_PERCENT_QUIZ_COINS);

            viewsAndDialogs.coinsRainAnime();
            int coins = Config.getCoins(this);
            coinsTXT.setText("" + coins);

            setScoreTxtDetail("" + Config.ANSWERED_10_PERCENT_QUIZ_COINS);

            return;
        }

        setScoreTxtDetail("" + 0);

    }

    private void setScoreTxtDetail(String coins) {
        String text;

        if (coins.equals("0"))
            text = "You Got " + coins + " Coins! \uD83D\uDE14";
        else
            text = "You Got " + coins + " Coins! \uD83D\uDE0D";

        SpannableString spannableString = new SpannableString(text);

        int start = text.indexOf(coins);
        int end = start + coins.length();
        int color = ContextCompat.getColor(this, R.color.accentColor_2);

        try {
            spannableString.setSpan(new ForegroundColorSpan(color), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            scoreTxtDetail.setText(spannableString);
        } catch (Exception e) {
            scoreTxtDetail.setText(text);
        }

    }

}

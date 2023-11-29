package com.ahmedapps.walkthrough.ui;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmedapps.walkthrough.BuildConfig;
import com.ahmedapps.walkthrough.R;


import com.ahmedapps.walkthrough.ui.fragmants.FragmentGuideList;
import com.ahmedapps.walkthrough.ui.fragmants.FragmentCalls;
import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.Shared;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

public class FragmentsHolderUI extends AppCompatActivity {

    private RelativeLayout home, videos;

    private FragmentGuideList fragmentGuideList;
    private FragmentCalls fragmentCalls;


    private ViewsAndDialogs viewsAndDialogs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_fragments_holder);

        viewsAndDialogs = new ViewsAndDialogs(this, this);


        initialize();

    }

    private void initialize() {

        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in_anime);
        LinearLayout navigation = findViewById(R.id.navigation);

        if (Config.DATA.show_tips && Config.DATA.show_fake_call) {
            new Handler().postDelayed(() -> {
                navigation.startAnimation(fade_in);
                navigation.setVisibility(View.VISIBLE);
            }, 300);
        }


        home = findViewById(R.id.homeHolder);
        videos = findViewById(R.id.videosHolder);

        home.setSelected(true);
        videos.setSelected(false);
        home.setAlpha(1);
        videos.setAlpha(0.5f);

        fragmentGuideList = new FragmentGuideList();
        fragmentCalls = new FragmentCalls();

        if (Config.DATA.show_tips && !Config.DATA.show_fake_call) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_Container, fragmentGuideList, null)
                    .commit();
            return;
        }

        if (!Config.DATA.show_tips && Config.DATA.show_fake_call) {
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_Container, fragmentCalls, null)
                    .commit();
            return;
        }

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_Container, fragmentGuideList, null)
                .commit();
        fragmentChangeClick();
    }

    private void fragmentChangeClick() {
        viewsAndDialogs.clickAnimationSmall(home, v -> {
            // show home action
            setHomeFragment();
        });

        viewsAndDialogs.clickAnimationSmall(videos, v -> {
            // show videos action
            setVideoFragment();
        });

    }

    public void setHomeFragment() {
        home.setSelected(true);
        videos.setSelected(false);

        home.setAlpha(1);
        videos.setAlpha(0.3f);

        getSupportFragmentManager().beginTransaction()

                .setCustomAnimations(
                        R.anim.fragment_slide_from_left,  // enter
                        R.anim.fragment_slide_to_right,  // exit
                        R.anim.fragment_fade_in,   // popEnter
                        R.anim.fragment_fade_out  // popExit
                )
                .setReorderingAllowed(true)
                .replace(R.id.fragment_Container, fragmentGuideList, null)
                .commit();

    }

    private void setVideoFragment() {

        videos.setSelected(true);
        home.setSelected(false);

        videos.setAlpha(1);
        home.setAlpha(0.3f);

        getSupportFragmentManager().beginTransaction()
                .setCustomAnimations(
                        R.anim.fragment_slide_from_right,  // enter
                        R.anim.fragment_slide_to_left,  // exit
                        R.anim.fragment_fade_in,   // popEnter
                        R.anim.fragment_fade_out  // popExit
                )
                .setReorderingAllowed(true)
                .replace(R.id.fragment_Container, fragmentCalls, null)
                .commit();

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
        flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        intent.addFlags(flags);
        return intent;
    }
}














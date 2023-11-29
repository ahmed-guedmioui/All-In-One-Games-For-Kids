package com.ahmedapps.walkthrough.ui;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.ahmedapps.walkthrough.R;
import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.Data;
import com.ahmedapps.walkthrough.utils.Internet;
import com.ahmedapps.walkthrough.utils.Shared;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;
import com.airbnb.lottie.LottieAnimationView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.onesignal.OneSignal;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class SplashUI extends AppCompatActivity {

    private Internet Internet;

    private CardView tryAgainButton;
    private LottieAnimationView progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_splash);


        ImageView splashIcon = findViewById(R.id.splashLogo);
        ImageView bg = findViewById(R.id.bg);

        Animation fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);

        new Handler().postDelayed(() -> {
            splashIcon.startAnimation(fall_less_animation);
            splashIcon.setVisibility(View.VISIBLE);

            bg.startAnimation(fall_less_animation);
            bg.setVisibility(View.VISIBLE);

        }, 1000);

        tryAgainButton = findViewById(R.id.try_again_button);
        progressBar = findViewById(R.id.animationView);

        Internet = new Internet(this);

        if (Internet.isNetworkAvailable()) {
            loadData();
        } else {
            visibilityHandler(true);
        }

        ViewsAndDialogs viewsAndDialogs = new ViewsAndDialogs(this, this);
        viewsAndDialogs.clickAnimation(tryAgainButton, v -> {
            visibilityHandler(false);
            if (Internet.isNetworkAvailable()) {
                loadData();
            } else {
                visibilityHandler(true);
            }
        });
    }

    private void loadData() {

        AssetManager assetManager = getApplicationContext().getAssets();

        try {
            // Open the file from the "assets" folder
            InputStream inputStream = assetManager.open("All In One Games For Kids.json");

            // Convert the input stream to a String (JSON content)
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            String jsonContent = stringBuilder.toString();

            // Now you have the JSON content as a string
            // You can use it in your Gson parsing as you mentioned
            Config.DATA = new Gson().fromJson(jsonContent, Data.class);

            OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
            OneSignal.initWithContext(this);
            OneSignal.setAppId(Config.DATA.onesignal_id);
            OneSignal.promptForPushNotifications();

            int coins = Config.getCoins(this);
            Config.setCoins(this, coins);

            int spins = Shared.getInt(this, Config.SPINS_SAVED_KEY, 3);
            Shared.setInt(this, Config.SPINS_SAVED_KEY, spins);

            Done();

            // Now you can access the parsed data in Config.DATA
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void loadInfo() {




        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "fileURL", null,
                response -> {
                    Config.DATA = new Gson().fromJson(String.valueOf(response), Data.class);

                    OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);
                    OneSignal.initWithContext(this);
                    OneSignal.setAppId(Config.DATA.onesignal_id);
                    OneSignal.promptForPushNotifications();

                    int coins = Config.getCoins(this);
                    Config.setCoins(this, coins);

                    int spins = Shared.getInt(this, Config.SPINS_SAVED_KEY, 3);
                    Shared.setInt(this, Config.SPINS_SAVED_KEY, spins);

                    Done();

                },

                error -> {
                    visibilityHandler(true);
                }
        );

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
        requestQueue.getCache().clear();
    }

    private void visibilityHandler(boolean showButton) {
        if (showButton) {
            tryAgainButton.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        } else {
            tryAgainButton.setVisibility(View.GONE);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    private void Done() {

        new Handler().postDelayed(() -> {

            boolean privacy = Shared.getBoolean(this, "privacy", false);
            Intent intent;

            if (privacy) {
                intent = new Intent(SplashUI.this, GenderUI.class);
            } else {
                intent = new Intent(SplashUI.this, PrivacyTermsActivity.class);
            }

            startActivity(intent);
            finish();
        }, 1000);
    }


}
package com.ahmedapps.walkthrough.ui.puzzle;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedapps.walkthrough.adapters.PuzzleAdapter;
import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.R;


import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

import java.io.IOException;

public class PuzzleListUI extends AppCompatActivity {

    private int clicksShowCount;
    private int clicks = 0;
    
    
    private int position;
    private String[] files;

    private TextView coinsTXT;
    private RecyclerView recyclerView;

    private ViewsAndDialogs viewsAndDialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_list_puzzle);

        viewsAndDialogs = new ViewsAndDialogs(this, this);

        clicksShowCount = Config.DATA.clicks;

        coinsTXT = findViewById(R.id.coins);
        int coins = Config.getCoins(this);
        coinsTXT.setText("" + coins);


        RelativeLayout coinsBTN = findViewById(R.id.coinsBTN);
        viewsAndDialogs.clickAnimation(coinsBTN, view -> {
            viewsAndDialogs.coinsDialog();
        });

        
        

        recyclerView = findViewById(R.id.puzzle_recyclerView);
        initialize();

        AssetManager am = getAssets();
        try {
            files = am.list("img");
            recyclerView();
        } catch (IOException e) {
            Toast.makeText(this, e.getLocalizedMessage(), Toast.LENGTH_SHORT);
        }
    }

    private void recyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        PuzzleAdapter puzzleAdapter = new PuzzleAdapter(this, this);
        puzzleAdapter.setClickListener((view, i) -> {
            clicks++;
            position = i;
            checkToperformAction();
        });
        recyclerView.setAdapter(puzzleAdapter);
    }

    private void initialize() {
        Animation fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in_anime);

        ImageView Top = findViewById(R.id.top);
        TextView text = findViewById(R.id.text);

        new Handler().postDelayed(() -> {
            Top.startAnimation(fade_in);
            Top.setVisibility(View.VISIBLE);

            recyclerView.startAnimation(fade_in);
            recyclerView.setVisibility(View.VISIBLE);
        }, 500);


        new Handler().postDelayed(() -> {

            text.startAnimation(fall_less_animation);
            text.setVisibility(View.VISIBLE);
        }, 1000);

    }

    private void checkToperformAction() {
        if (clicksShowCount == 0)
            navigate();
        else if (clicksShowCount == 1)
            performAction();
        else if (clicksShowCount == clicks) {
            performAction();
            clicksShowCount = clicksShowCount + Config.DATA.clicks;
        } else
            navigate();
    }

    private void performAction() {
            navigate();
    }

    private void navigate() {
        Intent intent = new Intent(getApplicationContext(), PuzzleUI.class);
        intent.putExtra("assetName", files[position % files.length]);
        startActivity(intent);

        

    }

    @Override
    public void onBackPressed() {
        clicksShowCount = Config.DATA.clicks;
        finish();
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
        coinsTXT = findViewById(R.id.coins);
        int coins = Config.getCoins(this);
        coinsTXT.setText("" + coins);

        
    }

}

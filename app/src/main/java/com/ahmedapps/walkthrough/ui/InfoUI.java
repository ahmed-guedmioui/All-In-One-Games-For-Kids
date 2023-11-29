package com.ahmedapps.walkthrough.ui;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ahmedapps.walkthrough.R;
import com.ahmedapps.walkthrough.utils.Config;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

public class InfoUI extends AppCompatActivity {

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_info);


        title = findViewById(R.id.main_title);
        ImageView imageView = findViewById(R.id.image);
        TextView guide = findViewById(R.id.text);
        TextView guide2 = findViewById(R.id.textTwo);
        Bundle data = getIntent().getExtras();
        LottieAnimationView animationView = findViewById(R.id.animationView);

        if (data != null) {

            int position = data.getInt("position");

            title.setText(Config.DATA.infos.get(position).getTitle());

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                guide.setText(Html.fromHtml(Config.DATA.infos.get(position).getText(), Html.FROM_HTML_MODE_LEGACY));
                guide2.setText(Html.fromHtml(Config.DATA.infos.get(position).getText_two(), Html.FROM_HTML_MODE_LEGACY));
            } else {
                guide.setText(Html.fromHtml(Config.DATA.infos.get(position).getText()));
                guide2.setText(Html.fromHtml(Config.DATA.infos.get(position).getText_two()));
            }

            Glide.with(this).load(Config.DATA.infos.get(position).getImage()).addListener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    animationView.setVisibility(View.GONE);
                    return false;
                }
            }).thumbnail(0.25f).into(imageView);

        }

        initialize();
        

       
    }

    private void initialize() {
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in_anime);
        Animation fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);

        ImageView Top = findViewById(R.id.top);
        LinearLayout content = findViewById(R.id.content);

        new Handler().postDelayed(() -> {

            Top.startAnimation(fall_less_animation);
            Top.setVisibility(View.VISIBLE);

            title.startAnimation(fall_less_animation);
            title.setVisibility(View.VISIBLE);

        }, 500);


        new Handler().postDelayed(() -> {
            content.startAnimation(fade_in);
            content.setVisibility(View.VISIBLE);
        }, 1000);

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

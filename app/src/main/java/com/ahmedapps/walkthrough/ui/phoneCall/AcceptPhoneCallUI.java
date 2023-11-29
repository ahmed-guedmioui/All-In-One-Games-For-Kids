package com.ahmedapps.walkthrough.ui.phoneCall;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmedapps.walkthrough.BuildConfig;
import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.R;


import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

public class AcceptPhoneCallUI extends AppCompatActivity {

    ImageView finish_call;
    ImageView send_app;
    TextView nam_hero2, num_hero2;
    MediaPlayer hero_song;

    

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_accept_phone_call);


        Config.mediaPlayerManager.pause();

        ViewsAndDialogs viewsAndDialogs = new ViewsAndDialogs(this, this);

        
        

        playVoice();

        send_app = findViewById(R.id.send_app);

        nam_hero2 = findViewById(R.id.name);
        num_hero2 = findViewById(R.id.num);
        nam_hero2.setText(getString(R.string.Chat_name));
        num_hero2.setText(getString(R.string.NumberPhone));


        finish_call = findViewById(R.id.finish_call);

        viewsAndDialogs.clickAnimation(finish_call, view -> {
            performAction();
        });

        send_app.setOnClickListener(v -> {
            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                String shareMessage = "\nLet me recommend you this application, it's so useful and fun!\n\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }

        });

    }


    @Override
    public void onBackPressed() {
        performAction();
    }


    public void playVoice() {
        hero_song = MediaPlayer.create(getBaseContext(), R.raw.phone_call);
        try {
            hero_song.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }

        hero_song.start();
        hero_song.setOnCompletionListener(mediaPlayer -> {
            playVoice();
        });
    }

    private void performAction() {
        finish();
        hero_song.stop();
        hero_song.release();
        hero_song = null;
        
    }

    

    

   


    @Override
    protected void onResume() {
        super.onResume();

        
    }

}

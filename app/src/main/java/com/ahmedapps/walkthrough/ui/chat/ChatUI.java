package com.ahmedapps.walkthrough.ui.chat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.ahmedapps.walkthrough.BuildConfig;
import com.ahmedapps.walkthrough.R;


import com.ahmedapps.walkthrough.ui.phoneCall.WaitingPhoneCallUI;
import com.ahmedapps.walkthrough.ui.videoCall.WaitingVideoCallUI;
import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

import java.util.ArrayList;
import java.util.List;

public class ChatUI extends AppCompatActivity implements View.OnClickListener {

    MediaPlayer SonMessage;
    ImageView Calling_voice, Calling_video;
    CardView ReStart;
    ScrollView mScrollView;
    Animation mAnimation;
    String GooglePlay = "http://play.google.com/store/apps/details?id=";

    Button msg1, msg2, msg3, msg4, msg5, msg6, msg7, msg8, msg9, msg10, msg11, msg12, msg13, msg14, msg15;

    LinearLayout mLinearLayout_1, mLinearLayout_2, mLinearLayout_3, mLinearLayout_4, mLinearLayout_5, mLinearLayout_6,
            mLinearLayout_7, mLinearLayout_8, mLinearLayout_9, mLinearLayout_10, mLinearLayout_11, mLinearLayout_12,
            mLinearLayout_13, mLinearLayout_14, mLinearLayout_15;

    LinearLayout FLinearLayout_1, FLinearLayout_2, FLinearLayout_3, FLinearLayout_4, FLinearLayout_5, FLinearLayout_6,
            FLinearLayout_7, FLinearLayout_8, FLinearLayout_9, FLinearLayout_10, FLinearLayout_11, FLinearLayout_12,
            FLinearLayout_13, FLinearLayout_14, FLinearLayout_15;

    FrameLayout mFrameLayout1, mFrameLayout2, mFrameLayout3, mFrameLayout4, mFrameLayout5, mFrameLayout6, mFrameLayout7,
            mFrameLayout8, mFrameLayout9, mFrameLayout10, mFrameLayout11, mFrameLayout12, mFrameLayout13,
            mFrameLayout14, mFrameLayout15;


    private int action;

    private ViewsAndDialogs viewsAndDialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_chat);

        viewsAndDialogs = new ViewsAndDialogs(this, this);


        InitializeMsgSend();
        InitializeLayout();
        InitializeFramelayout();


        Calling_voice = findViewById(R.id.call_famous);
        Calling_video = findViewById(R.id.video_famous);

        ReStart = findViewById(R.id.StartChatAgain);

        mAnimation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.chat_anime);
        mAnimation.setStartOffset(0);

        viewsAndDialogs.clickAnimation(Calling_voice, view -> {
            action = 1;
            performAction();
        });

        viewsAndDialogs.clickAnimation(Calling_video, view -> {
            action = 2;
            if (checkPermissionsCameraVideoCall()) {
                performAction();
            }
        });

        viewsAndDialogs.clickAnimation(ReStart, view -> {
            RestartChat();
        });

        FLinearLayout_10.setOnClickListener(v -> {
            try {
                Intent localIntent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID));
                startActivity(localIntent);
            } catch (ActivityNotFoundException localActivityNotFoundException) {
                startActivity(new Intent("android.intent.action.VIEW", Uri.parse(GooglePlay + BuildConfig.APPLICATION_ID)));
            }

        });


        initialize();
    }

    private void initialize() {
        Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in_anime);
        Animation fall_less_animation = AnimationUtils.loadAnimation(this, R.anim.fall_anime_less);

        TextView text = findViewById(R.id.text);
        LinearLayout linear_msg_button = findViewById(R.id.linear_msg_button);

        new Handler().postDelayed(() -> {

            text.startAnimation(fall_less_animation);
            text.setVisibility(View.VISIBLE);

            linear_msg_button.startAnimation(fade_in);
            linear_msg_button.setVisibility(View.VISIBLE);

        }, 500);

    }

    private boolean checkPermissionsCameraVideoCall() {
        int permissionCAMERA = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        List<String> listPermissionsNeeded = new ArrayList<>();

        if (permissionCAMERA != PackageManager.PERMISSION_GRANTED) {

            listPermissionsNeeded.add(Manifest.permission.CAMERA);
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this,
                    listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), 1);
            return false;
        }
        return true;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (ActivityCompat.checkSelfPermission(this, permissions[0]) == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 1) {
                Config.IS_CAMERA_PERMISSION_AVAILABLE = true;
                startActivity(new Intent(getApplicationContext(), WaitingVideoCallUI.class));

            }
            Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
        } else {
            Config.IS_CAMERA_PERMISSION_AVAILABLE = false;
            Toast.makeText(this, "Ops.. Permission to access camera Denied", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), WaitingVideoCallUI.class));

        }
    }

    private void moveToVoice() {
        startActivity(new Intent(getApplicationContext(), WaitingPhoneCallUI.class));

    }

    private void moveToVideo() {
        startActivity(new Intent(getApplicationContext(), WaitingVideoCallUI.class));

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.mesg1:
                mLinearLayout_1.setVisibility(View.VISIBLE);
                mFrameLayout1.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_1.setVisibility(View.VISIBLE);
                        FLinearLayout_1.startAnimation(mAnimation);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);

                break;

            case R.id.mesg2:

                mLinearLayout_2.setVisibility(View.VISIBLE);
                mFrameLayout2.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_2.setVisibility(View.VISIBLE);
                        FLinearLayout_2.startAnimation(mAnimation);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);

                break;

            case R.id.mesg3:
                mLinearLayout_3.setVisibility(View.VISIBLE);
                mFrameLayout3.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_3.setVisibility(View.VISIBLE);
                        FLinearLayout_3.startAnimation(mAnimation);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);

                break;
            case R.id.mesg4:
                mLinearLayout_4.setVisibility(View.VISIBLE);
                mFrameLayout4.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_4.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);

                break;

            case R.id.mesg5:
                mLinearLayout_5.setVisibility(View.VISIBLE);
                mFrameLayout5.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_5.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);

                break;

            case R.id.mesg6:
                mLinearLayout_6.setVisibility(View.VISIBLE);
                mFrameLayout6.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_6.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);
                break;
            case R.id.mesg7:
                mLinearLayout_7.setVisibility(View.VISIBLE);
                mFrameLayout7.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_7.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);
                break;

            case R.id.mesg8:
                mLinearLayout_8.setVisibility(View.VISIBLE);
                mFrameLayout8.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_8.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);
                break;
            case R.id.mesg9:
                mLinearLayout_9.setVisibility(View.VISIBLE);
                mFrameLayout9.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_9.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);
                break;

            case R.id.mesg10:
                mLinearLayout_10.setVisibility(View.VISIBLE);
                mFrameLayout10.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_10.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);
                break;
            case R.id.mesg11:
                mLinearLayout_11.setVisibility(View.VISIBLE);
                mFrameLayout11.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_11.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);
                break;
            case R.id.mesg12:
                mLinearLayout_12.setVisibility(View.VISIBLE);
                mFrameLayout12.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_12.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);
                break;

            case R.id.mesg13:
                mLinearLayout_13.setVisibility(View.VISIBLE);
                mFrameLayout13.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_13.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);
                break;

            case R.id.mesg14:
                mLinearLayout_14.setVisibility(View.VISIBLE);
                mFrameLayout14.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_14.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);
                break;
            case R.id.mesg15:
                mLinearLayout_15.setVisibility(View.VISIBLE);
                mFrameLayout15.setVisibility(View.GONE);
                view.postDelayed(new Runnable() {
                    public void run() {
                        FLinearLayout_15.setVisibility(View.VISIBLE);
                        scrollDown();
                        SoundMsg();
                    }
                }, 500);
                break;
        }

    }

    public void InitializeMsgSend() {
        msg1 = findViewById(R.id.mesg1);
        msg2 = findViewById(R.id.mesg2);
        msg3 = findViewById(R.id.mesg3);
        msg4 = findViewById(R.id.mesg4);
        msg5 = findViewById(R.id.mesg5);
        msg6 = findViewById(R.id.mesg6);
        msg7 = findViewById(R.id.mesg7);
        msg8 = findViewById(R.id.mesg8);
        msg9 = findViewById(R.id.mesg9);
        msg10 = findViewById(R.id.mesg10);
        msg11 = findViewById(R.id.mesg11);
        msg12 = findViewById(R.id.mesg12);
        msg13 = findViewById(R.id.mesg13);
        msg14 = findViewById(R.id.mesg14);
        msg15 = findViewById(R.id.mesg15);

        msg1.setOnClickListener(this);
        msg2.setOnClickListener(this);
        msg3.setOnClickListener(this);
        msg4.setOnClickListener(this);
        msg5.setOnClickListener(this);
        msg6.setOnClickListener(this);
        msg7.setOnClickListener(this);
        msg8.setOnClickListener(this);
        msg9.setOnClickListener(this);
        msg10.setOnClickListener(this);
        msg11.setOnClickListener(this);
        msg12.setOnClickListener(this);
        msg13.setOnClickListener(this);
        msg14.setOnClickListener(this);
        msg15.setOnClickListener(this);


    }

    public void InitializeLayout() {
        FLinearLayout_1 = findViewById(R.id.fdiscussion1);
        mLinearLayout_1 = findViewById(R.id.mdiscussion1);
        FLinearLayout_2 = findViewById(R.id.fdiscussion2);
        mLinearLayout_2 = findViewById(R.id.mdiscussion2);
        FLinearLayout_3 = findViewById(R.id.fdiscussion3);
        mLinearLayout_3 = findViewById(R.id.mdiscussion3);
        FLinearLayout_4 = findViewById(R.id.fdiscussion4);
        mLinearLayout_4 = findViewById(R.id.mdiscussion4);
        FLinearLayout_5 = findViewById(R.id.fdiscussion5);
        mLinearLayout_5 = findViewById(R.id.mdiscussion5);
        FLinearLayout_6 = findViewById(R.id.fdiscussion6);
        mLinearLayout_6 = findViewById(R.id.mdiscussion6);
        FLinearLayout_7 = findViewById(R.id.fdiscussion7);
        mLinearLayout_7 = findViewById(R.id.mdiscussion7);
        FLinearLayout_8 = findViewById(R.id.fdiscussion8);
        mLinearLayout_8 = findViewById(R.id.mdiscussion8);
        FLinearLayout_9 = findViewById(R.id.fdiscussion9);
        mLinearLayout_9 = findViewById(R.id.mdiscussion9);
        FLinearLayout_10 = findViewById(R.id.fdiscussion10);
        mLinearLayout_10 = findViewById(R.id.mdiscussion10);
        FLinearLayout_11 = findViewById(R.id.fdiscussion11);
        mLinearLayout_11 = findViewById(R.id.mdiscussion11);
        FLinearLayout_12 = findViewById(R.id.fdiscussion12);
        mLinearLayout_12 = findViewById(R.id.mdiscussion12);
        FLinearLayout_13 = findViewById(R.id.fdiscussion13);
        mLinearLayout_13 = findViewById(R.id.mdiscussion13);
        FLinearLayout_14 = findViewById(R.id.fdiscussion14);
        mLinearLayout_14 = findViewById(R.id.mdiscussion14);
        FLinearLayout_15 = findViewById(R.id.fdiscussion15);
        mLinearLayout_15 = findViewById(R.id.mdiscussion15);
    }

    public void InitializeFramelayout() {
        mFrameLayout1 = findViewById(R.id.Frame1);
        mFrameLayout2 = findViewById(R.id.Frame2);
        mFrameLayout3 = findViewById(R.id.Frame3);
        mFrameLayout4 = findViewById(R.id.Frame4);
        mFrameLayout5 = findViewById(R.id.Frame5);
        mFrameLayout6 = findViewById(R.id.Frame6);
        mFrameLayout7 = findViewById(R.id.Frame7);
        mFrameLayout8 = findViewById(R.id.Frame8);
        mFrameLayout9 = findViewById(R.id.Frame9);
        mFrameLayout10 = findViewById(R.id.Frame10);
        mFrameLayout11 = findViewById(R.id.Frame11);
        mFrameLayout12 = findViewById(R.id.Frame12);
        mFrameLayout13 = findViewById(R.id.Frame13);
        mFrameLayout14 = findViewById(R.id.Frame14);
        mFrameLayout15 = findViewById(R.id.Frame15);

    }

    public void ShowAllButtonAgain() {
        mFrameLayout1.setVisibility(View.VISIBLE);
        mFrameLayout2.setVisibility(View.VISIBLE);
        mFrameLayout3.setVisibility(View.VISIBLE);
        mFrameLayout4.setVisibility(View.VISIBLE);
        mFrameLayout5.setVisibility(View.VISIBLE);
        mFrameLayout6.setVisibility(View.VISIBLE);
        mFrameLayout7.setVisibility(View.VISIBLE);
        mFrameLayout8.setVisibility(View.VISIBLE);
        mFrameLayout9.setVisibility(View.VISIBLE);
        mFrameLayout10.setVisibility(View.VISIBLE);
        mFrameLayout11.setVisibility(View.VISIBLE);
        mFrameLayout12.setVisibility(View.VISIBLE);
        mFrameLayout13.setVisibility(View.VISIBLE);
        mFrameLayout14.setVisibility(View.VISIBLE);
        mFrameLayout15.setVisibility(View.VISIBLE);

    }

    public void ClearRoomChat() {
        mLinearLayout_1.setVisibility(View.GONE);
        FLinearLayout_1.setVisibility(View.GONE);
        mLinearLayout_2.setVisibility(View.GONE);
        FLinearLayout_2.setVisibility(View.GONE);
        mLinearLayout_3.setVisibility(View.GONE);
        FLinearLayout_3.setVisibility(View.GONE);
        mLinearLayout_4.setVisibility(View.GONE);
        FLinearLayout_4.setVisibility(View.GONE);
        mLinearLayout_5.setVisibility(View.GONE);
        FLinearLayout_5.setVisibility(View.GONE);
        mLinearLayout_6.setVisibility(View.GONE);
        FLinearLayout_6.setVisibility(View.GONE);
        mLinearLayout_7.setVisibility(View.GONE);
        FLinearLayout_7.setVisibility(View.GONE);
        mLinearLayout_8.setVisibility(View.GONE);
        FLinearLayout_8.setVisibility(View.GONE);
        mLinearLayout_9.setVisibility(View.GONE);
        FLinearLayout_9.setVisibility(View.GONE);
        mLinearLayout_10.setVisibility(View.GONE);
        FLinearLayout_10.setVisibility(View.GONE);
        mLinearLayout_11.setVisibility(View.GONE);
        FLinearLayout_11.setVisibility(View.GONE);
        mLinearLayout_12.setVisibility(View.GONE);
        FLinearLayout_12.setVisibility(View.GONE);
        mLinearLayout_13.setVisibility(View.GONE);
        FLinearLayout_13.setVisibility(View.GONE);
        mLinearLayout_14.setVisibility(View.GONE);
        FLinearLayout_14.setVisibility(View.GONE);
        mLinearLayout_15.setVisibility(View.GONE);
        FLinearLayout_15.setVisibility(View.GONE);


    }

    public void SoundMsg() {

        SonMessage = MediaPlayer.create(getBaseContext(), R.raw.ring_msg);
        try {
            SonMessage.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SonMessage.start();

    }

    public void RestartChat() {

        AlertDialog.Builder builder = new AlertDialog.Builder(ChatUI.this, R.style.CustomAlertDialogStyle);
        builder.setMessage("Do you want to restart Chat ?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    dialogInterface.dismiss();
                    action = 3;
                    performAction();
                })
                .setNegativeButton("No", (dialogInterface, i) -> {
                    dialogInterface.cancel();
                });
        builder.show();
    }

    public void scrollDown() {
        mScrollView = findViewById(R.id.ScrollRoom);
        mScrollView.postDelayed(new Runnable() {
            public void run() {
                mScrollView.fullScroll(130);
            }
        }, 10);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        finish();
    }


    private void performAction() {
        switch (action) {
            case 1:
                moveToVoice();
                break;
            case 2:
                moveToVideo();
                break;
            case 3:
                ShowAllButtonAgain();
                ClearRoomChat();
                break;
        }

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

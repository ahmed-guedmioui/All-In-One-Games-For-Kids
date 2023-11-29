package com.ahmedapps.walkthrough.ui.videoCall;

import android.app.Activity;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.VideoView;

import com.ahmedapps.walkthrough.R;


import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

import java.util.List;

public class AcceptVideoCallUI extends Activity implements SurfaceHolder.Callback {

    Camera mCamera;
    SurfaceView mPreview;
    int cameraCount;
    ImageView FinishCall;
    VideoView VideoPlay;
    ImageView ImageCheckVideo;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.ui_accept_video_call);

        Config.mediaPlayerManager.pause();

        ViewsAndDialogs viewsAndDialogs = new ViewsAndDialogs(this, this);


        FinishCall = findViewById(R.id.finish_video);
        ImageCheckVideo = findViewById(R.id.imgCheckvideo);
        VideoPlay = findViewById(R.id.video_play);

        if (Config.IS_CAMERA_PERMISSION_AVAILABLE)
            initializeCameraShow();

        VideoStart();
        viewsAndDialogs.clickAnimation(FinishCall, view -> {
            performAction();
        });

    }


    public void initializeCameraShow() {
        mPreview = (SurfaceView) findViewById(R.id.surfaceCam);
        mPreview.getHolder().addCallback(this);
        mPreview.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        VideoCam();

    }

    public void VideoCam() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int FacingCam = 0; FacingCam < cameraCount; FacingCam++) {
            Camera.getCameraInfo(FacingCam, cameraInfo);
            if (cameraInfo.facing == 1) {
                try {
                    mCamera = Camera.open(FacingCam);
                } catch (RuntimeException e) {
                    //do nothing
                }
            }
        }

    }

    @Override
    public void onBackPressed() {
        performAction();
    }

    public void VideoStart() {
        VideoPlay.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_call));
        VideoPlay.setOnPreparedListener(mp -> mp.setLooping(true));
        VideoPlay.start();

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        if (Config.IS_CAMERA_PERMISSION_AVAILABLE) {
            try {
                Camera.Parameters params = mCamera.getParameters();
                List<Camera.Size> sizes = params.getSupportedPreviewSizes();
                Camera.Size selected = sizes.get(0);
                params.setPreviewSize(selected.width, selected.height);
                mCamera.setParameters(params);
                mCamera.setDisplayOrientation(90);
                mCamera.setPreviewDisplay(holder);
                mCamera.startPreview();
            } catch (Exception e0) {
                //error to Initialize Camera
            }
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        if (Config.IS_CAMERA_PERMISSION_AVAILABLE) {
            try {
                mCamera.setPreviewDisplay(mPreview.getHolder());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.i("PREVIEW", "surfaceDestroyed");
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Config.IS_CAMERA_PERMISSION_AVAILABLE)
            mCamera.stopPreview();
    }


    private void performAction() {
        finish();
        VideoPlay.pause();
        VideoPlay = null;

    }


    @Override
    protected void onResume() {
        super.onResume();


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Config.IS_CAMERA_PERMISSION_AVAILABLE)
            mCamera.release();
    }
}






package com.ahmedapps.walkthrough.ui.fragmants;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.ahmedapps.walkthrough.R;
import com.ahmedapps.walkthrough.ui.FragmentsHolderUI;
import com.ahmedapps.walkthrough.ui.chat.ChatUI;
import com.ahmedapps.walkthrough.ui.phoneCall.WaitingPhoneCallUI;
import com.ahmedapps.walkthrough.ui.videoCall.WaitingVideoCallUI;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

public class FragmentCalls extends Fragment {


    private boolean isFirstTime = true;
    private View view;

    private Animation fall_less_animation;

    private int action;

    private ViewsAndDialogs viewsAndDialogs;

    public FragmentCalls() {
        super(R.layout.fragment_calls);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        this.view = view;


        viewsAndDialogs = new ViewsAndDialogs(getContext(), getActivity());

        fall_less_animation = AnimationUtils.loadAnimation(getContext(), R.anim.fall_anime_less);

        initialize();
        viewsAndDialogs.menuDialog(view);

        buttons();
        onBackPressed();
    }

    private void buttons() {
        CardView v_call_btn = view.findViewById(R.id.v_call_btn);
        CardView ph_call_btn = view.findViewById(R.id.ph_call_btn);
        CardView chat_btn = view.findViewById(R.id.chat_btn);

        viewsAndDialogs.clickAnimationBig(v_call_btn, v -> {
            action = 1;
            performAction();
        });

        viewsAndDialogs.clickAnimationBig(ph_call_btn, v -> {
            action = 2;
            performAction();
        });

        viewsAndDialogs.clickAnimationBig(chat_btn, v -> {
            action = 3;
            performAction();
        });
    }

    private void performAction() {
        navigate();
    }

    private void navigate() {
        if (action == 1)
            startActivity(new Intent(getContext(), WaitingVideoCallUI.class));
        else if (action == 2)
            startActivity(new Intent(getContext(), WaitingPhoneCallUI.class));
        else
            startActivity(new Intent(getContext(), ChatUI.class));

    }

    private void initialize() {
        TextView text = view.findViewById(R.id.text);

        if (isFirstTime) {
            isFirstTime = false;

            new Handler().postDelayed(() -> {
                text.startAnimation(fall_less_animation);
                text.setVisibility(View.VISIBLE);
            }, 500);

        } else
            text.setVisibility(View.VISIBLE);
    }

    private void onBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getActivity() != null)
                    getActivity().finish();
            }
        });
    }

}

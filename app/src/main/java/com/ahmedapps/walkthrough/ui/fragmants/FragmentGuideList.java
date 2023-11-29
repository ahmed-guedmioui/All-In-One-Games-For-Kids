package com.ahmedapps.walkthrough.ui.fragmants;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ahmedapps.walkthrough.R;
import com.ahmedapps.walkthrough.adapters.GuideAdapter;
import com.ahmedapps.walkthrough.ui.InfoUI;
import com.ahmedapps.walkthrough.utils.Config;
import com.ahmedapps.walkthrough.utils.Internet;
import com.ahmedapps.walkthrough.utils.ViewsAndDialogs;

public class FragmentGuideList extends Fragment implements GuideAdapter.ItemClickListener {

    private int position;
    private int clicksShowCount;
    private int clicks = 0;

    private Animation fall_less_animation, fade_in;
    private boolean isFirstTime = true;
    private boolean isFirstTimeRecycleView = true;
    private View view;

    public FragmentGuideList() {
        super(R.layout.fragmant_guide_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        this.view = view;

        ViewsAndDialogs viewsAndDialogs = new ViewsAndDialogs(getContext(), getActivity());

        fall_less_animation = AnimationUtils.loadAnimation(getContext(), R.anim.fall_anime_less);
        fade_in = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in_anime);

        clicksShowCount = Config.DATA.clicks;

        recyclerView();
        initialize();

        onBackPressed();

        viewsAndDialogs.menuDialog(view);
    }

    private void initialize() {

        ImageView Top = view.findViewById(R.id.top);
        TextView text = view.findViewById(R.id.text);

        if (isFirstTime) {
            isFirstTime = false;

            new Handler().postDelayed(() -> {
                Top.startAnimation(fade_in);
                Top.setVisibility(View.VISIBLE);
            }, 500);

            new Handler().postDelayed(() -> {
                text.startAnimation(fall_less_animation);
                text.setVisibility(View.VISIBLE);
            }, 1000);

        } else {
            Top.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onItemClick(View view, int position) {
        this.position = position;
        Internet internet = new Internet(getContext());

        clicks++;
        checkToPerformAction();

    }

    private void checkToPerformAction() {
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

    private void recyclerView() {

        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        if (isFirstTimeRecycleView) {
            isFirstTimeRecycleView = false;

            new Handler().postDelayed(() -> {
                recyclerView.startAnimation(fade_in);
                recyclerView.setVisibility(View.VISIBLE);
            }, 500);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
        }

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        GuideAdapter guideAdapter = new GuideAdapter(getContext(), getActivity());
        guideAdapter.setClickListener(this);
        recyclerView.setAdapter(guideAdapter);
    }

    private void navigate() {
        Intent intent;

        intent = new Intent(getContext(), InfoUI.class);
        intent.putExtra("position", position);
        startActivity(intent);

    }

    private void onBackPressed() {
        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                clicksShowCount = Config.DATA.clicks;
                if (getActivity() != null)
                    getActivity().finish();
            }
        });
    }

}
















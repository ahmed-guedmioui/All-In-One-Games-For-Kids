package com.ahmedapps.walkthrough.ui;

import android.content.Intent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ahmedapps.walkthrough.R;
import com.ahmedapps.walkthrough.utils.Shared;

public class PrivacyTermsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_terms);

        CheckBox second_check = findViewById(R.id.second_check);
        Button accept_button = findViewById(R.id.accept_button);

        second_check.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                accept_button.setFocusable(true);
                accept_button.setClickable(true);
                accept_button.setAlpha(1f);
            } else {
                accept_button.setFocusable(false);
                accept_button.setClickable(false);
                accept_button.setAlpha(0.7f);
            }
        });

        accept_button.setOnClickListener(v -> {
            if (second_check.isChecked()) {
                Shared.setBoolean(PrivacyTermsActivity.this, "privacy", true);
                startActivity(new Intent(PrivacyTermsActivity.this, GenderUI.class));
            }
        });

    }

}
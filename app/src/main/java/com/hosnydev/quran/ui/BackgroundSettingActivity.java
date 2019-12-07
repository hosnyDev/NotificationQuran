package com.hosnydev.quran.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.gw.swipeback.SwipeBackLayout;
import com.hosnydev.quran.R;

public class BackgroundSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_background);

        // finish activity when swipe left
        SwipeBackLayout mSwipeBackLayout = findViewById(R.id.swipeBackLayout);
        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_LEFT);

        final RadioButton radio1 = findViewById(R.id.radio1);
        final RadioButton radio2 = findViewById(R.id.radio2);

        boolean isTrue = getSharedPreferences("background", MODE_PRIVATE).getBoolean("w", false);
        if (isTrue) {
            radio2.setChecked(true);
        } else {
            radio1.setChecked(true);
        }

        findViewById(R.id.layout1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radio1.setChecked(true);
                radio2.setChecked(false);
                getSharedPreferences("background", MODE_PRIVATE)
                        .edit()
                        .putBoolean("w", false)
                        .apply();

                goToHome();
            }
        });

        findViewById(R.id.layout2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                radio1.setChecked(false);
                radio2.setChecked(true);
                getSharedPreferences("background", MODE_PRIVATE)
                        .edit()
                        .putBoolean("w", true)
                        .apply();

                goToHome();
            }
        });

    }

    private void goToHome() {
        Toast.makeText(BackgroundSettingActivity.this, "تم الحفظ", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(BackgroundSettingActivity.this, MainActivity.class));
        finish();
    }
}

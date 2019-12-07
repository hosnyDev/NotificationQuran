package com.hosnydev.quran.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.gw.swipeback.SwipeBackLayout;
import com.hosnydev.quran.R;
import com.hosnydev.quran.notification.Alarm;

public class NotificationSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_setting);

        // finish activity when swipe left
        SwipeBackLayout mSwipeBackLayout = findViewById(R.id.swipeBackLayout);
        mSwipeBackLayout.setDirectionMode(SwipeBackLayout.FROM_LEFT);

        // findView
        TextView tx = findViewById(R.id.textview1);
        Spinner spinnerTime = findViewById(R.id.spinnerTime);
        final Switch switchTime = findViewById(R.id.switchTime);
        final LinearLayout layoutSpinner = findViewById(R.id.layoutSpinner);

        //set custom font
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/a.ttf");
        tx.setTypeface(custom_font);

        //get state of time in shared pref
        boolean isTrue = getSharedPreferences("notification", MODE_PRIVATE).getBoolean("state", false);
        if (isTrue) {
            layoutSpinner.setVisibility(View.VISIBLE);
            switchTime.setChecked(true);
            switchTime.setText("تم التشغيل");
        } else {
            layoutSpinner.setVisibility(View.INVISIBLE);
            switchTime.setChecked(false);
            switchTime.setText("تم الايقاف");
        }

        // get position of time selected spinner from shared pref
        int positionSpinner = getSharedPreferences("notification", MODE_PRIVATE).getInt("time", 0);
        if (positionSpinner != -1) {
            spinnerTime.setSelection(positionSpinner);
        } else {
            spinnerTime.setSelection(0);
        }

        // set On Change Listener to switch
        switchTime.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    layoutSpinner.setVisibility(View.VISIBLE);

                    getSharedPreferences("notification", MODE_PRIVATE)
                            .edit()
                            .putBoolean("state", true)
                            .apply();
                    switchTime.setText("تم التشغيل");

                    Alarm alarm = new Alarm();
                    alarm.setAlarm(NotificationSettingActivity.this);

                    Toast.makeText(NotificationSettingActivity.this, "تم التشغيل", Toast.LENGTH_SHORT).show();

                } else {
                    layoutSpinner.setVisibility(View.INVISIBLE);
                    getSharedPreferences("notification", MODE_PRIVATE)
                            .edit()
                            .putBoolean("state", false)
                            .apply();
                    switchTime.setText("تم الايقاف");


                    Alarm alarm = new Alarm();
                    alarm.cancelAlarm(NotificationSettingActivity.this);

                    Toast.makeText(NotificationSettingActivity.this, "تم الايقاف", Toast.LENGTH_SHORT).show();

                }

            }
        });

        // set on item selected of spinner to save state in shared pref
        spinnerTime.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getSharedPreferences("notification", MODE_PRIVATE)
                        .edit()
                        .putInt("time", position)
                        .apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

}

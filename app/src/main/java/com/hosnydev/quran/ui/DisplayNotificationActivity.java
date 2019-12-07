package com.hosnydev.quran.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.hosnydev.quran.R;

public class DisplayNotificationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notification);

        // get intent
        Intent intent = getIntent();
        TextView notificationName = findViewById(R.id.notificationName);
        notificationName.setText(intent.getStringExtra("msg"));
        getSupportActionBar().setTitle(intent.getStringExtra("title"));

    }
}

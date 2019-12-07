package com.hosnydev.quran.ui;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.hosnydev.quran.R;
import com.hosnydev.quran.adapter.AdapterQuran;
import com.hosnydev.quran.adapter.AdapterQuranName;
import com.hosnydev.quran.data.QuranList;
import com.hosnydev.quran.data.QuranListName;
import com.hosnydev.quran.model.Model;
import com.hosnydev.quran.notification.Alarm;
import com.hosnydev.quran.utils.RecyclerItemClickListener;
import com.warkiz.widget.IndicatorSeekBar;
import com.warkiz.widget.OnSeekChangeListener;
import com.warkiz.widget.SeekParams;

import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    // recyclerView
    private RecyclerView recyclerView;
    private LinearLayoutManager mLayoutManager;
    private AdapterQuran adapterQuran;


    private Toolbar toolbar;
    private List<String> stringListQuranName;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // findView
        recyclerView = findViewById(R.id.recycler);

        /*
            toolbar
         */
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        /*
            drawer
         */
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

        initQuranRecyclerName();

        /*
            First open app
         */
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean firstOpened = preferences.getBoolean("first_opened", true);
        if (firstOpened) {
            SharedPreferences.Editor editor = preferences.edit();
            editor.putBoolean("first_opened", false);
            editor.apply();

            Alarm alarm = new Alarm();
            alarm.setAlarm(MainActivity.this);

            getSharedPreferences("notification", MODE_PRIVATE)
                    .edit()
                    .putBoolean("state", true)
                    .putInt("time", 0)
                    .putInt("srcFile", 0)
                    .putInt("positionName", 0)
                    .apply();
        }

    }

    private void initQuranRecyclerName() {

        // recyclerView quran name
        NavigationView navigationView = findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);

        SearchView searchView = hView.findViewById(R.id.search_bar_sor);
        final RecyclerView recyclerViewSor = hView.findViewById(R.id.recyclerViewSor);

        recyclerViewSor.setHasFixedSize(true);
        recyclerViewSor.setLayoutManager(new GridLayoutManager(this, 2));

        List<Model> listQuranName = new ArrayList<>();
        stringListQuranName = new ArrayList<>();

        // fill list of quran Name
        stringListQuranName = new QuranListName(this, stringListQuranName).getList();

        for (int i = 0; i < stringListQuranName.size(); i++) {
            listQuranName.add(new Model(stringListQuranName.get(i)));
        }

        // set recycler quran
        initQuranRecycler();

        final AdapterQuranName adapterQuranName = new AdapterQuranName(listQuranName);

        //set adapter to recyclerView
        recyclerViewSor.setAdapter(adapterQuranName);

        // close key bad
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);

        // recyclerOnClick
        recyclerViewSor.addOnItemTouchListener(
                new RecyclerItemClickListener(this, recyclerViewSor, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int i) {

                        drawer.closeDrawers();
                        getSharedPreferences("quranNum", MODE_PRIVATE)
                                .edit()
                                .putInt("num", i)
                                .apply();
                        initQuranRecycler();

                    }

                    @Override
                    public void onLongItemClick(View view, int i) {
                    }
                })
        );

        // search filtter in recyclerView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterQuranName.getFilter().filter(newText);
                return true;
            }
        });
    }


    private void initQuranRecycler() {

        // get list
        List<Model> listQuran = new ArrayList<>();

        List<String> stringListQuran = new ArrayList<>();

        stringListQuran = new QuranList(this, stringListQuran).getList();

        for (int i = 0; i < stringListQuran.size(); i++) {
            // add list from buffer reader
            listQuran.add(new Model(stringListQuran.get(i)));
        }

        // recycler quran
        adapterQuran = new AdapterQuran(listQuran);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(adapterQuran);

        // set color them
        boolean isTrue = getSharedPreferences("background", MODE_PRIVATE).getBoolean("w", false);
        if (isTrue) {
            adapterQuran.setTextColor(getResources().getColor(R.color.white));
            adapterQuran.setBackColor(getResources().getColor(R.color.blake));
            LinearLayout back = findViewById(R.id.back);
            recyclerView.setBackgroundColor(getResources().getColor(R.color.blake));
            back.setBackgroundColor(getResources().getColor(R.color.blake));
        } else {
            adapterQuran.setTextColor(getResources().getColor(R.color.blake));
            adapterQuran.setBackColor(getResources().getColor(R.color.white));
            LinearLayout back = findViewById(R.id.back);
            recyclerView.setBackgroundColor(getResources().getColor(R.color.white));
            back.setBackgroundColor(getResources().getColor(R.color.white));
        }

        // set text size
        SharedPreferences preferences = getSharedPreferences("fonts", MODE_PRIVATE);
        int fontSize = preferences.getInt("size", 18);
        if (fontSize > 0)
            adapterQuran.setTextSizes(fontSize);
        else
            adapterQuran.setTextSizes(18);

        // set title to tool_bar
        int quranNum = getSharedPreferences("quranNum", MODE_PRIVATE).getInt("num", 0);
        if (quranNum != -1)
            toolbar.setTitle(stringListQuranName.get(quranNum));
        else
            toolbar.setTitle(stringListQuranName.get(0));

    }

    @Override
    public void onPause() {
        super.onPause();
        //read current recyclerView position
        // last position visible
        int index = mLayoutManager.findFirstVisibleItemPosition();
        View v = recyclerView.getChildAt(0);
        int top = (v == null) ? 0 : (v.getTop() - recyclerView.getPaddingTop());
        getSharedPreferences("a", MODE_PRIVATE)
                .edit()
                .putInt("1", index)
                .putInt("2", top)
                .apply();
    }

    @Override
    public void onResume() {
        super.onResume();
        //set recyclerView position
        SharedPreferences preferences = getSharedPreferences("a", MODE_PRIVATE);
        int in = preferences.getInt("1", 0);
        int tp = preferences.getInt("2", 0);
        if (in <= adapterQuran.getItemCount() && in != -1) {
            mLayoutManager.scrollToPositionWithOffset(in, tp);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_Notification:
                startActivity(new Intent(MainActivity.this, NotificationSettingActivity.class));

                break;

            case R.id.action_settings:
                showAlertSetting();
                break;

            case R.id.action_background:
                startActivity(new Intent(MainActivity.this, BackgroundSettingActivity.class));
                break;

            case R.id.action_rateApp:

                final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                }
                break;

            default:
                return false;
        }
        return true;
    }

    private void showAlertSetting() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this, R.style.CustomDialog);

        LayoutInflater inflater = getLayoutInflater();
        View view1 = inflater.inflate(R.layout.dialog_setting_font, null);
        dialog.setContentView(view1);
        dialog.setCancelable(false);

        IndicatorSeekBar seekBar = view1.findViewById(R.id.seekBar);


        seekBar.setOnSeekChangeListener(new OnSeekChangeListener() {
            @Override
            public void onSeeking(SeekParams seekParams) {

                int progress = seekParams.progress;

                // set text size
                adapterQuran.setTextSizes(progress);

                getSharedPreferences("fonts", MODE_PRIVATE)
                        .edit()
                        .putInt("size", progress)
                        .apply();
            }

            @Override
            public void onStartTrackingTouch(IndicatorSeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(IndicatorSeekBar seekBar) {
            }
        });

        view1.findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    // onBackPressed
    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finishAffinity();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "اضغط مرة اخره للخروج", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        boolean alarmUp = (PendingIntent.getBroadcast(this, 0,
                new Intent(this, Alarm.class),
                PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmUp) {
            Alarm alarm = new Alarm();
            alarm.setAlarm(this);
        }
    }

}
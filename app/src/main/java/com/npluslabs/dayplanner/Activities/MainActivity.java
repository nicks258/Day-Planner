package com.npluslabs.dayplanner.Activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;


import com.crashlytics.android.Crashlytics;
import com.google.firebase.crash.FirebaseCrash;
import com.idescout.sql.SqlScoutServer;
import com.npluslabs.dayplanner.Fragments.DashboardFragment;
import com.npluslabs.dayplanner.R;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity  {
    public static final String HIGHESTSCORE = "hightestScores" ;
    SharedPreferences sharedpreferences;
//    private TextView mTextMessage;

//    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
//            = new BottomNavigationView.OnNavigationItemSelectedListener() {
//        Fragment selectedFragment;
//        @Override
//        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//            switch (item.getItemId()) {
//                case R.id.navigation_home: {
//                    selectedFragment = new AddTaskFragment();
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.content,selectedFragment);
//                    fragmentTransaction.commit();
//                    return true;
//                }
//                case R.id.navigation_dashboard: {
//                    selectedFragment = new DashboardFragment();
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.content,selectedFragment);
//                    fragmentTransaction.commit();
//                    return true;
//                }
//                case R.id.navigation_notifications:
////                    mTextMessage.setText(R.string.title_notifications);
//                    return true;
//            }
//            return false;
//        }
//
//    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SqlScoutServer.create(this, getPackageName());
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
//        Crashlytics.getInstance().crash();
//        FirebaseCrash.logcat(Log.ERROR, "MainActivity", "NPE caught");
////        FirebaseCrash.report("");
//        Crashlytics.log(1, "Test Crash", "its work");
//        Crashlytics.log("crash");
//        final Fabric fabric = new Fabric.Builder(this)
//                .kits(new Crashlytics())
//                .debuggable(true)           // Enables Crashlytics debugger
//                .build();
//        Fabric.with(fabric, new Crashlytics[]{new Crashlytics()});

//        mTextMessage = (TextView) findViewById(R.id.message);
//        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        DashboardFragment selectedFragment = new DashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,selectedFragment);
        fragmentTransaction.commit();
        sharedpreferences = getSharedPreferences(HIGHESTSCORE, Context.MODE_PRIVATE);
        FirebaseCrash.log("Activity created");


//        final View activityRootView = findViewById(R.id.content);
//        activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int heightDiff = activityRootView.getRootView().getHeight() - activityRootView.getHeight();
//                if (heightDiff > dpToPx(MainActivity.this, 200)) { // if more than 200 dp, it's probably a keyboard...
//                    // ... do something here
//                    navigation.setVisibility(View.GONE);
//                }
//                else {
//                    navigation.setVisibility(View.VISIBLE);
//                }
//            }
//        });
    }
    public static float dpToPx(Context context, float valueInDp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics);
    }
    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }
//    @Override
//    public void onBackPressed(){
//        android.app.FragmentManager fm = getFragmentManager();
//        if (fm.getBackStackEntryCount() > 0) {
//            Log.i("MainActivity", "popping backstack");
//            fm.popBackStack();
//        } else {
//            Log.i("MainActivity", "nothing on backstack, calling super");
//            super.onBackPressed();
//        }
//    }

}

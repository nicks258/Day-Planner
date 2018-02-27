package com.npluslabs.dayplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;

import com.idescout.sql.SqlScoutServer;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);

//        mTextMessage = (TextView) findViewById(R.id.message);
//        final BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
//        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        DashboardFragment selectedFragment = new DashboardFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content,selectedFragment);
        fragmentTransaction.commit();
        sharedpreferences = getSharedPreferences(HIGHESTSCORE, Context.MODE_PRIVATE);
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
}

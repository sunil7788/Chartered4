package com.chartered4;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;

import com.chartered4.databinding.ActivityIntroBinding;
import com.chartered4.models.IntroBean;
import com.chartered4.utils.AppDialogs;
import com.chartered4.utils.AppUtils;
import com.chartered4.utils.SharedObjects;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class IntroActivity extends AppCompatActivity implements View.OnClickListener {

    SharedObjects sharedObjects;

    ActivityIntroBinding binding;

    IntroViewPagerAdapter viewPagerAdapter;

    private static int currentPage = 0;
    private static final int NUM_PAGES = 3;

    final long DELAY_MS = 3000;
    final long PERIOD_MS = 3500;

    ArrayList<IntroBean> arrSlider = new ArrayList();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        sharedObjects = new SharedObjects(IntroActivity.this);

        setListeners();

        arrSlider.add(new IntroBean(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_slider), ""));
        arrSlider.add(new IntroBean(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_slider),""));
        arrSlider.add(new IntroBean(ContextCompat.getDrawable(IntroActivity.this, R.drawable.ic_slider), ""));

        viewPagerAdapter = new IntroViewPagerAdapter(IntroActivity.this, arrSlider);
        binding.viewPager.setAdapter(viewPagerAdapter);
        binding.dotsIndicator.attachTo(binding.viewPager);

        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable update = new Runnable() {
            @Override
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                binding.viewPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, DELAY_MS, PERIOD_MS);
    }

    private void setListeners() {
        binding.btnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        if (viewId == R.id.btnStart){
            if (AppUtils.isNetworkConnected(IntroActivity.this)) {
                Intent intent = new Intent(IntroActivity.this, LoginSignupActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(IntroActivity.this,binding.imgLogo, getString(R.string.transitionAppLogo)).toBundle());
            } else {
                AppDialogs.showAlertDialog(getString(R.string.internet_msg), IntroActivity.this);
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

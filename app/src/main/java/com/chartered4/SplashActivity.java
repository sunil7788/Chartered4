package com.chartered4;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.transition.Explode;
import androidx.transition.Transition;

import com.chartered4.databinding.ActivitySplashBinding;
import com.chartered4.utils.AppConstants;
import com.chartered4.utils.AppUtils;
import com.chartered4.utils.SharedObjects;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 3000;
    SharedObjects sharedObjects;

    String loginStatus;

    ActivitySplashBinding binding;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        sharedObjects = new SharedObjects(SplashActivity.this);

        binding.txtVersionName.setText(getString(R.string.version, AppUtils.getVersion(SplashActivity.this)));

        loginStatus = sharedObjects.getPreference(AppConstants.PreferenceConstants.STATUS);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intentLogin;
                if (TextUtils.isEmpty(loginStatus)) {
                    intentLogin = new Intent(SplashActivity.this, IntroActivity.class);
                } else if (loginStatus.equals(AppConstants.PreferenceConstants.STATUS_LOGOUT)) {
                    intentLogin = new Intent(SplashActivity.this, IntroActivity.class);
                } else {
                    intentLogin = new Intent(SplashActivity.this, MainActivity.class);
                    intentLogin.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                }

                startActivity(intentLogin,
                        ActivityOptions.makeSceneTransitionAnimation(SplashActivity.this,
                                binding.imgLogo, getString(R.string.transitionAppLogo)).toBundle());
                finish();
            }
        }, SPLASH_TIME_OUT);

    }

}

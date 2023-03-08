package com.chartered4;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;

import com.chartered4.add_listing.AddListingActivity;
import com.chartered4.bookings.BookingsFragment;
import com.chartered4.home.HomeFragment;
import com.chartered4.inquiries.InquiriesFragment;
import com.chartered4.listings.ListingsFragment;
import com.chartered4.utils.AppConstants;
import com.chartered4.utils.AppDialogs;
import com.chartered4.utils.AppUtils;
import com.chartered4.utils.SharedObjects;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.chartered4.databinding.ActivityMainBinding;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityMainBinding binding;

    private static final int IN_APP_REQUEST_CODE = 147;
    AppUpdateManager appUpdateManager;

    private boolean doubleBackToExitPressedOnce = false;

    SharedObjects sharedObjects;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        sharedObjects = new SharedObjects(MainActivity.this);

        /*binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        setListeners();

        loadFragment(new HomeFragment());

        if (AppUtils.isNetworkConnected(MainActivity.this)) {
            inAppUpdate();
        }
    }

    private void setListeners() {
        binding.bottomNavigationView.setOnItemSelectedListener(onBottomItemSelectedListener);
        binding.cardAddListing.setOnClickListener(this);
    }

    public void selectMenuItem(String menu) {
        if (menu.equals(getString(R.string.home))) {
            binding.bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
        } else if (menu.equals(getString(R.string.inquiries))) {
            binding.bottomNavigationView.getMenu().findItem(R.id.nav_inquiries).setChecked(true);
        } else if (menu.equals(getString(R.string.bookings))) {
            binding.bottomNavigationView.getMenu().findItem(R.id.nav_bookings).setChecked(true);
        } else if (menu.equals(getString(R.string.listings))) {
            binding.bottomNavigationView.getMenu().findItem(R.id.nav_listings).setChecked(true);
        } else {
            binding.bottomNavigationView.getMenu().findItem(R.id.nav_home).setChecked(true);
            binding.bottomNavigationView.getMenu().findItem(R.id.nav_inquiries).setChecked(false);
            binding.bottomNavigationView.getMenu().findItem(R.id.nav_bookings).setChecked(false);
            binding.bottomNavigationView.getMenu().findItem(R.id.nav_listings).setChecked(false);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.doubleBackToExitPressedOnce = false;
        Log.e("On Res", "called");

//        changeNavigationMenu();

        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.flContent);
        if (currentFragment instanceof HomeFragment) {
            selectMenuItem(getString(R.string.home));
        } else if (currentFragment instanceof InquiriesFragment) {
            selectMenuItem(getString(R.string.inquiries));
        } else if (currentFragment instanceof BookingsFragment) {
            selectMenuItem(getString(R.string.bookings));
        } else if (currentFragment instanceof ListingsFragment) {
            selectMenuItem(getString(R.string.listings));
        }
    }

    private NavigationBarView.OnItemSelectedListener onBottomItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.nav_home) {
                loadFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.nav_inquiries) {
                loadFragment(new InquiriesFragment());
            } else if (item.getItemId() == R.id.nav_bookings) {
                loadFragment(new BookingsFragment());
            } else if (item.getItemId() == R.id.nav_listings) {
                loadFragment(new ListingsFragment());
            }
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void inAppUpdate() {
        // Creates instance of the manager.
        appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);

        // Returns an intent object that you use to check for an update.
        Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();

// Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    // For a flexible update, use AppUpdateType.FLEXIBLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                // Request the update.
                requestInAppUpdate(appUpdateInfo);
            }
        });
    }

    private void requestInAppUpdate(AppUpdateInfo appUpdateInfo) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                    // Pass the intent that is returned by 'getAppUpdateInfo()'.
                    appUpdateInfo,
                    // Or 'AppUpdateType.FLEXIBLE' for flexible updates.
                    AppUpdateType.IMMEDIATE,
                    // The current activity making the update request.
                    this,
                    // Include a request code to later monitor this update request.
                    IN_APP_REQUEST_CODE);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
            if (doubleBackToExitPressedOnce) {
//                AppConstants.USE_CURRENT_LOCATION = true;
//                AppConstants.IS_FOR_FIRST_TIME = true;

                finish();
                System.exit(0);
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            AppDialogs.showSnackBarAutoHide(getString(R.string.exit), binding.bottomNavigationView);
//            Toast.makeText(this, getString(R.string.exit), Toast.LENGTH_SHORT).show();

            new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);

        } else {
            super.onBackPressed();
        }
    }

    public void loadFragment(Fragment fragment) {

        String backStateName = fragment.getClass().getName();
//        String fragmentTag = backStateName;

        FragmentManager manager = getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);

        if (!fragmentPopped && manager.findFragmentByTag(backStateName) == null) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.flContent, fragment, backStateName);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();
        if (viewId == R.id.cardAddListing){
            if (AppUtils.isNetworkConnected(MainActivity.this)) {
                Intent intent = new Intent(MainActivity.this, AddListingActivity.class);
                startActivity(intent,
                        ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
            } else {
                AppDialogs.showAlertDialog(getString(R.string.internet_msg), MainActivity.this);
            }
        }
    }

    public void removeAllPreferenceOnLogout() {
        sharedObjects.setPreference(AppConstants.PreferenceConstants.STATUS, AppConstants.PreferenceConstants.STATUS_LOGOUT);
        sharedObjects.removeSinglePreference(AppConstants.PreferenceConstants.USER_INFO);

        Intent intentLogin = new Intent(MainActivity.this, LoginSignupActivity.class);
        intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intentLogin,
                ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
//        overridePendingTransition(0, 0);
        finish();
    }
}
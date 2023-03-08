package com.chartered4.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.appcompat.app.AppCompatDelegate;

import com.chartered4.models.LoginBean;
import com.facebook.stetho.Stetho;
import com.google.gson.Gson;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;

/**
 * Created by Sunil on 20-Aug-22.
 */
public class SharedObjects extends Application {

    private static final String TAG = "SharedObjects";
    public Context context;
    SharedPreferences sharedPreference;
    SharedPreferences.Editor editor;

    public static String PREF_NAME = "Chartered4";

    private static SharedObjects instance;

    public static SharedObjects getInstance() {
        return instance;
    }

    public static Context getContext() {
        return instance;
        // or return instance.getApplicationContext();
    }

    public SharedObjects() {
    }

    public SharedObjects(Context context) {
        this.context = context;

        initializeStetho();

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        sharedPreference = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreference.edit();

        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/inter_regular.ttf")
                                .setFontAttrId(io.github.inflationx.calligraphy3.R.attr.fontPath)
                                .build()))
                .build());
    }

    @Override
    public void onCreate() {
        instance = new SharedObjects(this);
        super.onCreate();
    }

    public LoginBean.Data getUserInfo() {
        return new Gson().fromJson(getPreference(AppConstants.PreferenceConstants.USER_INFO), LoginBean.Data.class);
    }

/*
    public DeviceBean.Result getDeviceInfo(){
        DeviceBean.Result data = new Gson().fromJson(getPreference(AppConstants.USER_INFO), DeviceBean.Result.class);
        return new Gson().fromJson(getPreference(AppConstants.USER_INFO), DeviceBean.Result.class);
    }
*/

    /*

    public static ArrayList<DateOvertimeBean> getOTDates(SharedObjects sharedObjects, String key) {
        String json = sharedObjects.preferencesEditor.getPreference(key);
        Type type = new TypeToken<ArrayList<DateOvertimeBean>>() {
        }.getType();
        return new Gson().fromJson(json, type);
    }*/

    public void initializeStetho() {
        Stetho.InitializerBuilder initializerBuilder = Stetho.newInitializerBuilder(context);
        initializerBuilder.enableWebKitInspector(Stetho.defaultInspectorModulesProvider(context));
        initializerBuilder.enableDumpapp(Stetho.defaultDumperPluginsProvider(context));
        Stetho.Initializer initializer = initializerBuilder.build();
        Stetho.initialize(initializer);
    }

    public void setPreference(String key, String value) {
        editor = sharedPreference.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setPreferenceBoolean(String key, boolean value) {
        editor = sharedPreference.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public String getPreference(String key) {
        try {
            return sharedPreference.getString(key, "");
        } catch (Exception exception) {
            return "";
        }
    }

    public boolean getPreferenceBoolean(String key) {
        try {
            return sharedPreference.getBoolean(key, false);
        } catch (Exception exception) {
            return false;
        }
    }

    public void removeSinglePreference(String pref) {
        if (sharedPreference.contains(pref)) {
            editor = sharedPreference.edit();
            editor.remove(pref);
            editor.commit();
        }
    }

    public void clear() {
        editor = sharedPreference.edit();
        editor.clear();
        editor.commit();
    }

}

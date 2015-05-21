package com.codeforgvl.trolleytracker.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.codeforgvl.trolleytracker.BackgroundLocationService;
import com.codeforgvl.trolleytracker.PreferenceManager;
import com.codeforgvl.trolleytracker.R;

/**
 * Created by Adam on 2/13/14.
 */
public class SettingsActivity extends PreferenceActivity implements SharedPreferences.OnSharedPreferenceChangeListener{
    public static final String KEY_PREF_BACKGROUND_TESTS = "backgroundTestsEnabled";
    public static final String KEY_PREF_TROLLEY_ID = "trolleyId";
    public static final String KEY_PREF_SERVER_IP = "serverIP";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(KEY_PREF_BACKGROUND_TESTS)){
            if(PreferenceManager.getInstance().getBackgroundTestsEnabled(this)){
                //enable background collection
                Intent intent=new Intent(this, BackgroundLocationService.class);
                startService(intent);
            } else {

                //stop background collection
                Intent intent=new Intent(this, BackgroundLocationService.class);
                intent.putExtra(BackgroundLocationService.KEY_STOP, true);
                startService(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
}
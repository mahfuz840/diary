package com.the_spartan.virtualdiary.preferences;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.preference.SwitchPreferenceCompat;

import com.the_spartan.virtualdiary.R;
import com.the_spartan.virtualdiary.activities.SendingNotificationActivity;

public class NotificationPreference extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.notification_preference);

        findPreference("notification_switch_value").setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if ((boolean)newValue){
                    Intent intent = new Intent(getContext(), SendingNotificationActivity.class);
                    startActivity(intent);
                }
                return true;
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

package com.the_spartan.virtualdiary.preferences;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.the_spartan.virtualdiary.R;

/**
 * Created by Spartan on 3/31/2018.
 */

public class FontStylePreference extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.font_style_preference);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}

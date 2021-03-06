package com.the_spartan.virtualdiary.settings_activities;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.the_spartan.virtualdiary.R;

import yuku.ambilwarna.AmbilWarnaDialog;

public class FontColorActivity extends AppCompatActivity {

    int mDefaultColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_color);
        getSupportActionBar().setTitle(R.string.font_color_settings_title);

        mDefaultColor = ContextCompat.getColor(this, R.color.colorPrimary);
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(FontColorActivity.this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                finish();
            }

            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                mDefaultColor = color;
                SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("color", mDefaultColor);
                editor.apply();
                finish();
            }
        });
        colorPicker.show();
    }

}

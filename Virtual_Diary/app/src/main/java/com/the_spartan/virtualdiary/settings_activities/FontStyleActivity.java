package com.the_spartan.virtualdiary.settings_activities;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.the_spartan.virtualdiary.R;
import com.the_spartan.virtualdiary.preferences.FontStylePreference;

public class FontStyleActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_font_style);

        ActionBar actionBar = getSupportActionBar();

        actionBar.setTitle(R.string.settings_title);
        actionBar.setDisplayHomeAsUpEnabled(true);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new FontStylePreference()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }

        return true;
    }
}
